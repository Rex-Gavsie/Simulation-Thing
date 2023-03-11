import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class DisplayPanel extends JPanel {
    private int marg = 60;
    private World ourWorld;
    public int height = getHeight();

    public DisplayPanel(World inputWorld) {
        ourWorld = inputWorld;
    }

    protected void paintComponent(Graphics grf) {
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D)grf;

        
        
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double widthMiddle = getWidth()/2;
        double heightMiddle = getHeight()/2;
        
        //Draw the center point just for debug
        graph.fill(new Ellipse2D.Double(widthMiddle-5, heightMiddle-5, 10., 10.));
        graph.draw(new Line2D.Double(0, heightMiddle, getWidth(), heightMiddle));
        graph.draw(new Line2D.Double(widthMiddle, 0, widthMiddle, getHeight()));


        double y = (double)(height)/ourWorld.sizeY;
        double x = (double)(height)/ourWorld.sizeX;
        if (y > x) {
            y = x;
        }
        else {
            x = y;
        }

        
        //draw Horizontal lines
        Double leftPoint = widthMiddle-(x*(ourWorld.sizeX/2));
        Double rightPoint = widthMiddle+(x*(ourWorld.sizeX/2));
        Double upperPoint = heightMiddle-(y*(ourWorld.sizeY/2));
        Double lowerPoint = heightMiddle+(y*(ourWorld.sizeY/2));

        if (ourWorld.sizeX%2 == 0) { 
            //If width is even then draw lines in the center (x | x) 
            //If odd, draw the line in the center (| x |)
            for (int i = 0; i<=ourWorld.sizeY; i++) {
                graph.draw(new Line2D.Double(leftPoint, upperPoint+(y*i), rightPoint, upperPoint+(y*i)));
            }   
        } else {
            for (int i = 0; i <=ourWorld.sizeY; i++) {
                graph.draw(new Line2D.Double(leftPoint-(x/2), upperPoint+(y*i)-(y/2), rightPoint+(x/2), upperPoint+(y*i)-(y/2)));
            }
        }
        
        //draw Vertical lines

        if (ourWorld.sizeY%2 == 0) {
            //Same idea as with the horizontal lines
            for (int i=0; i<=ourWorld.sizeX; i++) {
                graph.draw(new Line2D.Double(leftPoint+(x*i), upperPoint, leftPoint+(x*i), lowerPoint));
            }
        } else {
            for (int i=0; i<=ourWorld.sizeX; i++) {
                graph.draw(new Line2D.Double(leftPoint+(x*i)-(x/2), upperPoint-(y/2), leftPoint+(x*i)-(x/2), lowerPoint+(y/2)));
            }
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

    public void changeMarg(int newMarg) {
        marg = newMarg;
        repaint();
    }
}
