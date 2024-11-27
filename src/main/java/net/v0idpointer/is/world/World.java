package net.v0idpointer.is.world;

import net.v0idpointer.is.gfx.Sprite;

import java.awt.*;

public class World {

    private final int width;
    private final int height;
    private final int[][] tiles;

    public World(final int width, final int height) {

        this.width = width;
        this.height = height;
        this.tiles = new int[this.width][this.height];

        // test code:
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                if ((x == 0) || (y == 0) || (x == (width - 1)) || (y == (height - 1))) this.tiles[x][y] = 1;
            }
        }
        this.tiles[2][height - 1] = 0;
        this.tiles[3][height - 1] = 0;

    }

    public void tick() {
        // ...
    }

    public void render(Graphics g) {

        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {

                if (this.tiles[x][y] == 1) {
                    g.drawImage(Sprite.WALL.getImage(), x * 32, y * 32, 32, 64, null);
                }

                if (this.tiles[x][y] == 0) {
                    g.drawImage(Sprite.FLOOR.getImage(), x * 32, (y + 1) * 32, 64, 32, null);
                }

            }
        }

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

}
