package ro.uaic.info.georgeboghez.compulsory;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    /**
     * the containter which includes every component
     */
    private static JFrame frame = new JFrame();
    /**
     * the configuration panel that has the label which specifies a shape and its size
     */
    private static JPanel configurationPanel = new JPanel();
    /**
     * the canvas on which we will paint
     */
    private static ro.uaic.info.georgeboghez.compulsory.Canvas canvas = new ro.uaic.info.georgeboghez.compulsory.Canvas();
    /**
     * the bottom panel which has the control buttons
     */
    private static JPanel controlPanel = new JPanel();
    /**
     * the save button which will save the canvas through serialization (not yet implemented)
     */
    private static JButton saveButton = new JButton("Save");
    /**
     * the load button which will load a canvas (not yet implemented)
     */
    private static JButton loadButton = new JButton("Load");
    /**
     * the reset button which will reset the canvas (not yet implemented)
     */
    private static JButton resetButton = new JButton("Reset");
    /**
     * the exit button which will close the window
     */
    private static JButton exitButton = new JButton("Exit");
    /**
     * the text field which holds the specified size of an ellipse (press enter after writing a number)
     */
    private static JTextField sizeField = new JTextField("50",3);
    /**
     * a group of radio buttons to select the shape of the drawn figure
     */
    private static ButtonGroup radioBtnGroup = new ButtonGroup();
    /**
     * a radio button which sets the future's drawn figures to be circles
     */
    private static JRadioButton circleRadioButton = new JRadioButton("Circle");
    /**
     * a radio button which sets the future's drawn figures to be rectangles
     */
    private static JRadioButton rectangleRadioButton = new JRadioButton("Rectangle");
    /**
     * a checkbox for making the shape to be filled or not
     */
    private static JCheckBox filledCheckBox = new JCheckBox("Fill Shape");

    public static void main(String[] args) {
        new Main();
    }

    /**
     * creates the frame, sets the layout, and adds the panels
     */
    private Main() {
        frame.setLayout(new BorderLayout());
        frame.setTitle("Painting App");
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupButtons();
        setupConfigurationPanel();
        setupTheControlPanel();

        frame.add(configurationPanel, BorderLayout.NORTH);
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(controlPanel,BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * sets up the configuration panel
     */
    private static void setupConfigurationPanel() {
        configurationPanel.setBackground(Color.GRAY);
        setupSizeField();

        configurationPanel.add(circleRadioButton);
        configurationPanel.add(rectangleRadioButton);

        configurationPanel.add(new JLabel("Size"));
        configurationPanel.add(sizeField);
        configurationPanel.add(filledCheckBox);
    }

    /**
     * sets up the control panel
     */
    private static void setupTheControlPanel() {
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(resetButton);
        controlPanel.add(exitButton);
    }

    /**
     * sets up the buttons' functionalities
     */
    private static void setupButtons() {
        setupRadioButtons();
        setupCheckboxButton();
        setupExitButton();
        setupSaveButton();
        setupLoadButton();
        setupResetButton();
    }

    /**
     * sets up the group of radio buttons
     */
    private static void setupRadioButtons() {
        radioBtnGroup.add(circleRadioButton);
        circleRadioButton.setSelected(true);
        radioBtnGroup.add(rectangleRadioButton);
        setupCircleRadioButton();
        setupRectangleRadioButton();
    }

    /**
     * it configures the fill rectangle radio button, having it set the canvas' future shapes to be circles
     */
    private static void setupCircleRadioButton() {
        circleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setIsCircle(true);
            }
        });
    }

    /**
     * it configures the fill rectangle radio button, having it set the canvas' future shapes to be rectangles
     */
    private static void setupRectangleRadioButton() {
        rectangleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setIsCircle(false);
            }
        });
    }

    /**
     * it configures the fill checkbox, having it set the canvas' future shapes to be filled
     */
    private static void setupCheckboxButton() {
        filledCheckBox.setSelected(true);
        filledCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(canvas.getFilled()) {
                    canvas.setFilled(false);
                }
                else {
                    canvas.setFilled(true);
                }

            }
        });
    }

    /**
     * it configures the exit button, therefore making the program close itself when the button clicked
     */
    private static void setupExitButton() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * it configures the save button, therefore when the button is clicked, the Canvas object will be saved to a file on the user's Desktop
     */
    private static void setupSaveButton() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.saveCanvas();
            }
        });
    }

    /**
     * it configures the load button, therefore when the load button is clicked, the static method loadCanvas from class Canvas is called in order to load an Canvas object from a file
     */
    private static void setupLoadButton() {
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(canvas);
                String homedirPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
                canvas = ro.uaic.info.georgeboghez.compulsory.Canvas.loadCanvas(homedirPath + "\\canvas.txt");
                assert canvas != null;
                canvas.revalidate();
                canvas.repaint();
                frame.revalidate();
                frame.repaint();
                frame.add(canvas);
                reconfigCanvas();
                frame.setVisible(true);
            }
        });
    }

    /**
     * it configures the reset button, therefore when the button is clicked, the canvas will be deleted
     */
    private static void setupResetButton() {
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(canvas);
                canvas = new Canvas();
                frame.add(canvas, BorderLayout.CENTER);
                reconfigCanvas();
                frame.setVisible(true);
            }
        });
    }

    /**
     * it reconfigures the properties of the canvas in order to continue with the previous selected properties when resetting the canvas
     */
    private static void reconfigCanvas() {
        canvas.setFilled(filledCheckBox.isSelected());
        canvas.setWidthAndHeight(Integer.parseInt(sizeField.getText()), Integer.parseInt(sizeField.getText()));
        if(circleRadioButton.isSelected()) {
            canvas.setIsCircle(true);
        }
        else {
            canvas.setIsCircle(false);
        }
    }

    /**
     * When an action is performed (enter is pressed after introducing a new number), the shape's size will change.
     */
    private static void setupSizeField() {
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
    }

}
