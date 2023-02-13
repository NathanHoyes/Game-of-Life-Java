import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CellClickListener extends MouseAdapter {
    private final GameOfLife gol;
    private final AnimateThread animate;

    public CellClickListener(AnimateThread animate, GameOfLife gol) {
        this.gol = gol;
        this.animate = animate;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        System.out.printf("Mouse event registered, at %s, %s which is cell at %s, %s. Animation is %s.%n", event.getX(), event.getY(), event.getX() / gol.getCellSize(), event.getY() / gol.getCellSize(), animate.isRunning()?"playing":"paused");
        if (!animate.isRunning()) {
            int x = event.getX() / gol.getCellSize();
            int y = event.getY() / gol.getCellSize();
            Cell cell = gol.getCell(x, y);
            System.out.printf("Changing cell at %s, %s.%n", x,y);
            cell.changeAlive();
            gol.repaint();
        }
    }
}
