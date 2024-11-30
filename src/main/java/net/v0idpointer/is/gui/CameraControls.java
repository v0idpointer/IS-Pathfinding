package net.v0idpointer.is.gui;

import net.v0idpointer.is.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CameraControls implements MouseListener, MouseMotionListener {

    private final Game game;
    private final float sensitivity;
    private int startX, startY;

    public CameraControls(final Game game, final float sensitivity) {
        this.game = game;
        this.sensitivity = sensitivity;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if ((e.getClickCount() == 2) && (this.game.getCamera() != null)) {

            final int x = ((e.getX() + this.game.getCamera().getX()) / 32);
            final int y = ((e.getY() + this.game.getCamera().getY()) / 32);

            if (this.game.getWorld() != null)
                this.game.getWorld().setPingMarkerAt(x, y);

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.startX = e.getX();
        this.startY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (this.game.getCamera() == null) return;

        final int dx = (int)(-(e.getX() - this.startX) / this.sensitivity);
        final int dy = (int)(-(e.getY() - this.startY) / this.sensitivity);

        this.game.getCamera().setX(this.game.getCamera().getX() + dx);
        this.game.getCamera().setY(this.game.getCamera().getY() + dy);
        this.startX = e.getX();
        this.startY = e.getY();

    }

    @Override
    public void mouseMoved(MouseEvent e) { }

}
