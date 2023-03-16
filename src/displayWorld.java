import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import java.util.Arrays;
import java.awt.event.*;

public class DisplayWorld extends JFrame implements KeyListener {
    int marg = 60;
    public World ourWorld;

    JFrame dwFrame;
    DisplayPanel simulatorPanel;
    ButtonPanel buttonPanel;

    public DisplayWorld() {
        simulatorPanel = new DisplayPanel(new World());
        
        init();
    }
    
    public DisplayWorld(World inputWorld) {
        ourWorld = inputWorld;
        simulatorPanel = new DisplayPanel(inputWorld);
        
        init();
    }

    private void init() {
        dwFrame = new JFrame();
        dwFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dwFrame.addKeyListener(this);
        dwFrame.setFocusable(true);
        dwFrame.setSize(600, 600);
        dwFrame.setLocation(0,0);
        simulatorPanel.initVariables(dwFrame.getWidth(), (int)(dwFrame.getHeight()/1.6));
        buttonPanel = new ButtonPanel(this);

        
        // JPanel vBox2 = new JPanel();
        Container c = dwFrame.getContentPane();
        // vBox2.add(Box.createVerticalStrut(1));
        c.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.gridheight = GridBagConstraints.RELATIVE;
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.weighty=1;
        constraints.weightx=1;
        c.add(simulatorPanel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty=0.1;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridheight=1;
        c.add(buttonPanel, constraints);
        
        
        //Zoom
        dwFrame.addMouseWheelListener(e -> {
            // System.out.println("mWheel: ");
            // System.out.println(e);
            if (e.getPreciseWheelRotation() > 0) {
                simulatorPanel.incrementHeight(1);
                // System.out.println("++");
                repaint();
                // System.out.println(dwFrame.getFocusOwner());
            } else {
                simulatorPanel.incrementHeight(-1);
                // System.out.println("--");
                repaint();
                // System.out.println(dwFrame.getFocusOwner());
            }
        });

        
        // c.add(vBox2);
        dwFrame.addKeyListener(this);
        buttonPanel.survivalZoneX0.addKeyListener(this);
        buttonPanel.survivalZoneX1.addKeyListener(this);
        buttonPanel.survivalZoneY0.addKeyListener(this);
        buttonPanel.survivalZoneY1.addKeyListener(this);
        dwFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                System.out.println("Event: " + componentEvent + "\n");
            }
        });
        dwFrame.setVisible(true);
    }


    //KEY LISTENER STUFF -----------------

    @Override
    public void keyTyped(KeyEvent e) {
        // System.out.println("Key Typed");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println("Key Code: " + e.getKeyCode());
        switch (e.getKeyCode()) {
            case 37:
                simulatorPanel.incrementMiddleX(-1);
                break;
            case 39:
                simulatorPanel.incrementMiddleX(1); break;
            case 38:
                simulatorPanel.incrementMiddleY(-1); break;
            case 40:
                simulatorPanel.incrementMiddleY(1); break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // System.out.println("You pressed: " + e.getKeyChar());
    }

    public static void main(String[] args) {
        World thisWorld = new World(10, 10, 10, "Steve", 5, 0.01, 2);
        DisplayWorld dWorld = new DisplayWorld(thisWorld);
        


        //DEBUG -----
        // thisWorld.printCells();
        // for (int row = 0; row < thisWorld.cellsInWorld.length; row++) {
        //     int numInRow=0;
        //     for (int col = 0; col < thisWorld.cellsInWorld[row].length; col++) {
        //         if (thisWorld.cellsInWorld[row][col] != null) {
        //             numInRow++;
        //         }
        //     }
        //     System.out.println("Row: " + row + " | Cells: " + numInRow);
        // }
    }

}