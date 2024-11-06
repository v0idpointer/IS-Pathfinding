package net.v0idpointer.is;

public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        Window wnd = new Window();

        wnd.getFrame().add(game);
        game.start();

    }

}