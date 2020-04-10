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

    private double centerX = 0.0;
    private double centerY = 0.0;

    private double deltaX = Math.random() / 20;
    private double deltaY = Math.random() / 20;
    private double deltaAngle = 2 * Math.PI / 180;
    private double phase = 0.0;
    private Shape shape;

    private Color color = Color.red;
    private List<Polygon3D> poly = new ArrayList<>();
    private int NUM_POLYGON = 1;
    private int NUM_SIDES = 6;
    private String TYPE = "normal";
    private List<Matrix4X4> spinner = new ArrayList<>();

    private Vector ILLUMINATION_VECTOR = new Vector(-1, -1, 0).normalise();
    
    int triggeredCounter = 0;

    public SwingPanel() {
        Timer timer = new Timer(30, this);
        timer.start();

        //create a spinner matrix
        Matrix4X4 a = new Matrix4X4();
        a.rotationX(-0.01);
        Matrix4X4 b = new Matrix4X4();
        b.rotationY(0.04);
        Matrix4X4 c = new Matrix4X4();
        c.rotationZ(0.025);

        for (int i = 0; i < NUM_POLYGON; i++) {
            this.poly.add(new Polygon3D(NUM_SIDES, 0.6, 1, TYPE));
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

        draw3DShape(transform, g2D, poly.get(0));

    } // paintComponent( Graphics )

    @Override
    public void actionPerformed(ActionEvent event) {

        for (int i = 0; i < NUM_POLYGON; i++) {
            this.poly.get(i).transform(spinner.get(i));
        }//for
        this.repaint();
    } // actionPerformed( ActionEvent )

    public void draw3DShape(AffineTransform transform, Graphics2D g2D,
            Polygon3D poly) {
        System.out.println("counter"+triggeredCounter);
        List<Shape> s = new ArrayList<>();
        Boolean TopZ = poly.getTopZ() < poly.getBottomZ();
        if (TopZ) {
            if(TYPE.equals("cone")){
                this.shape = poly.getShapeTop();
            }
            else{this.shape = poly.getShapeBottom();}
            g2D.setColor(getIlluminationBottom(poly));
        } else {
            this.shape = poly.getShapeTop();
            g2D.setColor(getIlluminationTop(poly));
        }
        s.add(transform.createTransformedShape(this.shape));
        g2D.fill(s.get(0));
        
        ArrayList<Integer> order = poly.sortSides();

        int fillCounter = 1;
        for (int i : order) {
            this.shape = poly.getShapeSide(i);
            s.add(transform.createTransformedShape(this.shape));
            g2D.setColor(getIlluminationSide(poly, i));
            g2D.fill(s.get(fillCounter));
            fillCounter++;
        }//for

        if (TopZ != true) {
            this.shape = poly.getShapeBottom();
            g2D.setColor(getIlluminationBottom(poly));
            s.add(transform.createTransformedShape(this.shape));
            g2D.fill(s.get(NUM_SIDES + 1));

        } else {
            this.shape = poly.getShapeTop();
            g2D.setColor(getIlluminationTop(poly));
            s.add(transform.createTransformedShape(this.shape));
            g2D.fill(s.get(NUM_SIDES + 1));
        }


    }

    public Color getIlluminationTop(Polygon3D poly) {
        double illFactor = poly.getNormalTop().dot(ILLUMINATION_VECTOR);
        if (illFactor < 0.0) {
            illFactor = 0.0;
        } else if (illFactor > 1) {
            illFactor = 1;
        }
        return new Color((int) (this.getColour().getRed() * illFactor),
                (int) (this.getColour().getGreen() * illFactor),
                (int) (this.getColour().getBlue() * illFactor));
    }

    public Color getIlluminationBottom(Polygon3D poly) {
        double illFactor = poly.getNormalBottom().dot(ILLUMINATION_VECTOR);
        if (illFactor < 0.0) {
            illFactor = 0.0;
        } else if (illFactor > 1) {
            illFactor = 1;
        }
        return new Color((int) (this.getColour().getRed() * illFactor),
                (int) (this.getColour().getGreen() * illFactor),
                (int) (this.getColour().getBlue() * illFactor));
    }

    public Color getIlluminationSide(Polygon3D poly, int firstSide) {
        double illFactor = poly.getNormalSide(firstSide).dot(ILLUMINATION_VECTOR);
        if (illFactor < 0.0) {
            illFactor = 0.0;
        } else if (illFactor > 1) {
            illFactor = 1;
        }
        return new Color((int) (this.getColour().getRed() * illFactor),
                (int) (this.getColour().getGreen() * illFactor),
                (int) (this.getColour().getBlue() * illFactor));
    }

    public Color getIlluminationFinalSide(Polygon3D poly) {
        double illFactor = poly.getNormalFinalSide().dot(ILLUMINATION_VECTOR);
        if (illFactor < 0.0) {
            illFactor = 0.0;
        } else if (illFactor > 1) {
            illFactor = 1;
        }
        return new Color((int) (this.getColour().getRed() * illFactor),
                (int) (this.getColour().getGreen() * illFactor),
                (int) (this.getColour().getBlue() * illFactor));
    }

} // SwingPanel
