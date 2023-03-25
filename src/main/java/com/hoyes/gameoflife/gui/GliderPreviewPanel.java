package main.java.com.hoyes.gameoflife.gui;

import main.java.com.hoyes.gameoflife.animate.AnimateThread;
import main.java.com.hoyes.gameoflife.grid.GliderPreviewGrid;
import main.java.com.hoyes.gameoflife.io.GridData;
import main.java.com.hoyes.gameoflife.io.GridIO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GliderPreviewPanel extends JPanel {

    private List<GliderPreviewGrid> grids;

    public GliderPreviewPanel(){
        super(new FlowLayout(FlowLayout.LEFT, 5, 5));
        grids = getGliderPreviewsFromFile();
    }

    /*
     * Init helpers
     */

    private GliderPreviewGrid makeGliderPreview(GridData grid) {
        return new GliderPreviewGrid(200, 200, 3, grid.data());
    }

    private List<GliderPreviewGrid> getGliderPreviewsFromFile() {
        ArrayList<GliderPreviewGrid> grids = new ArrayList<>();
        GridIO gridIO = new GridIO(GridIO.Type.GLIDER);
        while ( gridIO.hasNext() ) {
            GliderPreviewGrid gPreview = makeGliderPreview(gridIO.nextGrid());
            this.add(gPreview);
            grids.add(gPreview);
            AnimateThread animateThread = new AnimateThread(gPreview);
            animateThread.start();
        }
        return grids;
    }
}
