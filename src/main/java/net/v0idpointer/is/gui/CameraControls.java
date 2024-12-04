/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is.gui;

import net.v0idpointer.is.Game;

import java.awt.event.*;

public class CameraControls implements MouseListener, MouseMotionListener, MouseWheelListener {

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

            final int x = (int)(((double)(e.getX() + this.game.getCamera().getX()) / 32) / this.game.getCamera().getZoom());
            final int y = (int)(((double)(e.getY() + this.game.getCamera().getY()) / 32) / this.game.getCamera().getZoom());

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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.game.getCamera().setZoom(this.game.getCamera().getZoom() + (0.025 * -e.getWheelRotation()));
    }

}
