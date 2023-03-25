package main.java.com.hoyes.gameoflife.animate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayListener implements ActionListener {

    private final AnimateThread animate;
    private final JButton playBtn;

    public PlayListener(AnimateThread animate, JButton playBtn) {
        this.animate = animate;
        this.playBtn = playBtn;
    }

    public void actionPerformed (ActionEvent event) {
        Object source = event.getSource();
        if (source == playBtn) {
            animate.changeRunning();
            playBtn.setText(animate.isRunning()?"Pause":"Play");
        }
    }
}
