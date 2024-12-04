/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet {

    public static final SpriteSheet TERRAIN = new SpriteSheet("/textures/terrain.png");

    private final BufferedImage image;

    public SpriteSheet(final String path) {

        BufferedImage img = null;

        try {
            final URL url = this.getClass().getResource(path);
            if (url != null) img = ImageIO.read(url);
            else System.err.printf("Resource '%s' doesn't exist.\n", path);
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        this.image = img;

    }

    public Sprite getSprite(final int x, final int y, final int w, final int h) {

        if (((x * Sprite.SPRITE_WIDTH) > this.image.getWidth()) || (x < 0))
            throw new IllegalArgumentException("x out of range.");

        if (((y * Sprite.SPRITE_HEIGHT) > this.image.getHeight()) || (y < 0))
            throw new IllegalArgumentException("y out of range.");

        if (((x * Sprite.SPRITE_WIDTH) + w) > this.image.getWidth())
            throw new IllegalArgumentException("w out of range.");

        if (((y * Sprite.SPRITE_WIDTH) + h) > this.image.getWidth())
            throw new IllegalArgumentException("h out of range.");

        BufferedImage img = this.image.getSubimage(
                (x * Sprite.SPRITE_WIDTH),
                (y * Sprite.SPRITE_HEIGHT),
                w,
                h
        );

        return new Sprite(img);
    }

    public Sprite getSprite(final int x, final int y) {
        return this.getSprite(x, y, Sprite.SPRITE_WIDTH, Sprite.SPRITE_HEIGHT);
    }

    public BufferedImage getImage() {
        return image;
    }

}
