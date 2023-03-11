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

        //Establish Text Fields
        JTextField margChange = new JTextField(3);
        JTextField heightChange = new JTextField(3);
        JTextField stepsPerClick = new JTextField(3);

        List<JTextField> textFields = Arrays.asList(stepsPerClick, margChange, heightChange);
        String[] labels = {"Steps: ", "marg: ", "height: "};

        

        //Buttons
        JButton timeStepButton = new JButton("Step");
        timeStepButton.addActionListener(e -> {
            ourWorld.worldTimestep();
            repaint();
        });

        JButton setMargButton = new JButton("Set Marg");
        setMargButton.addActionListener(e -> {
            simulatorPanel.changeMarg(Integer.parseInt(margChange.getText()));
        });

        JButton setHeightButton = new JButton("Set Height");
        setHeightButton.addActionListener(e -> {
            // simulatorPanel.height = Integer.parseInt(heightChange.getText());
            // repaint();
        });
        
        // Button Panel
        // Box buttonBar = Box.createHorizontalBox();
        // buttonBar.add(timeStepButton);
        // buttonBar.add(setMargButton);
        // buttonBar.add(setHeightButton);
        List<JButton> buttonList = Arrays.asList(timeStepButton, setMargButton, setHeightButton);

        //Establish text field stuff
        JPanel infoBar = new JPanel();
        for (int i = 0; i < textFields.size(); i++) {
            Box vBox = Box.createVerticalBox();
            vBox.add(new JLabel(labels[i], null, JLabel.RIGHT));
            //vBox.add(Box.createHorizontalStrut(1));
            vBox.add(textFields.get(i));
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
        c.add(simulatorPanel);
    }

    public static void displayThisWorld(World inputWorld) {
        displayWorld dWorld = new displayWorld(inputWorld);
        dWorld.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dWorld.setSize(600, 400);
        dWorld.setLocation(200,200);
        dWorld.setVisible(true);
    }

    public static void main(String[] args) {
        World thisWorld = new World(10, 10, 10, "Steve", 2, 2);
        displayWorld dWorld = new displayWorld(thisWorld);
        dWorld.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dWorld.setSize(600, 600);
        dWorld.setLocation(0,0);
        dWorld.setVisible(true);
    }

}