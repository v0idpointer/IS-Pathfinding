package net.v0idpointer.is.gfx;

import java.awt.image.BufferedImage;

public class Sprite {

    public static final Sprite WALL = SpriteSheet.TERRAIN.getSprite(0, 0, 16, 32);
    public static final Sprite FLOOR = SpriteSheet.TERRAIN.getSprite(1, 0);

    public static final int SPRITE_WIDTH = 16;
    public static final int SPRITE_HEIGHT = 16;

    private final BufferedImage image;

    public Sprite(final BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

}
