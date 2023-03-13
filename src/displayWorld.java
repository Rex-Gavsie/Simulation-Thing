import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.List;
import java.util.Arrays;

public class displayWorld extends JFrame {
    int marg = 60;
    public World ourWorld;

    public displayWorld(World inputWorld) {
        ourWorld = inputWorld;
        DisplayPanel simulatorPanel = new DisplayPanel(inputWorld);
        simulatorPanel.heightInit(200); //Set a base height so its not tiny
        

        //Establish Text Fields
        // JTextField heightChange = new JTextField(3);
        // JTextField stepsPerClick = new JTextField(3);

        // List<JTextField> textFields = Arrays.asList(stepsPerClick, heightChange);
        // String[] labels = {"Steps: ", "height: "};

        

        //Buttons
        JButton timeStepButton = new JButton("Step");
        timeStepButton.addActionListener(e -> {
            // ourWorld.printCells();
            ourWorld.worldTimestep();
            // ourWorld.printCells();
            repaint();
        });


        
        
        // Button Panel
        // Box buttonBar = Box.createHorizontalBox();
        // buttonBar.add(timeStepButton);
        // buttonBar.add(setMargButton);
        // buttonBar.add(setHeightButton);
        List<JButton> buttonList = Arrays.asList(timeStepButton);

        //Establish text field stuff
        JPanel infoBar = new JPanel();
        for (int i = 0; i < buttonList.size(); i++) {
            Box vBox = Box.createVerticalBox();
            // vBox.add(new JLabel(labels[i], null, JLabel.RIGHT));
            // //vBox.add(Box.createHorizontalStrut(1));
            // vBox.add(textFields.get(i));
            // vBox.add(Box.createHorizontalStrut(1));
            vBox.add(buttonList.get(i));
            infoBar.add(vBox);
        }

        //Control Panel
        Box controlPanel = Box.createVerticalBox();
        // controlPanel.add(Box.createVerticalStrut(1));
        controlPanel.add(infoBar);
        // controlPanel.add(Box.createVerticalStrut(1));
        // controlPanel.add(buttonBar);

        Box vBox2 = Box.createVerticalBox();
        // vBox2.add(Box.createVerticalStrut(1));
        vBox2.add(simulatorPanel);
        vBox2.add(controlPanel);
        // vBox2.add(Box.createVerticalStrut(1));
        // vBox2.add(Box.createVerticalStrut(1));
        //Zoom
        simulatorPanel.addMouseWheelListener(e -> {
            // System.out.println("mWheel: ");
            // System.out.println(e);
            if (e.getPreciseWheelRotation() > 0) {
                simulatorPanel.height++;
                // System.out.println("++");
                repaint();
            } else {
                simulatorPanel.height--;
                // System.out.println("--");
                repaint();
            }
        });


        Container c = this.getContentPane();
        c.add(vBox2);
    }

    public static void displayThisWorld(World inputWorld) {
        displayWorld dWorld = new displayWorld(inputWorld);
        dWorld.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dWorld.setSize(600, 400);
        dWorld.setLocation(200,200);
        dWorld.setVisible(true);
    }

    public static void main(String[] args) {
        World thisWorld = new World(10, 10, 10, "Steve", 5, 2);
        displayWorld dWorld = new displayWorld(thisWorld);
        dWorld.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dWorld.setSize(600, 600);
        dWorld.setLocation(0,0);
        dWorld.setVisible(true);


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