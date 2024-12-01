package net.v0idpointer.is.ai;

import net.v0idpointer.is.Game;
import net.v0idpointer.is.entities.Entity;
import net.v0idpointer.is.world.World;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class BestFirstSearch extends PathfindingAI {

    private static class BestFirstRecord implements Comparable<BestFirstRecord> {

        public Point point;
        public int heuristic;
        public LinkedList<Point> path;

        public BestFirstRecord(final Point point, final Point target) {
            this.point = point;
            this.path = new LinkedList<>();
            this.heuristic = BestFirstRecord.calculateHeuristics(point, target);
        }

        private static int calculateHeuristics(final Point from, final Point to) {

            if ((from == null) || (to == null)) return Integer.MAX_VALUE;

            final int x1 = from.x;
            final int y1 = from.y;
            final int x2 = to.x;
            final int y2 = to.y;

            return (int)(Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2))));
        }

        @Override
        public int compareTo(BestFirstRecord o) {
            return Integer.compare(this.heuristic, o.heuristic);
        }

    }

    private final Game game;
    private final PriorityQueue<BestFirstRecord> priorityQueue;
    private final HashSet<Point> visited;
    private LinkedList<Point> result;

    public BestFirstSearch(final Game game) {
        this.game = game;
        this.priorityQueue = new PriorityQueue<>();
        this.visited = new HashSet<>();
        this.result = null;
    }

    @Override
    public void tick() {

        if (!this.isConfigured()) return;
        if (this.result != null) return;

        if (this.priorityQueue.isEmpty()) {
            final Entity pingMarker = this.game.getWorld().getEntityByName("PING_MARKER");
            if (pingMarker != null) pingMarker.deleteEntity();
            this.setEnd(null);
            return;
        }

        final BestFirstRecord current = this.priorityQueue.poll();
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

    private boolean queueTile(final BestFirstRecord record, final int x, final int y, final World world) {

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

        BestFirstRecord r = new BestFirstRecord(point, this.getEnd());
        r.path.addAll(record.path);
        r.path.add(record.point);

        this.priorityQueue.add(r);
        return false;
    }

    @Override
    public void render(Graphics g) {

        if (!this.isConfigured()) return;

        if (!this.isFinished()) {

            BestFirstRecord[] records = this.priorityQueue.toArray(new BestFirstRecord[0]);
            for (final BestFirstRecord record : records) {
                if (record == null) continue;
                g.setColor(Color.yellow);
                g.drawRect((record.point.x * 32), (record.point.y * 32), 32, 32);
                g.drawString(String.format("%d", record.heuristic), ((record.point.x * 32) + 4), ((record.point.y * 32) + 28));
            }

        }

        // drawing the pathfinding result is really fucking retarded
        try {

            LinkedList<Point> result = this.result;
            if (result == null) return;

            for (final Point point : result) {
                g.setColor(Color.green);
                g.drawRect((point.x * 32), (point.y * 32), 32, 32);
            }

        }
        catch (Exception ex) {
            System.err.printf("Best-first search: error in draw result: %s\n", ex.getMessage());
        }

    }

    @Override
    public void reset() {
        this.priorityQueue.clear();
        this.visited.clear();
        this.result = null;
        this.priorityQueue.add(new BestFirstRecord(this.getStart(), this.getEnd()));
    }

    @Override
    public boolean isFinished() {
        return (this.result != null);
    }

    @Override
    public LinkedList<Point> getResult() {
        return this.result;
    }

    @Override
    protected boolean isConfigured() {
        if (this.game.getWorld() == null) return false;
        return super.isConfigured();
    }

}
