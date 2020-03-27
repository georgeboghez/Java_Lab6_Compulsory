import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    /**
     * the containter which includes every component
     */
    private JFrame frame = new JFrame();
    /**
     * the configuration panel that has the label which specifies a shape and its size
     */
    private JPanel configurationPanel = new JPanel();
    /**
     * the canvas on which we will paint
     */
    private EllipseCanvas canvas = new EllipseCanvas();
    /**
     * the bottom panel which has the control buttons
     */
    private JPanel controlPanel = new JPanel();
    /**
     * the save button which will save the canvas through serialization (not yet implemented)
     */
    private JButton saveButton = new JButton("Save");
    /**
     * the load button which will load a canvas (not yet implemented)
     */
    private JButton loadButton = new JButton("Load");
    /**
     * the reset button which will reset the canvas (not yet implemented)
     */
    private JButton resetButton = new JButton("Reset");
    /**
     * the exit button which will close the window
     */
    private JButton exitButton = new JButton("Exit");
    /**
     * the text field which holds the specified size of an ellipse (press enter after writing a number)
     */
    private JTextField sizeField = new JTextField("50",3);

    private Main() {
        frame.setLayout(new BorderLayout());
        frame.setTitle("Painting App");
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        configurationPanel.setBackground(Color.GRAY);
        configurationPanel.add(new JLabel("Circle"));
        /**
         * When an action is performed (enter is pressed after introducing a new number), the ellipse's size will change.
         */
        sizeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int improvisedWidthHeigth;
                try {
                    improvisedWidthHeigth = Integer.parseInt(e.getActionCommand());
                    canvas.setWidthAndHeight(improvisedWidthHeigth, improvisedWidthHeigth);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });

        /**
         * When button is pressed, the app will close itself.
         */
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /**
         * I'll implement it for next week
         */
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //will do it
            }
        });

        /**
         * I'll implement it for next week
         */
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //will do it
            }
        });

        /**
         * This one too
         */
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //will do it
            }
        });

        configurationPanel.add(sizeField);

        frame.add(configurationPanel, BorderLayout.NORTH);

        frame.add(canvas, BorderLayout.CENTER);

        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(resetButton);
        controlPanel.add(exitButton);

        frame.add(controlPanel,BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
