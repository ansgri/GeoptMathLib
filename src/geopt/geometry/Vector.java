
package geopt.geometry;

/**
 *
 * Вектор в трехмерном геометрическом пространстве.
 * 
 * @author AnSGri.
 */
public class Vector {
    
    private double x;
    private double y;
    private double z;
    
    public Vector(double xC, double yC, double zC) {
        x = xC;
        y = yC;
        z = zC;
    }
    
    // --- элементарные операции над векторами ---
    
    public double abs() {
        return Math.sqrt(x*x + y*y + z*z);
    }
    
    public Vector add(Vector term) {
        return new Vector(
                x + term.x,
                y + term.y,
                z + term.z);
    }
    
    public Vector subtract(Vector term) {
        return new Vector(
                x - term.x,
                y - term.y,
                z - term.z);
    }

    public double smul(Vector term) {
        return x*term.x + y*term.y + z*term.z;
    }

    public static double scalarProduct(Vector term1, Vector term2) {
        return term1.smul(term2);
    }
    
    public Vector vmul(Vector term) {
        return new Vector(
                y*term.z - z*term.y,
                z*term.x - x*term.z,
                x*term.y - y*term.x);
    }
    
    public static Vector vectorProduct(Vector term1, Vector term2) {
        return term1.vmul(term2);
    }
    
    public static double mixedProduct(Vector term1, Vector term2, Vector term3) {
        return term1.smul(term2.vmul(term3));
    }
    
    public Vector multiply(double term) {
        return new Vector(
                x * term,
                y * term,
                z * term);
    }
    
    public Vector divide(double term) 
            throws IllegalArgumentException {
        
        if ( term == 0 )
            throw new IllegalArgumentException();
        
        return this.multiply(1 / term);        
    }
    
    public Vector normalize() {
        return this.divide(this.abs());
    }
    
    public void normalizeIt() {
        double length = abs();
        x /= length;
        y /= length;
        z /= length;
    }
    
    // --- Проверки на коллинеарность, ортогональность итп ---
    
    public boolean isZero() {
        return x==0 && y==0 && z==0;
    }
    
    public boolean isCollinear(Vector term) {
        return this.vmul(term).isZero();
    }
    
    public boolean isOrthogonal(Vector term) {
        return this.smul(term) == 0;
    }
    
    public boolean isComplanar(Vector term1, Vector term2) {
        return Vector.isComplanar(this, term1, term2);
    }
    
    public static boolean isComplanar(Vector term1, Vector term2, Vector term3) {
        return Vector.mixedProduct(term1, term2, term3) == 0;
    }
    
}
