import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import org.json.*;


public class Gui{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGui();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static void createAndShowGui () throws IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        GameGridView gol = new GameGridView(1000,1000);
        AnimateThread animate = new AnimateThread(gol);
        CellClickListener cellClickListener = new CellClickListener(animate, gol);
        JButton playBtn = new JButton("Play");
        PlayListener playListener = new PlayListener(animate, playBtn);

        gol.addMouseListener(cellClickListener);
        frame.add(gol, c);

        playBtn.addActionListener(playListener);
        c.fill = GridBagConstraints.NONE;
        c.gridy = 1;
        frame.add(playBtn, c);
        c.gridy = 2;
        generateGliderPanels(frame, c);

        frame.pack();
        frame.setVisible(true);

        animate.start();
    }

    /*
     * Helpers
     */

    public static void generateGliderPanels(JFrame frame, GridBagConstraints c) throws IOException {
        JFrame container = new JFrame();
        c.gridy = 3;
        frame.add(container);
        String jsonString = new String(Files.readString(Path.of("./Data/gliders.json")));
        JSONObject jsonObject = new JSONObject(jsonString);
        Iterator<String> gliders = jsonObject.keys();
        while (gliders.hasNext()) {
            String title = gliders.next();
            System.out.println(title);
            JSONArray grid = jsonObject.getJSONArray(title);
            GliderViewPanel gliderViewPanel = new GliderViewPanel(title, grid);
            frame.add(container);
        }
        container.pack();
    }
}
