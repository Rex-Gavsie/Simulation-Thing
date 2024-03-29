import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;

public class DisplayPanel extends JPanel {
    private World ourWorld;
    public int height;
    double widthMiddle, heightMiddle;

    public DisplayPanel(World inputWorld) {
        ourWorld = inputWorld;
        widthMiddle = getWidth()/2;
        heightMiddle = getHeight()/2;
        height = getHeight();
        this.setFocusable(true);
    }

    public void initVariables(int width, int height) {
        widthMiddle = width/2;
        heightMiddle = height/2;
        this.height = height;
    }

    protected void paintComponent(Graphics grf) {
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D)grf;
        
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        
        //Draw the center point just for debug
        // graph.fill(new Ellipse2D.Double(widthMiddle-5, heightMiddle-5, 10., 10.));
        // graph.draw(new Line2D.Double(0, heightMiddle, getWidth(), heightMiddle));
        // graph.draw(new Line2D.Double(widthMiddle, 0, widthMiddle, getHeight()));


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

        Double lineYOddMod = 0.;
        Double lineXOddMod = 0.;

        if (ourWorld.sizeY%2 != 0) { 
            lineYOddMod = y/2;
        }
        if (ourWorld.sizeX%2 != 0) { 
            lineXOddMod = x/2;
        }

        for (int i = 0; i <=ourWorld.sizeY; i++) {
            graph.draw(new Line2D.Double(leftPoint-lineXOddMod, upperPoint+(y*i)-lineYOddMod, rightPoint+lineXOddMod, upperPoint+(y*i)-lineYOddMod));
        }
        
        //draw Vertical lines
        for (int i=0; i<=ourWorld.sizeX; i++) {
            graph.draw(new Line2D.Double(leftPoint+(x*i)-lineXOddMod, upperPoint-lineYOddMod, leftPoint+(x*i)-lineXOddMod, lowerPoint+lineYOddMod));
        }
        
        
        graph.setPaint(Color.RED);

        

        for (Cell[] row : ourWorld.cellsInWorld) {
            for (Cell cell : row) {
                if (cell != null) {
                    Double xOddMod = 0.;
                    Double yOddMod = 0.;
                    if (ourWorld.sizeX%2 != 0) {
                        xOddMod = x/2;
                    }
                    if (ourWorld.sizeY%2 != 0) {
                        yOddMod = y/2;
                    }
                    Double cellXPos = (((((double)cell.getX())-(ourWorld.sizeX/2))*x)+widthMiddle-x)-xOddMod;
                    Double cellYPos = (((((double)cell.getY())-(ourWorld.sizeY/2))*y)+heightMiddle-y)-yOddMod;
                    Ellipse2D drawThisCell = new Ellipse2D.Double(cellXPos, cellYPos, y, y);    
                    graph.draw(drawThisCell);
                    graph.fill(drawThisCell);
                    //System.out.println("Printed at (" + cell.getX() + ", " + cell.getY() + ")");
                }
            }
        }

        // System.out.println("Height: " + height);
    }

    /**
     * 
     * @param direction 
     *  <p> If > 0, go right
     *  <p> If < 0, go left
     */
    public void incrementMiddleX(int direction) {
        if (direction > 0) {
            widthMiddle++;
        } else {
            widthMiddle--;
        }
        repaint();
    }

    /**
     * 
     * @param direction 
     *  <p> If > 0, go down
     *  <p> If < 0, go up
     */
    public void incrementMiddleY(int direction) {
        if (direction > 0) {
            heightMiddle++;
        } else {
            heightMiddle--;
        }
        repaint();
    }

    /**
     * 
     * @param direction 
     *  <p> If > 0, go down
     *  <p> If < 0, go up
     */
    public void incrementHeight(int direction) {
        if (direction > 0) {
            height++;
        } else {
            height--;
        }
        repaint();
    }

}
