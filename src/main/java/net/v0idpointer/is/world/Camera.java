package net.v0idpointer.is.world;

public class Camera {

    private int x;
    private int y;
    private double zoom;

    public Camera(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.zoom = 1.00f;
    }

    public Camera() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

}