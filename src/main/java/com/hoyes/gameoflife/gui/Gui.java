package main.java.com.hoyes.gameoflife.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import main.java.com.hoyes.gameoflife.animate.AnimateThread;
import main.java.com.hoyes.gameoflife.animate.PlayListener;
import main.java.com.hoyes.gameoflife.cell.CellClickListener;
import main.java.com.hoyes.gameoflife.grid.GridView;


public class Gui{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGui();
            } catch (IOException e) {
                System.out.println("Something went wrong with the invokeLater on create & show gui.");
                e.printStackTrace();
            }
        });
    }
    public static void createAndShowGui () throws IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        GridView gol = new GridView(1000,1000);
        AnimateThread animate = new AnimateThread(gol);
        CellClickListener cellClickListener = new CellClickListener(animate, gol);
        gol.addMouseListener(cellClickListener);
        JPanel golWrapper = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        golWrapper.add(gol, gbc);
        frame.add(golWrapper, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        frame.add(bottomPanel, BorderLayout.SOUTH);

        JPanel controlsPanel = new JPanel();
        JButton playBtn = new JButton("Play");
        PlayListener playListener = new PlayListener(animate, playBtn);
        playBtn.addActionListener(playListener);
        controlsPanel.add(playBtn);
        bottomPanel.add(controlsPanel, BorderLayout.NORTH);

        GliderPreviewPanel gliderPreviewPanel = new GliderPreviewPanel();
        JScrollPane gliderPreviewPanelScrollPane = new JScrollPane(gliderPreviewPanel);
        gliderPreviewPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gliderPreviewPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        bottomPanel.add(gliderPreviewPanelScrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

        animate.start();
    }

    /*
     * Helpers
     */

}
