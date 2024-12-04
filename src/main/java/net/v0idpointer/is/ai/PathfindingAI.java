/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is.ai;

import net.v0idpointer.is.world.World;

import java.awt.*;
import java.util.LinkedList;

public abstract class PathfindingAI {

    private Point start, end;

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void reset();
    public abstract boolean isFinished();
    public abstract LinkedList<Point> getResult();

    protected boolean isValidTile(final int x, final int y, final World world) {
        if ((x < 0) || (x >= world.getWidth())) return false;
        if ((y < 0) || (y >= world.getHeight())) return false;
        return true;
    }

    protected boolean isValidFloorTile(final int x, final int y, final World world) {
        if (!this.isValidTile(x, y, world)) return false;
        if (world.getTiles()[x][y] != World.FLOOR) return false;
        return (!this.isValidTile(x, y - 1, world) || (world.getTiles()[x][y - 1] == World.FLOOR));
    }

    protected boolean isConfigured() {
        return ((this.getStart() != null) && (this.getEnd() != null));
    }

    public Point getStart() {
        return this.start;
    }

    public void setStart(Point start) {
        this.start = start;
        this.reset();
    }

    public Point getEnd() {
        return this.end;
    }

    public void setEnd(Point end) {
        this.end = end;
        this.reset();
    }

}
