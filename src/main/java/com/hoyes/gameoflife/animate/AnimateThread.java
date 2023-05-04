package main.java.com.hoyes.gameoflife.animate;

import main.java.com.hoyes.gameoflife.grid.GridView;

public class AnimateThread extends Thread{
    GridView gol;

    private boolean running = false;

    public AnimateThread(GridView gol, boolean isRunning) {
        this.gol = gol;
        running = isRunning;
    }

    public AnimateThread(GridView gol) {
        this(gol, false);
    }

    public void run() {
        while( true ) {

            try {
                Thread.sleep(100);
            } catch (Exception e) {}

            if (isRunning()) {
                gol.calculateChanges();
                gol.repaint();
            }
        }
    }

    /*
     * Accessors
     */
    public boolean isRunning() {
        return running;
    }

    /*
     * Mutators
     */
    public void setRunning(boolean running) {
        this.running = (running);
    }

    /*
     * Helpers
     */

    public void changeRunning() {
        setRunning(!isRunning());
    }
}
