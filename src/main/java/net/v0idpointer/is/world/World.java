package net.v0idpointer.is.world;

import net.v0idpointer.is.entities.Entity;
import net.v0idpointer.is.entities.PingMarker;
import net.v0idpointer.is.gfx.Sprite;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class World {

    // World tiles:
    public static final int VOID = 0;
    public static final int FLOOR = 1;
    public static final int WALL = 2;

    private final int width;
    private final int height;
    private final int[][] tiles;
    private final List<Entity> entities;

    public World(final int width, final int height) {

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

    public static World loadWorld(final Sprite sprite) {

        World world = new World(sprite.getImage().getWidth(), sprite.getImage().getHeight());

        for (int y = 0; y < world.getHeight(); ++y) {
            for (int x = 0; x < world.getWidth(); ++x) {

                final int pixel = (sprite.getImage().getRGB(x, y) & 0xFFFFFF);

                int tile = switch (pixel) {
                    case 0xFFFFFF -> World.FLOOR;
                    case 0xFF0000 -> World.WALL;
                    default -> World.VOID;
                };

                world.getTiles()[x][y] = tile;

            }
        }

        return world;
    }

}
