import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class displayWorld extends JPanel {
    int[] cord = {65, 20, 40, 80};
    int marg = 60;
    public World ourWorld;

    public displayWorld(World inputWorld) {
        ourWorld = inputWorld;
    }

    protected void paintComponent(Graphics grf) {
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D)grf;

        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //int width = getWidth();
        int height = getHeight();

        double y = (double)(height-2*marg)/128;
        
        for (int i = 0; i<=128; i++) {
            graph.draw(new Line2D.Double(marg+(y*i), marg, marg+(y*i), height-marg));
            graph.draw(new Line2D.Double(marg, marg+(y*i), marg+(y*128), marg+(y*i)));
        }
        graph.setPaint(Color.RED);

        

        for (Cell[] row : ourWorld.cellsInWorld) {
            for (Cell cell : row) {
                if (cell != null) {    
                    graph.draw(new Ellipse2D.Double(marg+(y*(cell.getX()+0.25)), marg+(y*(cell.getY()+0.75)), y/2, y/2));
                    System.out.println("Printed at (" + cell.getX() + ", " + cell.getY() + ")");
                }
            }
        }
    }

    public static void displayThisWorld(World inputWorld) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayWorld displayObject = new displayWorld(inputWorld);
        frame.add(displayObject);
        frame.setSize(400, 400);
        frame.setLocation(200,200);
        frame.setVisible(true);
    }

}