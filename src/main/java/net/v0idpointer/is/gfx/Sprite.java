/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is.gfx;

import java.awt.image.BufferedImage;

public class Sprite {

    public static final Sprite WALL = SpriteSheet.TERRAIN.getSprite(0, 0, 16, 32);
    public static final Sprite FLOOR = SpriteSheet.TERRAIN.getSprite(1, 0);
    public static final Sprite PING = SpriteSheet.TERRAIN.getSprite(0, 2);

    public static final AnimatedSprite PERSON = new AnimatedSprite(
            SpriteSheet.TERRAIN.getSprite(3, 0, 16, 32),
            SpriteSheet.TERRAIN.getSprite(4, 0, 16, 32)
    );

    public static final AnimatedSprite PERSON_WALKING = new AnimatedSprite(
            SpriteSheet.TERRAIN.getSprite(7, 0, 16, 32),
            SpriteSheet.TERRAIN.getSprite(8, 0, 16, 32)
    );

    public static final Sprite PERSON_VICTORY = SpriteSheet.TERRAIN.getSprite(5, 0, 26, 32);

    public static final Sprite LEVEL1 = SpriteSheet.TERRAIN.getSprite(0, 3, 32, 32);

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
