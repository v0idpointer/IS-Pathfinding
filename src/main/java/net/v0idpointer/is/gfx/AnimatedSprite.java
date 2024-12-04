/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is.gfx;

import java.awt.image.BufferedImage;

public class AnimatedSprite {

    private final BufferedImage[] images;
    private int frame;

    public AnimatedSprite(final BufferedImage... images) {
        this.images = images;
        this.frame = 0;
    }

    public AnimatedSprite(final Sprite... sprites) {
        this.images = new BufferedImage[sprites.length];
        for (int i = 0; i < this.images.length; ++i)
            this.images[i] = sprites[i].getImage();
        this.frame = 0;
    }

    public BufferedImage getImage() {
        return this.images[this.frame];
    }

    public void nextFrame() {
        if (++this.frame >= this.images.length) this.frame = 0;
    }

    public BufferedImage[] getImages() {
        return this.images;
    }

}
