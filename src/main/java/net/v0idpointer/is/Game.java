package net.v0idpointer.is;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas {

    private static final double TICKS_PER_SECOND = 64.0;

    private Thread gameThread = null;
    private Thread renderThread = null;
    private boolean isRunning = false;

    private int frameCounter = 0;

    public Game() {
        this.setSize(640, 480);
        this.setBackground(Color.black);
    }

    public synchronized void start() {

        if (this.isRunning) return;

        this.gameThread = new Thread(this::run, "GameThread");
        this.renderThread = new Thread(this::render, "RenderThread");

        this.isRunning = true;
        this.gameThread.start();
        this.renderThread.start();

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

    private void tick() { /* TODO: implement. */ }

    private void render() {

        // test code:
        Random rnd = new Random();
        BufferedImage test = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < test.getWidth(); ++x)
            for (int y = 0; y < test.getHeight(); ++y)
                test.setRGB(x, y, rnd.nextInt(0xFFFFFF));

        double timer = 0;
        // ----

        while (this.isRunning) {

            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                this.createBufferStrategy(3);
                continue;
            }

            Graphics g = bs.getDrawGraphics();

            // test code:
            g.drawImage(test, (int)(Math.cos(timer) * 100 + 160), (int)(Math.sin(timer) * 100 + 120), 256, 256, null);
            timer += 0.0025;

            g.dispose();
            bs.show();

            ++this.frameCounter;

        }

    }

}
