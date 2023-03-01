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

        double y = (double)(height-2*marg)/ourWorld.sizeY;
        double x = (double)(height-2*marg)/ourWorld.sizeX;
        if (y > x) {
            y = x;
        }
        else {
            x = y;
        }

        int widthModifierNum = 0;
        int heightModifierNum = 0;
        if (ourWorld.sizeY == ourWorld.sizeX) {
            //keep going, don't check other stuff
        }
        else if (ourWorld.sizeY > ourWorld.sizeX) {
            widthModifierNum = ourWorld.sizeY-ourWorld.sizeX;
        }
        else {
            heightModifierNum = ourWorld.sizeX-ourWorld.sizeY;
        }
        //draw Horizontal lines
        for (int i = 0; i<=ourWorld.sizeY; i++) {
            graph.draw(new Line2D.Double(marg, marg+(y*i), marg+(y*(ourWorld.sizeX)), marg+(y*i)));
        }
        //draw Vertical lines
        for (int i=0; i<=ourWorld.sizeX; i++) {
            graph.draw(new Line2D.Double(marg+(x*i), marg, marg+(x*i), (marg+(y*ourWorld.sizeY))));
        }
        graph.setPaint(Color.RED);

        

        for (Cell[] row : ourWorld.cellsInWorld) {
            for (Cell cell : row) {
                if (cell != null) {
                    Ellipse2D drawThisCell = new Ellipse2D.Double(marg+(y*(cell.getX()-0.75)), marg+(y*(cell.getY()-0.75)), y/2, y/2);    
                    graph.draw(drawThisCell);
                    graph.fill(drawThisCell);
                    //System.out.println("Printed at (" + cell.getX() + ", " + cell.getY() + ")");
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