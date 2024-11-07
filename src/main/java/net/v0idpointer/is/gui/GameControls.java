package net.v0idpointer.is.gui;

import net.v0idpointer.is.Game;

import javax.swing.*;

public class GameControls extends JPanel {

    private final Game gameComponent;
    private final JButton playPauseButton;

    public GameControls(Game gameComponent) {

        this.gameComponent = gameComponent;

        this.setSize(640, 256);
        this.setLayout(null);

        this.playPauseButton = new JButton();
        this.playPauseButton.setLocation(0, 0);
        this.playPauseButton.setSize(160, 32);
        this.playPauseButton.setEnabled(false);

        this.playPauseButton.addActionListener((_) -> {
            if (this.gameComponent.isPaused()) this.gameComponent.resume();
            else this.gameComponent.pause();
            this.update();
        });

        this.add(playPauseButton);

        this.update();

    }

    public void update() {

        if (this.gameComponent.isPaused()) this.playPauseButton.setText("Resume simulation");
        else this.playPauseButton.setText("Pause simulation");
        this.playPauseButton.setEnabled(true);

    }

}
