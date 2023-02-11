import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public Class displayWorld extends JPanel {
    private Color color;

    public displayWorld() {
        JFrame frame = new JFrame(;)
        setBackground(Color.WHITE);

        setTitle("Cells and Positions");
        setSize(World.sizeX, World.sizeY);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        color = Color.BLUE;
    }


    public void paintAllCells() {
        Graphics2D g2d = (Graphics2d) g;
        for (rows : World.cellsInWorld) {
            for (cells : rows) {
                paint(cell);
            }
        }
    }

}