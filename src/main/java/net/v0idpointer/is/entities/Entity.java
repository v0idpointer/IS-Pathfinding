/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is.entities;

import java.awt.*;

public abstract class Entity {

    public static final int MARKED_FOR_DELETE = 1;

    private int x, y;
    private String name;
    private int flags;

    public Entity(final int x, final int y, final String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.flags = 0;
    }

    public abstract void tick();
    public abstract void render(Graphics g);

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void deleteEntity() {
        this.setFlags(this.getFlags() | Entity.MARKED_FOR_DELETE);
    }

}
