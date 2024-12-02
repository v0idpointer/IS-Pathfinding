package net.v0idpointer.is.gui;

import net.v0idpointer.is.Game;
import net.v0idpointer.is.ai.PathfindingAlgorithm;

import javax.swing.*;
import java.util.Hashtable;

public class GameControls extends JPanel {

    private final Game gameComponent;
    private final JButton playPauseButton;
    private final JSlider tickrateSlider;
    private final JComboBox<PathfindingAlgorithm> algorithms;

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

        final Hashtable<Integer, JLabel> sliderLabels = new Hashtable<>();
        sliderLabels.put(16, new JLabel("x0.25"));
        sliderLabels.put(32, new JLabel("x0.5"));
        sliderLabels.put(64, new JLabel("x1"));
        sliderLabels.put(96, new JLabel("x1.5"));
        sliderLabels.put(128, new JLabel("x2"));

        this.tickrateSlider = new JSlider();
        this.tickrateSlider.setLocation(176, 0);
        this.tickrateSlider.setSize(464, 32);
        this.tickrateSlider.setMaximum(128);
        this.tickrateSlider.setMinimum(16);
        this.tickrateSlider.setValue(64);
        this.tickrateSlider.setLabelTable(sliderLabels);
        this.tickrateSlider.setPaintLabels(true);

        this.tickrateSlider.addChangeListener((_) -> {
            this.gameComponent.setTickRate(this.tickrateSlider.getValue());
        });

        this.add(this.tickrateSlider);

        this.algorithms = new JComboBox<>();
        this.algorithms.setLocation(0, 48);
        this.algorithms.setSize(256, 32);
        this.algorithms.addItem(PathfindingAlgorithm.DEPTH_FIRST_SEARCH);
        this.algorithms.addItem(PathfindingAlgorithm.BREADTH_FIRST_SEARCH);
        this.algorithms.addItem(PathfindingAlgorithm.BEST_FIRST_SEARCH);
        this.algorithms.setSelectedIndex(1);

        this.algorithms.addActionListener((_) -> {
            this.gameComponent.setAi((PathfindingAlgorithm)(this.algorithms.getSelectedItem()));
        });

        this.add(this.algorithms);

        this.update();

    }

    public void update() {

        if (this.gameComponent.isPaused()) this.playPauseButton.setText("Resume simulation");
        else this.playPauseButton.setText("Pause simulation");
        this.playPauseButton.setEnabled(true);

    }

}
