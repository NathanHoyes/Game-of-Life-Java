import org.json.JSONArray;

import javax.swing.*;

public class GliderViewPanel extends JPanel {

    public GliderViewPanel (String title, JSONArray grid) {
        super();
        JLabel titleLabel = new JLabel(title);
        GliderGridView gliderView = new GliderGridView(100, 100);
        gliderView.populateCellsFromArray(grid);

        add(titleLabel);
        add(gliderView);
    }
}
