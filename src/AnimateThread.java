public class AnimateThread extends Thread{
    GameOfLife gol;

    private boolean running = false;

    public AnimateThread(GameOfLife gol) {
        this.gol = gol;

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
