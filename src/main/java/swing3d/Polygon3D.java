package swing3d;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

public class Polygon3D {

    private final List<Vector> vertices = new ArrayList<>();
    private final int layers = 2;

    public Polygon3D(int numberOfSides, double radius, double depth) {
        for (int j = 0; j < layers; j++) {
            for (int i = 0; i < numberOfSides; i++) {
                double fraction = ((double) i) / numberOfSides;
                double angle = fraction * 2.0 * Math.PI;
                double x = radius * Math.cos(angle);
                double y = radius * Math.sin(angle);
                Vector v = new Vector(x, y, (j * depth) - (depth / 2));
                this.vertices.add(v);
            } // for
        }//for

    } // Polygon3D( int, double )

    public void transform(Matrix4X4 m) {
        for (Vector u : this.vertices) {
            u.set(m.multiply(u));
        } // for 
    } // transform( Matrix4X4 )

    public Shape getShape() {
        GeneralPath path = new GeneralPath();
        int size = this.vertices.size() / layers;

        for (int j = 0; j < layers; j++) {
            Vector v = this.vertices.get(0 + j * size);
            double x = v.get(0);
            double y = v.get(1);
            path.moveTo(x, y);

            for (int i = 1; i < size; i++) {
                v = this.vertices.get(i + j * size);
                x = v.get(0);
                y = v.get(1);
                path.lineTo(x, y);
            } // for
        }//for
        path.closePath();
        return path;
    } // getShape()

    public Shape getShapeBottom() {
        GeneralPath path = new GeneralPath();
        int size = this.vertices.size() / layers;

        Vector v = this.vertices.get(size);
        double x = v.get(0);
        double y = v.get(1);
        path.moveTo(x, y);

        for (int i = size; i < size * layers; i++) {
            v = this.vertices.get(i);
            x = v.get(0);
            y = v.get(1);
            path.lineTo(x, y);
        }
        path.closePath();
        return path;
    }

    public Vector getNormalBottom() {
        List<Vector> vectors = new ArrayList<>();
        int size = this.vertices.size() / layers;
        vectors.add(this.vertices.get(size));
        vectors.add(this.vertices.get(size + 1));        
        vectors.add(this.vertices.get(size + 2));
        vectors.add(vectors.get(0).subtract(vectors.get(1)));
        vectors.add(vectors.get(2).subtract(vectors.get(1)));
        vectors.add(vectors.get(3).cross(vectors.get(4)).normalise());
        return vectors.get(5);
    }

    public Shape getShapeTop() {
        GeneralPath path = new GeneralPath();
        int size = this.vertices.size() / layers;

        Vector v = this.vertices.get(0);
        double x = v.get(0);
        double y = v.get(1);
        path.moveTo(x, y);

        for (int i = 1; i < size; i++) {
            v = this.vertices.get(i);
            x = v.get(0);
            y = v.get(1);
            path.lineTo(x, y);
        }
        path.closePath();
        return path;
    }
    
    public Vector getNormalTop() {
        List<Vector> vectors = new ArrayList<>();
        vectors.add(this.vertices.get(0));
        vectors.add(this.vertices.get(1));        
        vectors.add(this.vertices.get(2));
        vectors.add(vectors.get(0).subtract(vectors.get(1)));
        vectors.add(vectors.get(2).subtract(vectors.get(1)));
        vectors.add(vectors.get(3).cross(vectors.get(4)).normalise());
        return vectors.get(5);
    }

    public Shape getShapeSide(int firstSide) {
        GeneralPath path = new GeneralPath();
        int size = this.vertices.size() / layers;

        Vector v = this.vertices.get(firstSide + size);
        double x = v.get(0);
        double y = v.get(1);
        path.moveTo(x, y);

        v = this.vertices.get(firstSide);
        x = v.get(0);
        y = v.get(1);
        path.lineTo(x, y);

        v = this.vertices.get(firstSide + 1);
        x = v.get(0);
        y = v.get(1);
        path.lineTo(x, y);

        v = this.vertices.get(firstSide + size + 1);
        x = v.get(0);
        y = v.get(1);
        path.lineTo(x, y);

        path.closePath();
        return path;
    }
    
    public Vector getNormalSide(int firstSide) {
        int size = this.vertices.size() / layers;
        List<Vector> vectors = new ArrayList<>();
        vectors.add(this.vertices.get(firstSide + 1));
        vectors.add(this.vertices.get(firstSide));        
        vectors.add(this.vertices.get(firstSide +size));
        vectors.add(vectors.get(0).subtract(vectors.get(1)));
        vectors.add(vectors.get(2).subtract(vectors.get(1)));
        vectors.add(vectors.get(3).cross(vectors.get(4)).normalise());
        return vectors.get(5);
    }

    public Shape getShapeFinalSide() {
        GeneralPath path = new GeneralPath();
        int size = this.vertices.size() / layers;

        Vector v = this.vertices.get(2 * size - 1);
        double x = v.get(0);
        double y = v.get(1);
        path.moveTo(x, y);

        v = this.vertices.get(size - 1);
        x = v.get(0);
        y = v.get(1);
        path.lineTo(x, y);

        v = this.vertices.get(0);
        x = v.get(0);
        y = v.get(1);
        path.lineTo(x, y);

        v = this.vertices.get(size);
        x = v.get(0);
        y = v.get(1);
        path.lineTo(x, y);

        path.closePath();
        return path;
    }
    
    public Vector getNormalFinalSide() {
        int size = this.vertices.size() / layers;
        List<Vector> vectors = new ArrayList<>();
        vectors.add(this.vertices.get(2 * size - 1));
        vectors.add(this.vertices.get(size - 1));        
        vectors.add(this.vertices.get(0));
        vectors.add(vectors.get(0).subtract(vectors.get(1)));
        vectors.add(vectors.get(2).subtract(vectors.get(1)));
        vectors.add(vectors.get(3).cross(vectors.get(4)).normalise());
        return vectors.get(5);
    }

    public double getTopZ() {
        int size = this.vertices.size() / layers;
        Vector v = this.vertices.get(0);
        double currentZ = v.get(2);

        for (int i = 1; i < size; i++) {
            v = this.vertices.get(i);
            double z = v.get(2);
            if (z > currentZ) {
                currentZ = z;
            }
        }
        return currentZ;
    }//getTop()
    
    public int getTopTopVertex() {
        int size = this.vertices.size() / layers;
        Vector v = this.vertices.get(0);
        double currentZ = v.get(2);
        int currentVertex = 0;

        for (int i = 1; i < size; i++) {
            v = this.vertices.get(i);
            double z = v.get(2);
            if (z > currentZ) {
                currentZ = z;
                currentVertex = i;
            }
        }
        return currentVertex;
    }//getTopTopVertex()

    public double getBottomZ() {
        int size = this.vertices.size() / layers;
        Vector v = this.vertices.get(size);
        double currentZ = v.get(2);

        for (int i = size; i < size * layers; i++) {
            v = this.vertices.get(i);
            double z = v.get(2);
            if (z > currentZ) {
                currentZ = z;
            }
        }
        return currentZ;
    }//getTop()
    
    public int getBottomTopVertex() {
        int size = this.vertices.size() / layers;
        Vector v = this.vertices.get(size);
        double currentZ = v.get(2);
        int currentVertex = size;

        for (int i = size; i < size*layers; i++) {
            v = this.vertices.get(i);
            double z = v.get(2);
            if (z > currentZ) {
                currentZ = z;
                currentVertex = i;
            }
        }
        return currentVertex-size;
    }//getBottomTopVertex()

} // Polygon3D
