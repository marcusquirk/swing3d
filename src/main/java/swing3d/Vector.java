package swing3d;


/** 
 * This class models a four-component ("4D") vector, intended to be used to model
 * a 3D object in space. The x component is called u0, the y component u1, the z 
 * component u2, and the homogeneous component u3.
 * 
 * @author marcus
 */

public class Vector {

    // The x component is called u0, the y component u1, the z component u2, and
    // the homogeneous component u3.
    private double u0;
    private double u1;
    private double u2;
    private double u3;

    /**
     * A constructor which accepts three parameters: the x, y, z components of the
     * vector. The homogeneous component is set to 1.
     * 
     * @param u0 The vector's x component
     * @param u1 The vector's y component
     * @param u2 The vector's z component
     */
    public Vector(double u0, double u1, double u2) {
        this.u0 = u0;
        this.u1 = u1;
        this.u2 = u2;
        this.u3 = 1.0;
    } // Vector( double, double, double )

    
    /**
     * A constructor which accepts four parameters: the x, y, z components and the
     * homogeneous components of the vector.
     * 
     * @param u0 The vector's x component
     * @param u1 The vector's y component
     * @param u2 The vector's z component
     * @param u3 The vector's homogeneous component
     */
    public Vector(double u0, double u1, double u2, double u3) {
        this.u0 = u0;
        this.u1 = u1;
        this.u2 = u2;
        this.u3 = u3;
    } // Vector( double, double, double, double )

    /**
     * A constructor in which the x, y, z, and homogeneous components are all set
     * to 0.
     */
    public Vector() {
        this.u0 = 0.0;
        this.u1 = 0.0;
        this.u2 = 0.0;
        this.u3 = 0.0;
    } // Vector()

    /**
     * Returns the sum of the vector with another vector.
     * 
     * @param v The other vector whose sum is to be returned
     * @return The sum of this vector with another vector.
     */
    public Vector add(Vector v) {
        return new Vector(this.get(0) + v.get(0), this.get(1) + v.get(1),
                this.get(2) + v.get(2), this.get(3) + v.get(3));
    } // add( Vector2D )

    public Vector subtract(Vector v) {
        return new Vector(this.get(0) - v.get(0), this.get(1) - v.get(1),
                this.get(2) - v.get(2), this.get(3));
    } // add( Vector2D )
    
    /**
     * Returns a vector scaled in each direction with different factors .
     * 
     * @param factor1 The scale factor for the x component.
     * @param factor2 The scale factor for the y component.
     * @param factor3 The scale factor for the z component.
     * @param factor4 The scale factor for the homogeneous component.
     * @return The scaled vector.
     */
    public Vector scale(double factor1, double factor2, double factor3,
            double factor4) {
        return new Vector(factor1 * this.u0, factor2 * this.u1,
                factor3 * this.u2, factor4 * this.u3);
    } // scale( double )

    
    /**
     * Returns a vector scaled in every direction by a single factor.
     * 
     * @param factor The scale factor
     * @return The scaled vector
     */
    public Vector scale(double factor) {
        return new Vector(factor * this.u0, factor * this.u1,
                factor * this.u2, factor * this.u3);
    } // scale( double )

    /**
     * Returns a vector rotated about the x-axis by a given angle.
     * 
     * @param angle The angle to rotate by.
     * @return The rotated vector.
     */
    public Vector rotateX(double angle) {
        Matrix4X4 rotationMatrix = new Matrix4X4();
        rotationMatrix.rotationX(angle);
        return rotationMatrix.multiply(this);
    } // rotate( double )

    /**
     * Returns the dot product of the vector with another vector.
     * 
     * @param v The other vector with which to calculate the dot product.
     * @return The dot product.
     */
    public double dot(Vector v) {
        double u0Dot = this.get(0) * v.get(0);
        double u1Dot = this.get(1) * v.get(1);
        double u2Dot = this.get(2) * v.get(2);
        double u3Dot = this.get(3) * v.get(3);
        return u0Dot + u1Dot + u2Dot + u3Dot;
    } // dotproduct ( Vector2D )

    /**
     * Returns the magnitude of the vector.
     * 
     * @return The magnitude of the vector.
     */
    public double magnitude() {
        double u0Sq = this.get(0) * this.get(0);
        double u1Sq = this.get(1) * this.get(1);
        double u2Sq = this.get(2) * this.get(2);
        double u3Sq = this.get(3) * this.get(3);
        return Math.sqrt(u0Sq + u1Sq + u2Sq + u3Sq);
    } // magnitude ( Vector2D )

    
    /**
     * Returns a given component of the vector. The parameter takes an integer,
     * where 0 returns u0, the x component; 1 returns u1, the y component; 2
     * returns u2, the z component; and 3 returns u3, the homogeneous component.
     * 
     * @param component The component to return.
     * @return The vector component
     */
    public double get(int component) {
        switch (component) {
            case 0:
                return this.u0;
            case 1:
                return this.u1;
            case 2:
                return this.u2;
            case 3:
                return this.u3;
            default:
                return this.u0;
        } //switch
    } // get()

    /**
     * Returns the vector in the same direction as this vector with a magnitude of
     * 1.
     * 
     * @return The normalised vector.
     */
    public Vector normalise() {
        double magnitude = this.magnitude();
        return new Vector(this.get(0) / magnitude, this.get(1) / magnitude,
                this.get(2) / magnitude, this.get(3) / magnitude);
    }
    
    /**
     * Returns the cross product of this vector with another vector.
     * 
     * @param v The other vector from which to calculate the cross product.
     * @return The cross product vector
     */
    public Vector cross(Vector v) {
        double newU0 = this.get(1)*v.get(2)-this.get(2)*v.get(1);
        double newU1 = this.get(2)*v.get(0)-this.get(0)*v.get(2);
        double newU2 = this.get(0)*v.get(1)-this.get(1)*v.get(0);
        return new Vector(newU0, newU1, newU2);
    }

    /**
     * Returns the vector in the string form "(u0, u1, u2, u3)"
     * 
     * @return The string form of the vector
     */
    @Override
    public String toString() {
        return "(" + get(0) + ", " + get(1) + ", " + get(2) + ", " + get(3)
                + ")";
    } // toString()
    
    /**
     * Main method for testing the Vector methods.
     * @param args 
     */
    public static void main(String[] args){
        Vector v1 = new Vector(7,8,2,0);
        Matrix4X4 m1 = new Matrix4X4();
        m1.set(1,3,7);
        m1.set(3,1,6);
        m1.set(2,3,-3);
        Vector v2 = v1.normalise();
        System.out.println(v2);
        System.out.println(v2.magnitude());
        System.out.println(m1);
        System.out.println(v1);
        System.out.println(m1.multiply(v1));
        Vector v3 = new Vector(3,2,7);
        System.out.println(v1.cross(v3));
    }
    
    
    public void set(Vector v) {
        this.u0 = v.get(0);
        this.u1 = v.get(1);
        this.u2 = v.get(2);
        this.u3 = v.get(3);
    } // get()

} // Vector
