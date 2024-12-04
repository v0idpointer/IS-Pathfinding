/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is;

import net.v0idpointer.is.gui.Window;

public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        Window wnd = new Window(Window.WIDTH, Window.HEIGHT, Window.TITLE, game);

        game.start();

    }

}