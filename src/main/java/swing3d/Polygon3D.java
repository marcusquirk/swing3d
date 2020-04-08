package swing3d;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

public class Polygon3D {

    private final List<Vector> vertices = new ArrayList<>();
    private final int layers = 100;

    public Polygon3D(int numberOfSides, double radius, double depth) {
        for(int j = 0; j<layers; j++){
        for (int i = 0; i < numberOfSides; i++) {
            double fraction = ((double) i) / numberOfSides;
            double angle = fraction * 2.0 * Math.PI;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            Vector v = new Vector(x, y-depth/2+(j*depth/layers), 0.0);
            this.vertices.add(v);
        } // for
        }//for
        
    } // Polygon3D( int, double )

    public void transform(Matrix4X4 m) {
        for (Vector u : this.vertices) {
            u.set( m.multiply(u) );
        } // for 
    } // transform( Matrix4X4 )

    public Shape getShape() {
        GeneralPath path = new GeneralPath();
        
        int size = this.vertices.size()/layers;

        for (int j = 0; j<layers; j++){
        Vector v = this.vertices.get(0+j*size);
        double x = v.get(0);
        double y = v.get(1);
        path.moveTo(x, y);

        for (int i = 1; i < size; i++) {
            v = this.vertices.get(i+j*size);
            x = v.get(0);
            y = v.get(1);
            path.lineTo(x, y);
        } // for
        }//for



        path.closePath();

        return path;
    } // getShape()

} // Polygon3D