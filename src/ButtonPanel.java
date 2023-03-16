import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.awt.BorderLayout;

public class ButtonPanel extends JPanel {

    JButton step, endGeneration, survivalZoneListClear, addTosurvivalZone;
    DisplayWorld dWorld;
    JLabel szX0, szY0, szX1, szY1, errorLabel;
    JTextField survivalZoneX0, survivalZoneY0, survivalZoneX1, survivalZoneY1;

    int x0 = 0;
    int y0 = 0;
    int x1 = 0;
    int y1 = 0;
    
    // JTextField survivalZoneX0, survivalZoneY0, survivalZoneX1, survivalZoneY1;

    public ButtonPanel() {
        this.setFocusable(false);

        init();
    }

    public ButtonPanel(DisplayWorld dWorld) {
        this.dWorld = dWorld;
        this.setFocusable(false);
        
        init();
        
        //Set Button Actions
        step.addActionListener(e -> {
            dWorld.ourWorld.worldTimestep();
            dWorld.dwFrame.repaint();
        });

        endGeneration.addActionListener(e -> {
            try {
            dWorld.ourWorld.endGeneration();
            }
            catch (Exception IllegalAccessException) {
                errorLabel.setText("ERROR: NO WIN ZONES DECLAREAD");
            }
            dWorld.dwFrame.repaint();            
        });

        survivalZoneListClear.addActionListener(e -> {
            dWorld.ourWorld.survivalZoneListClear();
            dWorld.dwFrame.repaint();
        });

        //Using boolean methods to break out of action listener
        addTosurvivalZone.addActionListener(e -> {
            if (parseCoordInts()) {
                if (checkIntsBigger()) {
                    int[][] tempSum = {{x0,y0},{x1,y1}};
                    dWorld.ourWorld.addSurvivalCondition(tempSum);
                    errorLabel.setText("Succesfully added condition");
                    survivalZoneX0.setText("");
                    survivalZoneX1.setText("");
                    survivalZoneY0.setText("");
                    survivalZoneY1.setText("");
                }
            }

        });

    }

    private boolean parseCoordInts() {
        try {
            x0 = Integer.parseInt(survivalZoneX0.getText());
            y0 = Integer.parseInt(survivalZoneY0.getText());
            x1 = Integer.parseInt(survivalZoneX1.getText());
            y1 = Integer.parseInt(survivalZoneY1.getText());
            if (x0 < 0 || y0 < 0 || x1 < 0 || y1 < 0) {
                errorLabel.setText("ERROR: All Coordinates Must Be Positive");
                return(false);
            }
        } catch (NumberFormatException parseIntException) {
            errorLabel.setText("ERROR: All Coordinates Must Be Integers");
            return(false);
        }
        return(true);
    }

    private boolean checkIntsBigger() {
        if (x1 < x0) {
            errorLabel.setText("ERROR: X1 Must Be Greater Than X0");
            return(false);
        } else if (y1 < y0) {
            errorLabel.setText("ERROR: Y1 must be greater than Y0");
            return(false);
        } else {
            return(true);
        }
    }

    private void init() {
        step = new JButton("Step");
        endGeneration = new JButton("End Generation");
        survivalZoneListClear = new JButton("Clear Survival Zone List");
        addTosurvivalZone = new JButton("Add Box To Survival Zone");

        survivalZoneX0 = new JTextField(1);
        survivalZoneY0 = new JTextField(1);
        survivalZoneX1 = new JTextField(1);
        survivalZoneY1 = new JTextField(1);
        
        szX0 = new JLabel("SZ X0", JLabel.CENTER);
        szX1 = new JLabel("SZ X1", JLabel.CENTER);
        szY0 = new JLabel("SZ Y0", JLabel.CENTER);
        szY1 = new JLabel("SZ Y1", JLabel.CENTER);
        errorLabel = new JLabel("No Errors", JLabel.CENTER);
        

        List<JComponent> annoying = Arrays.asList(step, endGeneration, survivalZoneListClear, addTosurvivalZone);
        for (JComponent component : annoying) {
            component.setFocusable(false);
        }

        //Creating a Box For the Buttons and Adding Them
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(step, BorderLayout.SOUTH);
        buttonBox.add(endGeneration, BorderLayout.SOUTH);
        buttonBox.add(survivalZoneListClear, BorderLayout.SOUTH);
        Box buttonBoxBox = Box.createVerticalBox();
        errorLabel.setAlignmentX(Box.CENTER_ALIGNMENT);
        buttonBoxBox.add(errorLabel);
        buttonBoxBox.add(buttonBox);
        
 
        //Create vertical Box For Each Text Field and Add It
        Box x0Box = Box.createVerticalBox();
        Box x1Box = Box.createVerticalBox();
        Box y0Box = Box.createVerticalBox();
        Box y1Box = Box.createVerticalBox();
        x0Box.add(survivalZoneX0, BorderLayout.CENTER);
        szX0.setAlignmentX(Box.CENTER_ALIGNMENT);
        x0Box.add(szX0);
        y0Box.add(survivalZoneY0, BorderLayout.CENTER);
        szY0.setAlignmentX(Box.CENTER_ALIGNMENT);
        y0Box.add(szY0);
        x1Box.add(survivalZoneX1, BorderLayout.CENTER);
        szX1.setAlignmentX(Box.CENTER_ALIGNMENT);
        x1Box.add(szX1);
        y1Box.add(survivalZoneY1, BorderLayout.CENTER);
        szY1.setAlignmentX(Box.CENTER_ALIGNMENT);
        y1Box.add(szY1);

        Box coordinateBox = Box.createHorizontalBox();
        coordinateBox.add(x0Box);
        coordinateBox.add(Box.createHorizontalStrut(20));
        coordinateBox.add(y0Box);
        coordinateBox.add(Box.createHorizontalStrut(20));
        coordinateBox.add(x1Box);
        coordinateBox.add(Box.createHorizontalStrut(20));
        coordinateBox.add(y1Box);

        Box compilationBox = Box.createVerticalBox();
        compilationBox.add(buttonBoxBox);
        compilationBox.add(Box.createVerticalStrut(40));
        compilationBox.add(coordinateBox);
        compilationBox.add(Box.createVerticalStrut(10));
        addTosurvivalZone.setAlignmentX(Box.CENTER_ALIGNMENT);
        compilationBox.add(addTosurvivalZone);
        compilationBox.setFocusable(false);
        this.add(compilationBox);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new ButtonPanel());
        frame.setVisible(true);
    }
}
