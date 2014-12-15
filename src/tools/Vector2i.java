package tools;

/**
 * This class represents a vector, or a position, in the map.
 * PTSP-Competition
 * Created by Diego Perez, University of Essex.
 * Date: 19/12/11
 */
public class Vector2i
{
    /**
     * X-coordinate of the vector.
     */
    public int x;

    /**
     * Y-coordinate of the vector.
     */
    public int y;

    /**
     * Default constructor.
     */
    public Vector2i() {
        this(0, 0);
    }

    /**
     * Builds a vector from its coordinates.
     * @param x x coordinate
     * @param y y coordinate
     */
    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Builds a vector from another vector.
     * @param v Vector to copy from.
     */
    public Vector2i(Vector2i v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Creates a copy of this vector
     * @return a copy of this vector
     */
    public Vector2i copy() {
        return new Vector2i(x,y);
    }

    /**
     * Sets this vector's coordinates to the coordinates of another vector.
     * @param v that other vector.
     */
    public void set(Vector2i v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Sets this vector's coordinates to the coordinates given.
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the vector's coordinates to (0,0)
     */
//    public void zero() {
//        x = 0;
//        y = 0;
//    }
//
//    /**
//     * Returns a representative String of this vector.
//     * @return a representative String of this vector.
//     */
    public String toString() {
        return x + " : " + y;
    }

    /**
     * Adds another vector to this.
     * @param v vector to add.
     * @return this, after the addition.
     */
    public Vector2i add(Vector2i v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    /**
     * Adds to this vector two coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @return returns this, after the addition.
     */
    public Vector2i add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }
//
//    /**
//     * Adds to this vector another vector, scaled it by a factor..
//     * @param v Vector to add, to be scaled by w
//     * @param w Scale of v.
//     * @return this vector, after the addition.
//     */
//    public Vector2i add(Vector2i v, double w) {
//        // weighted addition
//        this.x += w * v.x;
//        this.y += w * v.y;
//        return this;
//    }
//
//    /**
//     * Performs a wrap operation over this vector.
//     * @param w width
//     * @param h height
//     * @return This vector, after the wrap.
//     */
////    public Vector2i wrap(double w, double h) {
//////        w = 2 * w;
//////        h = 2 * h;
////        x = (x + w) % w;
////        y = (y + h) % h;
////        return this;
////    }
//
//    /**
//     * Subtracts another vector from this.
//     * @param v vector to subtract.
//     * @return this, after the subtraction.
//     */
//    public Vector2i subtract(Vector2i v) {
//        this.x -= v.x;
//        this.y -= v.y;
//        return this;
//    }
//
//    /**
//     * Subtracts two coordinates to this vector.
//     * @param x x coordinate
//     * @param y y coordinate
//     * @return returns this, after the subtraction.
//     */
//    public Vector2i subtract(double x, double y) {
//        this.x -= x;
//        this.y -= y;
//        return this;
//    }
//
//    /**
//     * Multiplies this vector by a factor.
//     * @param fac factor to multiply this vector by.
//     * @return This vector, after the operation.
//     */
//    public Vector2i mul(double fac) {
//        x *= fac;
//        y *= fac;
//        return this;
//    }
//
//    /**
//     * Rotates the vector an angle given, in radians.
//     * @param theta angle given, in radians
//     */
////    public void rotate(double theta) {
////        // rotate this vector by the angle made to the horizontal by this line
////        // theta is in radians
////        double cosTheta = Math.cos(theta);
////        double sinTheta = Math.sin(theta);
////
////        double nx = x * cosTheta - y * sinTheta;
////        double ny = x * sinTheta + y * cosTheta;
////
////        x = nx;
////        y = ny;
////    }
//
//    /**
//     * Calculates the scalar product of this vector and the one passed by parameter
//     * @param v vector to do the scalar product with.
//     * @return the value of the scalar product.
//     */
//    public double scalarProduct(Vector2i v) {
//        return x * v.x + y * v.y;
//    }
//
//    /**
//     * Gets the square value of the parameter passed.
//     * @param x parameter
//     * @return x * x
//     */
//    public static double sqr(double x) {
//        return x * x;
//    }
//
//    /**
//     * Returns the square distance from this vector to the one in the arguments.
//     * @param v the other vector, to calculate the distance to.
//     * @return the square distance, in pixels, between this vector and v.
//     */
//    public double sqDist(Vector2i v) {
//        return sqr(x - v.x) + sqr(y - v.y);
//    }
//
//    /**
//     * Gets the magnitude of the vector.
//     * @return the magnitude of the vector (Math.sqrt(sqr(x) + sqr(y)))
//     */
//    public double mag() {
//        return Math.sqrt(sqr(x) + sqr(y));
//    }
//
//    /**
//     * Returns the distance from this vector to the one in the arguments.
//     * @param v the other vector, to calculate the distance to.
//     * @return the distance, in pixels, between this vector and v.
//     */
//    public double dist(Vector2i v) {
//        return Math.sqrt(sqDist(v));
//    }
//
//    /**
//     * Returns the distance from this vector to a pair of coordinates.
//     * @param xx x coordinate
//     * @param yy y coordinate
//     * @return the distance, in pixels, between this vector and the pair of coordinates.
//     */
//    public double dist(double xx, double yy) {
//        return Math.sqrt(sqr(x - xx) + sqr(y - yy));
//    }
//
//
//    /**
//     * Returns the atan2 of this vector.
//     * @return the atan2 of this vector.
//     */
//    public double theta() {
//        return Math.atan2(y, x);
//    }
//
//    /**
//     * Normalises this vector.
//     */
//    public void normalise() {
//        double mag = mag();
//        if(mag == 0)
//        {
//            x = y = 0;
//        }else{
//            x /= mag;
//            y /= mag;
//        }
//    }
//
//    /**
//     * Calculates the dot product between this vector and the one passed by parameter.
//     * @param v the other vector.
//     * @return the dot product between these two vectors.
//     */
//    public double dot(Vector2i v) {
//        double tot = this.x * v.x + this.y * v.y;
//        return tot;
//    }
//
//    public Vector2i unitVector()
//    {
//        double l = this.mag();
//        if(l > 0)
//        {
//            return new Vector2i(this.x/l,this.y/l);
//        }
//        else return new Vector2i(1,0);
//    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2i other = (Vector2i) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
    
    
    

}

