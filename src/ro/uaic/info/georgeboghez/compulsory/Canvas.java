package ro.uaic.info.georgeboghez.compulsory;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A canvas (on which ellipses or rectangles will be painted) that extends the JPanel component which will be placed inside the frame.
 */
public class Canvas extends JPanel implements Serializable {
    /**
     * stores the points where the ellipses are painted.
     */
    private ArrayList<Point> points;
    /**
     * stores the widths of the ellipses
     */
    private ArrayList<Integer> widths;
    /**
     * stores the heights of the ellipses
     */
    private ArrayList<Integer> heights;
    /**
     * stores the colors of the ellipses
     */
    private ArrayList<Color> colors;
    /**
     * current width of an ellipse
     */
    private int w = 50;
    /**
     * current height of an ellipse
     */
    private int h = 50;
    /**
     * an arraylist to remember the order in which the shapes of the objects have been painted (rectangles or circles)
     */
    private ArrayList<Boolean> shapeHistory;
    /**
     * an arraylist to remember the order in which the fill property of the objects have been painted (rectangles or circles)
     */
    private ArrayList<Boolean> fillHistory;
    /**
     * a boolean to remember the current shape (circle or rectangle)
     */
    private boolean isCircle = true;
    /**
     * a boolean to remember if the shapes drawn from now on will be filled or not
     */
    private boolean filled = true;

    /**
     * the constructor will allocate the needed memory and the mouseListener is created telling the canvas to repaint the shapes and a new one when the canvas clicked
     */
    public Canvas() {
        setBackground(Color.WHITE);
        points = new ArrayList<Point>();
        widths = new ArrayList<Integer>();
        heights = new ArrayList<Integer>();
        colors = new ArrayList<Color>();
        shapeHistory = new ArrayList<Boolean>();
        fillHistory = new ArrayList<Boolean>();
        setBackground(Color.WHITE);
        /**
         * When mouse is pressed on the canvas, the canvas will be repainted with the previously painted ellipses and a new one with the current specifications (size, a random color).
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Color[] cols = {Color.blue, Color.yellow, Color.red, Color.green, Color.black, Color.magenta};
                shapeHistory.add(isCircle);
                fillHistory.add(filled);

                points.add(new Point(e.getX() - w / 2, e.getY() - h / 2));
                widths.add(w);
                heights.add(h);
                colors.add(cols[new Random().nextInt(cols.length)]);
                repaint();
            }
        });
    }

    /**
     * A way to set the new width and height of the ellipse.
     * @param w
     * @param h
     */
    public void setWidthAndHeight(int w, int h) {
        this.w = w;
        this.h = h;
    }

    /**
     * gets the state of the shape (if it is a circle or a rectangle)
     * @return a boolean stating if the shape is a circle not
     */
    public boolean getIsCircle() {
        return isCircle;
    }

    /**
     * sets the current and future shapes to be circles or rectangles
     * @param isCircle
     */
    public void setIsCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    /**
     * gets a state of the fill property a shape has
     * @return a boolean stating if the shapes are going to be filled or not
     */
    public boolean getFilled() {
        return filled;
    }

    /**
     * sets the current and future fill property of the shapes
     * @param filled
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /**
     * saves through serialization the Canvas object (bytes, not text)
     * @return a boolean representing if the save process has gone well or not
     */
    public boolean saveCanvas() {
        try {
            String homedirPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
            FileOutputStream fileOutputStream = new FileOutputStream(homedirPath + "\\canvas.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);

            out.writeObject(this);
            out.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * loads an object previously written to a file through serialization (bytes)
     * @param filename
     * @return
     */
    public static Canvas loadCanvas(String filename) {
        Canvas obj = new Canvas();
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);


            obj = (Canvas) in.readObject();
            in.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    /**
     * This will paint on the canvas the ellipses and/or the rectangles.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < colors.size(); ++i) {
            g2.setColor(colors.get(i));
            if(shapeHistory.get(i)) {
                if(fillHistory.get(i)) {
                    g2.fillOval(points.get(i).x, points.get(i).y, widths.get(i), heights.get(i));
                }
                else {
                    g2.drawOval(points.get(i).x, points.get(i).y, widths.get(i), heights.get(i));
                }
            }
            else {
                if(fillHistory.get(i)) {
                    g2.fillRect(points.get(i).x, points.get(i).y, widths.get(i), heights.get(i));
                }
                else {
                    g2.drawRect(points.get(i).x, points.get(i).y, widths.get(i), heights.get(i));
                }
            }
        }
    }
}