package net.v0idpointer.is;

import net.v0idpointer.is.ai.BestFirstSearch;
import net.v0idpointer.is.ai.PathfindingAI;
import net.v0idpointer.is.gfx.Sprite;
import net.v0idpointer.is.gui.CameraControls;
import net.v0idpointer.is.world.Camera;
import net.v0idpointer.is.world.World;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas {

    private static final double TICKS_PER_SECOND = 64.0;

    private Thread gameThread = null;
    private Thread renderThread = null;
    private boolean isRunning = false;
    private boolean isPaused = false;

    private int frameCounter = 0;

    private Camera camera;
    private World world;
    private PathfindingAI ai;

    public Game() {
        this.setSize(640, 480);
        this.setBackground(Color.black);

        final CameraControls cameraControls = new CameraControls(this, 1.5f);
        this.addMouseListener(cameraControls);
        this.addMouseWheelListener(cameraControls);
        this.addMouseMotionListener(cameraControls);

    }

    public synchronized void start() {

        if (this.isRunning) return;

        this.gameThread = new Thread(this::run, "GameThread");
        this.renderThread = new Thread(this::render, "RenderThread");

        this.isRunning = true;
        this.gameThread.start();
        this.renderThread.start();

        this.camera = new Camera();
        this.world = World.loadWorld(this, Sprite.LEVEL1);
        this.ai = new BestFirstSearch(this);

    }

    public synchronized void stop() {

        if (!this.isRunning) return;

        try {
            this.isRunning = false;
            this.gameThread.join();
            this.renderThread.join();
        }
        catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

    }

    public void pause() {
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
    }

    public boolean isPaused() {
        return this.isPaused;
    }

    private void run() {

        long lastTime = System.nanoTime();
        double ns = (1000000000 / Game.TICKS_PER_SECOND);
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (this.isRunning) {

            long now = System.nanoTime();
            delta += ((now - lastTime) / ns);
            lastTime = now;

            while (delta >= 1) {
                this.tick();
                --delta;
            }

            if ((System.currentTimeMillis() - timer) > 1000) {
                System.out.println("FPS: " + this.frameCounter);
                this.frameCounter = 0;
                timer += 1000;
            }

        }

    }

    private void tick() {
        if (this.isPaused) return;
        if (this.world != null) this.world.tick();
    }

    private void render() {

        while (this.isRunning) {

            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                this.createBufferStrategy(3);
                continue;
            }

            Graphics g = bs.getDrawGraphics();
            Graphics2D g2d = (Graphics2D)(g);

            g2d.translate(0, 0);
            g2d.scale(1.00, 1.00);
            g2d.setColor(Color.black);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

            if (this.camera != null) {
                g2d.translate(-this.camera.getX(), -this.camera.getY());
                g2d.scale(this.camera.getZoom(), this.camera.getZoom());
            }

            if (this.world != null) this.world.render(g2d);

            g2d.dispose();
            bs.show();

            ++this.frameCounter;

        }

    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public PathfindingAI getAi() {
        return ai;
    }

    public void setAi(PathfindingAI ai) {
        this.ai = ai;
    }

}
