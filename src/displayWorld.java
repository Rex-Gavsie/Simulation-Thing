import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public Class displayWorld extends JPanel {
    private ArrayList<Cell> cells;
    private Color color;

    public displayWorld() {
        setBackground(Color.WHITE);

        cells = new ArrayList<Cell>();
        activeCell = null;

        color = Color.BLUE;
    }

    public void drawCell(int posX, int posY) {

    }
}