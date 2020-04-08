package swing3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SwingPanel extends JPanel implements ActionListener {

    private final int points = 8;
    private double centerX = 0.0;
    private double centerY = 0.0;
    private final double minorRadius = 0.2;
    private final double majorRadius = 0.3;

    private double deltaX = Math.random() / 20;
    private double deltaY = Math.random() / 20;
    private double deltaAngle = 2 * Math.PI / 180;
    private double phase = 0.0;
    private Shape shape;

    private Color color = Color.red;
    private List<Polygon3D> poly = new ArrayList<>();
    private int NUM_POLYGON = 1;
    private List<Matrix4X4> spinner = new ArrayList<>();

    public SwingPanel() {
        Timer timer = new Timer(20, this);
        timer.start();

        //create a spinner matrix
        Matrix4X4 a = new Matrix4X4();
        a.rotationX(0.0);
        Matrix4X4 b = new Matrix4X4();
        b.rotationY(0.0);
        Matrix4X4 c = new Matrix4X4();
        c.rotationZ(0.0);
        
        for (int i = 0; i < NUM_POLYGON; i++) {
            this.poly.add(new Polygon3D(5, 0.4, 0.6));
            this.spinner.add(a.multiply(b).multiply(c));
        }//for

//        Matrix4X4 translate = new Matrix4X4();
//        translate.set(1, 3, 0);
//        for (int i = 0; i < NUM_POLYGON; i++) {
//            translate.set(1, 3, translate.get(1, 3));
//            this.poly.get(i).transform(translate);
//        }//for
    } // SwingPanel()

    public Color getColour() {
        return this.color;
    } // getColor()

    public void setColour(Color c) {
        this.color = c;
    } // setColor( Color )

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        int w = this.getWidth();
        int h = this.getHeight();

        AffineTransform transform = new AffineTransform();

        AffineTransform rotation = new AffineTransform();
        rotation.setToRotation(this.phase);

        AffineTransform scaling = new AffineTransform();
        scaling.setToScale(w / 2, h / 2);

        AffineTransform translation = new AffineTransform();
        double cx = 1.0 + this.centerX;
        double cy = 1.0 + this.centerY;
        translation.setToTranslation(cx, cy);

        transform.concatenate(scaling);
        transform.concatenate(translation);
        transform.concatenate(rotation);

        List<Shape> s = new ArrayList<>();

        for (int i = 0; i < NUM_POLYGON; i++) {
            this.shape = poly.get(i).getShape();
            s.add(transform.createTransformedShape(this.shape));
            g2D.setColor(this.getColour());
            g2D.fill(s.get(i));
        }
    } // paintComponent( Graphics )

    @Override
    public void actionPerformed(ActionEvent event) {

        for (int i = 0; i < NUM_POLYGON; i++) {
            this.poly.get(i).transform(spinner.get(i));
        }//for
        this.repaint();
    } // actionPerformed( ActionEvent )

} // SwingPanel
