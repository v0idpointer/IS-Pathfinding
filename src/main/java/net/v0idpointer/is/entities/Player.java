package net.v0idpointer.is.entities;

import net.v0idpointer.is.gfx.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private int timer;
    private boolean frame;

    public Player(final int x, final int y) {
        super(x, y, "PLAYER");
    }

    @Override
    public void tick() {

        if (++timer > 24) {
            frame = !frame;
            timer = 0;
        }

    }

    @Override
    public void render(Graphics g) {

        BufferedImage img = ((frame) ? Sprite.SLIME1 : Sprite.SLIME2).getImage();

        g.drawImage(
                img,
                (this.getX() * 32),
                (this.getY() * 32),
                32,
                32,
                null
        );
    }

}
