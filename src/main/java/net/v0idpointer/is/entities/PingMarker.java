package net.v0idpointer.is.entities;

import net.v0idpointer.is.gfx.Sprite;

import java.awt.*;

public class PingMarker extends Entity {

    public PingMarker(int x, int y) {
        super(x, y, "PING_MARKER");
    }

    @Override
    public void tick() { }

    @Override
    public void render(Graphics g) {
        g.drawImage(
                Sprite.PING.getImage(),
                (this.getX() * 32),
                (this.getY() * 32),
                32,
                32,
                null
        );
    }

}
