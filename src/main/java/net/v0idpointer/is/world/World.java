/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is.world;

import net.v0idpointer.is.Game;
import net.v0idpointer.is.ai.PathfindingAI;
import net.v0idpointer.is.entities.Entity;
import net.v0idpointer.is.entities.PingMarker;
import net.v0idpointer.is.entities.Player;
import net.v0idpointer.is.gfx.Sprite;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class World {

    // World tiles:
    public static final int VOID = 0;
    public static final int FLOOR = 1;
    public static final int WALL = 2;

    private final Game game;
    private final int width;
    private final int height;
    private final int[][] tiles;
    private final List<Entity> entities;

    public World(final Game game, final int width, final int height) {

        this.game = game;
        this.width = width;
        this.height = height;
        this.tiles = new int[this.width][this.height];
        this.entities = new LinkedList<>();

    }

    public void tick() {

        LinkedList<Entity> markedForDelete = new LinkedList<>();

        for (Entity entity : this.entities) {
            entity.tick();
            if ((entity.getFlags() & Entity.MARKED_FOR_DELETE) != 0)
                markedForDelete.add(entity);
        }

        for (Entity entity : markedForDelete)
            this.entities.remove(entity);

        if (this.game.getAi() != null)
            this.game.getAi().tick();

    }

    public Entity getEntityByName(final String name) {
        for (Entity entity : this.entities)
            if (entity.getName().equals(name))
                return entity;
        return null;
    }

    public void setPingMarkerAt(int x, int y) {

        if ((x < 0) || (x >= this.width)) return;
        if ((y < 0) || (y >= this.height)) return;

        // prevent marker placement on walls, sides of walls, and void.
        if ((this.tiles[x][y] == World.WALL) || (this.tiles[x][y] == World.VOID)) return;
        if ((y > 0) && (this.tiles[x][y - 1]) == World.WALL) return;

        Entity marker = this.getEntityByName("PING_MARKER");
        if (marker == null) {
            marker = new PingMarker(x, y);
            this.entities.add(marker);
        }

        marker.setX(x);
        marker.setY(y);

        Entity player = this.getEntityByName("PLAYER");
        if (player == null) return;

        PathfindingAI ai = this.game.getAi();
        if (ai == null) return;

        ai.setStart(new Point(player.getX(), player.getY()));
        ai.setEnd(new Point(x, y));

    }

    public void render(Graphics g) {

        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {

                if (this.tiles[x][y] == World.WALL) {
                    g.drawImage(Sprite.WALL.getImage(), x * 32, y * 32, 32, 64, null);
                }

                if (this.tiles[x][y] == World.FLOOR) {
                    g.drawImage(Sprite.FLOOR.getImage(), x * 32, (y + 1) * 32, 64, 32, null);
                }

            }
        }

        if (this.game.getAi() != null)
            this.game.getAi().render(g);

        for (Entity entity : this.entities)
            entity.render(g);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public static World loadWorld(final Game game, final Sprite sprite) {

        World world = new World(game, sprite.getImage().getWidth(), sprite.getImage().getHeight());

        LinkedList<Point> spawnPoints = new LinkedList<>();

        for (int y = 0; y < world.getHeight(); ++y) {
            for (int x = 0; x < world.getWidth(); ++x) {

                final int pixel = (sprite.getImage().getRGB(x, y) & 0xFFFFFF);

                int tile = switch (pixel) {
                    case 0xFFFFFF -> World.FLOOR;
                    case 0xFF0000 -> World.WALL;
                    default -> World.VOID;
                };

                if (pixel == 0xFF0080) {
                    spawnPoints.add(new Point(x, y));
                    tile = World.FLOOR;
                }

                world.getTiles()[x][y] = tile;

            }
        }

        Random random = new Random();
        if (!spawnPoints.isEmpty()) {
            final Point spawnPoint = spawnPoints.get(random.nextInt(spawnPoints.size()));
            final Player player = new Player(game, spawnPoint.x, spawnPoint.y);
            game.getCamera().focusAt(player);
            world.getEntities().add(player);
        }

        return world;
    }

}
