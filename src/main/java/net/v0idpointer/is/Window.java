package net.v0idpointer.is;

import javax.swing.*;

public class Window {

    private final JFrame frame;

    public Window() {

        this.frame = new JFrame();
        this.frame.setSize(800, 600);
        this.frame.setTitle("Intelligent Systems");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.setLayout(null);

    }

    public JFrame getFrame() {
        return frame;
    }

}
