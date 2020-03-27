import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * A canvas (on which ellipses will be painted) that extends the JPanel component which will be placed inside the frame.
 */
public class EllipseCanvas extends JPanel {
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

    public EllipseCanvas() {
        setBackground(Color.WHITE);
        points = new ArrayList<Point>();
        widths = new ArrayList<Integer>();
        heights = new ArrayList<Integer>();
        colors = new ArrayList<Color>();
        setBackground(Color.WHITE);
        /**
         * When mouse is pressed on the canvas, the canvas will be repainted with the previously painted ellipses and a new one with the current specifications (size, a random color).
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Color[] cols = {Color.blue, Color.yellow, Color.red, Color.green, Color.black, Color.magenta};
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
     * This will paint on the canvas the ellipses.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < colors.size(); ++i) {
            g2.setColor(colors.get(i));
            g2.fillOval(points.get(i).x, points.get(i).y, widths.get(i), heights.get(i));
        }
    }
}