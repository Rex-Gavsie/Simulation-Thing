import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;

public class TestingStuff extends JPanel {

    JButton step, endGeneration, survivalZoneListClear, addTosurvivalZone;
    JLabel szX0, szY0, szX1, szY1;
    JTextField survivalZoneX0, survivalZoneY0, survivalZoneX1, survivalZoneY1;
    // JTextField survivalZoneX0, survivalZoneY0, survivalZoneX1, survivalZoneY1;

    public TestingStuff() {
        this.setFocusable(false);

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
        
        //Set Button Actions
        // step.addActionListener(e -> {
        //     dWorld.ourWorld.worldTimestep();
        //     dWorld.dwFrame.repaint();
        // });

        // endGeneration.addActionListener(e -> {
        //     dWorld.ourWorld.endGeneration();
        //     dWorld.dwFrame.repaint();
        // });

        //Semi Automated Set As Not Focusable
        List<JComponent> annoying = Arrays.asList(step, endGeneration, survivalZoneListClear, addTosurvivalZone);
        for (JComponent component : annoying) {
            component.setFocusable(false);
        }

        //Creating a Box For the Buttons and Adding Them
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(step, BorderLayout.NORTH);
        buttonBox.add(endGeneration, BorderLayout.NORTH);
        buttonBox.add(survivalZoneListClear, BorderLayout.NORTH);

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
        // compilationBox.add(Box.createRigidArea(new Dimension(0, 5)));
        // compilationBox.setLayout(new LayoutManager);
        compilationBox.add(buttonBox);
        compilationBox.add(Box.createVerticalStrut(20));
        compilationBox.add(coordinateBox);
        // this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(compilationBox);


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Testing Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new TestingStuff());
        frame.setVisible(true);
        
    }
}
