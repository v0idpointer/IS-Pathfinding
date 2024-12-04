/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is.entities;

import net.v0idpointer.is.Game;
import net.v0idpointer.is.gfx.AnimatedSprite;
import net.v0idpointer.is.gfx.Sprite;

import java.awt.*;

public class Player extends Entity {

    private final Game game;
    private int animationTimer;
    private int walkTimer;
    private int victoryTimer;

    public Player(final Game game, final int x, final int y) {
        super(x, y, "PLAYER");
        this.game = game;
    }

    @Override
    public void tick() {

        if (++this.animationTimer > 24) {
            this.getAnimatedSprite().nextFrame();
            this.animationTimer = 0;
        }

        if ((this.game.getAi() != null) && this.game.getAi().isFinished() && (this.walkTimer == 0)) {

            if (this.victoryTimer > 0) this.victoryTimer = 0;

            final Point point = this.game.getAi().getResult().poll();
            if (point == null) {

                this.game.getAi().setStart(null);
                this.game.getAi().setEnd(null);
                this.game.getAi().reset();
                this.victoryTimer = 128;
                this.walkTimer = 0;

                final Entity pingMarker = this.game.getWorld().getEntityByName("PING_MARKER");
                if (pingMarker != null) pingMarker.deleteEntity();

                return;
            }

            this.setX(point.x);
            this.setY(point.y);
            this.walkTimer = 16;

        }

        if (this.walkTimer > 0) --this.walkTimer;
        if (this.victoryTimer > 0) --this.victoryTimer;

    }

    @Override
    public void render(Graphics g) {

        if (this.victoryTimer > 0) {

            g.drawImage(
                    Sprite.PERSON_VICTORY.getImage(),
                    ((this.getX() * 32) - 10),
                    ((this.getY() * 32) - 48),
                    52,
                    64,
                    null
            );

            return;
        }

        g.drawImage(
                this.getAnimatedSprite().getImage(),
                (this.getX() * 32),
                ((this.getY() * 32) - 48),
                32,
                64,
                null
        );

    }

    private AnimatedSprite getAnimatedSprite() {
        if ((this.game.getAi() != null) && this.game.getAi().isFinished())
            return Sprite.PERSON_WALKING;
        else return Sprite.PERSON;
    }

}
