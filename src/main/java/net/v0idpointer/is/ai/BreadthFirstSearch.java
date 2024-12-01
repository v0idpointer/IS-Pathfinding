package net.v0idpointer.is.ai;

import net.v0idpointer.is.Game;
import net.v0idpointer.is.entities.Entity;
import net.v0idpointer.is.world.World;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends PathfindingAI {

    private static class BfsRecord {

        public Point point;
        public LinkedList<Point> path;

        public BfsRecord(final Point point) {
            this.point = point;
            this.path = new LinkedList<>();
        }

    }

    private final Game game;
    private final Queue<BfsRecord> queue;
    private final HashSet<Point> visited;
    private LinkedList<Point> result;

    public BreadthFirstSearch(final Game game) {
        this.game = game;
        this.queue = new LinkedList<>();
        this.visited = new HashSet<>();
        this.result = null;
    }

    @Override
    public synchronized void tick() {

        if (!this.isConfigured()) return;
        if (this.result != null) return;

        if (this.queue.isEmpty()) {
            final Entity pingMarker = this.game.getWorld().getEntityByName("PING_MARKER");
            if (pingMarker != null) pingMarker.deleteEntity();
            this.setEnd(null);
            return;
        }

        final BfsRecord current = this.queue.poll();
        if (this.visited.contains(current.point)) return;

        if (current.point.equals(this.getEnd())) {
            this.reset();
            this.result = current.path;
            this.result.add(current.point);
            return;
        }

        if (this.queueTile(current, (current.point.x + 1), current.point.y, this.game.getWorld())) return;
        if (this.queueTile(current, (current.point.x - 1), current.point.y, this.game.getWorld())) return;
        if (this.queueTile(current, current.point.x, (current.point.y + 1), this.game.getWorld())) return;
        if (this.queueTile(current, current.point.x, (current.point.y - 1), this.game.getWorld())) return;

        this.visited.add(current.point);

    }

    private boolean isValidTile(final int x, final int y, final World world) {
        if ((x < 0) || (x >= world.getWidth())) return false;
        if ((y < 0) || (y >= world.getHeight())) return false;
        return true;
    }

    private boolean isValidFloorTile(final int x, final int y, final World world) {
        if (!this.isValidTile(x, y, world)) return false;
        if (world.getTiles()[x][y] != World.FLOOR) return false;
        return (!this.isValidTile(x, y - 1, world) || (world.getTiles()[x][y - 1] != World.WALL));
    }

    private boolean queueTile(final BfsRecord record, final int x, final int y, final World world) {

        final Point point = new Point(x, y);
        if (!this.isValidFloorTile(x, y, world)) return false;
        if (this.visited.contains(point)) return false;

        if (point.equals(this.getEnd())) {
            this.reset();
            this.result = record.path;
            this.result.add(record.point);
            this.result.add(point);
            return true;
        }

        BfsRecord r = new BfsRecord(point);
        r.path.addAll(record.path);
        r.path.add(record.point);

        this.queue.add(r);
        return false;
    }

    @Override
    public synchronized void render(Graphics g) {

        if (!this.isConfigured()) return;

        // draw the outline of queued tiles:
        for (final BfsRecord record : this.queue) {
            g.setColor(Color.yellow);
            g.drawRect((record.point.x * 32), (record.point.y * 32), 32, 32);
        }

        // if finished: draw the path from the start tile to the end tile
        if (this.isFinished()) {
            for (int i = 0; i < this.result.size(); ++i) {
                final Point point = this.result.get(i);
                g.setColor(Color.green);
                g.drawRect((point.x * 32), (point.y * 32), 32, 32);
            }
        }

    }

    @Override
    public void reset() {
        this.queue.clear();
        this.visited.clear();
        this.result = null;
        this.queue.add(new BfsRecord(this.getStart()));
    }

    @Override
    public boolean isFinished() {
        return (this.result != null);
    }

    @Override
    public LinkedList<Point> getResult() {
        return this.result;
    }

    private boolean isConfigured() {
        if (this.game.getWorld() == null) return false;
        return ((this.getStart() != null) && (this.getEnd() != null));
    }

}
