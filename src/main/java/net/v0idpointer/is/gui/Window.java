/*

    This project was developed as part
    of the Intelligent Systems course
    at Računarski fakultet (RAF).

    Copyright (c) 2024 V0idPointer
    Licensed under the MIT License

*/

package net.v0idpointer.is.gui;

import net.v0idpointer.is.Game;

import javax.swing.*;

public class Window {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    public static final String TITLE = "Intelligent Systems";

    private final JFrame frame;
    private final Game gameComponent;

    public Window(int width, int height, String title, Game gameComponent) {

        this.frame = new JFrame();
        this.frame.setSize(width, height);
        this.frame.setTitle(title);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setLayout(null);

        int x = ((width - gameComponent.getWidth()) / 2);

        this.gameComponent = gameComponent;
        this.gameComponent.setLocation(x, 32);
        this.frame.add(this.gameComponent);

        GameControls controls = new GameControls(this.gameComponent);
        controls.setLocation(x, (this.gameComponent.getHeight() + 64));
        this.frame.add(controls);

        this.frame.setVisible(true);

    }

    public JFrame getFrame() {
        return frame;
    }

    public Game getGameComponent() {
        return this.gameComponent;
    }

}
