import javax.swing.*;
import java.awt.*;


public class Gui{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gui::createAndShowGui);
    }
    public static void createAndShowGui () {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        GameOfLife gol = new GameOfLife();
        AnimateThread animate = new AnimateThread(gol);
        CellClickListener cellClickListener = new CellClickListener(animate, gol);
        JButton playBtn = new JButton("Play");
        PlayListener playListener = new PlayListener(animate, playBtn);

        gol.addMouseListener(cellClickListener);
        gol.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        gol.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        frame.add(gol, c);

        playBtn.addActionListener(playListener);
        c.fill = GridBagConstraints.NONE;
        c.gridy = 1;
        frame.add(playBtn, c);

        frame.pack();
        frame.setVisible(true);

        animate.start();
    }

    /*
     * Helpers
     */
}
