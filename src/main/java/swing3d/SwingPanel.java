package swing3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SwingPanel extends JPanel implements ActionListener {
// a SwingPanel is a kind of JPanel
// and
// a SwingPanel is a kind of ActionListener

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
    private Polygon3D poly;
    private Matrix4X4 spinner;

    public SwingPanel() {
        Timer timer = new Timer(20, this);
        timer.start();

//        int p = this.points;
//        double x = this.centerX;
//        double y = this.centerY;
//        double r0 = this.minorRadius;
//        double r1 = this.majorRadius;
//        this.shape = makeStar(p, x, y, r0, r1);
        this.poly = new Polygon3D(5, 0.6);
        Matrix4X4 a = new Matrix4X4();
        a.rotationX(Math.PI / 112);

        Matrix4X4 b = new Matrix4X4();
        b.rotationY(Math.PI / 144);

        Matrix4X4 c = new Matrix4X4();
        c.rotationZ(0);

        this.spinner = a.multiply(b).multiply(c);
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

        this.shape = poly.getShape();

        Shape s = transform.createTransformedShape(this.shape);

        g2D.setColor(this.getColour());
        g2D.fill(s);
    } // paintComponent( Graphics )

    private Shape makeStar(int points,
            double centerX, double centerY,
            double minorRadius, double majorRadius) {

        GeneralPath star = new GeneralPath();

        double x = centerX + majorRadius * Math.cos(0.0);
        double y = centerY + majorRadius * Math.sin(0.0);
        star.moveTo(x, y);
        for (int i = 1; i < 2 * points; i++) {
            double fraction = ((double) i) / (2 * points);
            double angle = 2.0 * Math.PI * fraction;

            if (i % 2 == 0) {
                x = centerX + majorRadius * Math.cos(angle);
                y = centerY + majorRadius * Math.sin(angle);
            } // if
            else {
                x = centerX + minorRadius * Math.cos(angle);
                y = centerY + minorRadius * Math.sin(angle);
            } // else
            star.lineTo(x, y);
        } // for
        star.closePath();

        return star;
    } // makeStar()

    @Override
    public void actionPerformed(ActionEvent event) {
        
        this.poly.transform(spinner);
        this.repaint();
    } // actionPerformed( ActionEvent )

} // SwingPanel
