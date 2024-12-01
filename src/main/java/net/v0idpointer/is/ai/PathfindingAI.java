package net.v0idpointer.is.ai;

import java.awt.*;
import java.util.LinkedList;

public abstract class PathfindingAI {

    private Point start, end;

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void reset();
    public abstract boolean isFinished();
    public abstract LinkedList<Point> getResult();

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
