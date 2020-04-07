package swing3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A JPanel that models a bouncing shape, including a gravitational constant, a
 * damping constant, and the methods describing the shape bouncing off of the
 * frame boundaries; the coordinate system -1 to 1 has been removed.
 *
 * The velocity of the shape is randomly initialised. Gravity is initialised to
 * 1. Damping is initialised to 1.
 *
 * Note that damping only applies to the 'floor' of the frame. I chose to do
 * this because shapes that bounced freely off the walls without damping were
 * more interesting to watch.
 *
 * @author marcus
 */
public class SwingPanel extends JPanel implements ActionListener {

    Random rng = new Random();
    private double centerX = 0.0;
    private double centerY = -0.5;
    private double gravity = 0.003;
    private double damping = 0.003;
    private double radius = 0.1;
    private double rotationAngle = 0.0;
    private double rotationSpeed = 0.05 + (rng.nextDouble() / 10);
    private final double[] direction = new double[2];
    private Color colour = Color.red;
    private String shapeDraw = "Circle";

    /**
     * Initialises the Timer, and creates a randomly-generated velocity. The
     * shape's x and y velocity components are independent.
     */
    public SwingPanel() {
        Timer timer = new Timer(50, this);
        timer.start();
        direction[0] = -7.142 / 300 + (rng.nextDouble() / 20);
        direction[1] = -7.142 / 300 + (rng.nextDouble() / 20);
    } // SwingPanel()

    public double getCenterX() {
        return this.centerX;
    } // getCenterX()

    public void setCenterX(double x) {
        this.centerX = x;
    } // setCenterX( double )

    public double getCenterY() {
        return this.centerY;
    } // getCenterY()

    public void setCenterY(double y) {
        this.centerY = y;
    } // setCenterY( double )

    public double getRadius() {
        return this.radius;
    } // getRadius()

    public void setRadius(double r) {
        this.radius = r;
    } // setRadius( double )

    public Color getColour() {
        return this.colour;
    } // getColour()

    public void setColour(Color c) {
        this.colour = c;
    } // setColour( Color )

    public void setShape(String s) {
        this.shapeDraw = s;
    }

    /**
     * Create a new random velocity for the shape, with independent x and y
     * components.
     */
    public void setDirection() {
        direction[0] = -7.142 / 300 + (rng.nextDouble() / 20);
        direction[1] = -7.142 / 300 + (rng.nextDouble() / 20);
    }

    /**
     * Set gravity to a given g.
     *
     * @param g The chosen gravitational constant.
     */
    public void setGravity(double g) {
        gravity = g / 300;
    }

    /**
     * Set damping to a given d.
     *
     * @param d The chosen damping constant.
     */
    public void setDamping(double d) {
        damping = d / 300;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        int w = this.getWidth();
        int h = this.getHeight();

        AffineTransform transform = new AffineTransform();

        /*
        This code creates a -1 to 1 x-y coordinate system. Should you choose to
        re-implement it, all of the initial variables such as centerX, centerY,
        direction, and g must all be changed to more suitable values (likely
        very small, around 0.0-0.2)
         */
        AffineTransform scaling = new AffineTransform();
        scaling.setToScale(w / 2, h / 2);
        AffineTransform translation = new AffineTransform();
        translation.setToTranslation(1.0, 1.0);

        transform.concatenate(scaling);
        transform.concatenate(translation);

        if (shapeDraw == "Circle") {
            double d = this.radius;
            double ulx = this.centerX;
            double uly = this.centerY;
            Ellipse2D.Double circle = new Ellipse2D.Double(ulx, uly, d, d);

            Shape shape = transform.createTransformedShape(circle);
            g2D.setColor(colour);
            g2D.fill(shape);
        }//if
        else if (shapeDraw == "Square") {
            rotationAngle += rotationSpeed;
            Rectangle2D.Double square = new Rectangle2D.Double(centerX, centerY,
                    radius, radius);
            AffineTransform at = AffineTransform.getRotateInstance(rotationAngle, centerX, centerY);
            Shape shape = at.createTransformedShape(square);
            shape = transform.createTransformedShape(shape);
            g2D.setColor(colour);
            g2D.fill(shape);

        }
    } // paintComponent( Graphics )

    @Override
    public void actionPerformed(ActionEvent event) {
        // Each time tick, the shape moves in the direction set.
        this.centerY = this.centerY + (direction[0]);
        this.centerX = this.centerX + (direction[1]);

        //These describe situations for the shape to bounce off of a boundary
        if (Math.abs(this.centerY) > 0.9) {
            if (this.centerY > 0.9) {
                this.centerY = 0.89;
            } else {
                this.centerY = -0.89;
            }
            direction[0] = -direction[0] + damping; //bounce
            rotationSpeed = -rotationSpeed;
        } // if
//        else if (this.centerY + radius < 0) {
//            centerY = -radius;
//            direction[0] = -direction[0];
//            rotationSpeed = -rotationSpeed;
//        } // if
        if (this.centerX > 0.9) {
            this.centerX = 0.89;
            direction[1] = -direction[1];
            rotationSpeed = -rotationSpeed;
//        } else if (this.centerX < 0) {
//            centerX = 0;
//            direction[1] = -direction[1];
//            rotationSpeed = -rotationSpeed;
        }
        else if (this.centerX < -1.0){
            this.centerX = -0.99;
            direction[1] = -direction[1];
            rotationSpeed = -rotationSpeed;
        }
        direction[0] += gravity / 2; // each time tick, apply the g force
        this.repaint();
    } // actionPerformed( ActionEvent )

    public static void main(String[] args) {
        System.out.println("Colour");
    }

} // SwingPanel
