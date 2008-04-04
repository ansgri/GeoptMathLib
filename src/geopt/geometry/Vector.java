
package geopt.geometry;

import static geopt.geometry.Utils.*;

/**
 *
 * Вектор в трехмерном геометрическом пространстве.
 * 
 * @author AnSGri.
 */
public class Vector implements Cloneable {
    
    // --- Cloneable members ---
    
    @Override
    public Object clone() {
        return new Vector(x, y, z);
    }
    
    // --- Переопределение стандартных методов ---
    
    @Override
    public String toString() {
        return String.format("(%g, %g, %g)", x, y, z);
    }
    
    /**
     * 
     * Преобразование строки в вектор. Формат (x; y; z) или x;y;z
     * 
     * @param str Разбираемая строка
     * @return Vector, представляемый данной строкой
     * @throws java.lang.NumberFormatException в случае, если строка имеет неверный формат.
     */
    public static Vector parse(String str) throws NumberFormatException {
        String[] parts = str.split("\\(\\s*|\\s*\\)|\\s*;\\s*");
        
        /*for ( int i = 0; i < parts.length; i++ ) {
            System.out.printf("%1$d> %2$s\n", i, parts[i]);
        }*/ 
        
        if ( parts.length == 3 ) {
            return new Vector(
                    Double.parseDouble(parts[0]),
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2]));
        } else if ( parts.length > 3 ) {
            return new Vector(
                    Double.parseDouble(parts[1]),
                    Double.parseDouble(parts[2]),
                    Double.parseDouble(parts[3]));
        } else throw new NumberFormatException();
    }
    
    @Override
    public boolean equals(Object term) {
        if ( term instanceof Vector ) {
            Vector t = (Vector) term;
            
            return doubleEquals(t.x, x) 
                    && doubleEquals(t.y, y) 
                    && doubleEquals(t.z, z);

           // return equalsZero(subtract(t).square());
        }
        return false;
    }

    @Override // Это не я писал, оно само.
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
    
    // --- Закрытые поля ---
    
    protected double x;
    protected double y;
    protected double z;
    
    // --- Методы доступа к полям ---
    // По некоторым причинам методов установки нет, только чтение.
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }    
    
    public double getZ() {
        return z;
    }
    
    // --- Конструкторы ---
    
    public Vector(double xC, double yC, double zC) {
        x = xC;
        y = yC;
        z = zC;
    }
    
    public Vector() {
        x = y = z = 0;
    }
    
    // --- элементарные операции над векторами ---
    
    public double abs() {
        return Math.sqrt(x*x + y*y + z*z);
    }
    
    public double square() {
        return x*x + y*y + z*z;
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
        return term1.x * (term2.y*term3.z - term2.z*term3.y)
                - term1.y * (term2.x*term3.z - term2.z*term3.x)
                + term1.z * (term2.x*term3.y - term2.y*term3.x);
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
    
    
    // --- Методы, изменяющие текущий экземпляр ---
    
    public void normalizeIt() {
        double length = abs();        
        if ( length != 0 ) {        
            x /= length;
            y /= length;
            z /= length;
        }
    }
    
    // --- Проверки на коллинеарность, ортогональность итп ---
    
    public boolean isZero() {
        return equalsZero(x) 
                && equalsZero(y)
                && equalsZero(z);
    }
    
    public boolean isCollinear(Vector term) {
        return this.vmul(term).isZero();
    }
    
    public boolean isCodirectional(Vector term) {
        if( isCollinear(term) ) {
            if ( !equalsZero(x) ) {
                return (x / term.x) > 0;
            }
            if ( !equalsZero(y) ) {
                return (y / term.y) > 0;
            }            
            if ( !equalsZero(z) ) {
                return (z / term.z) > 0;
            }
            return true;
        } else { return false; }
    }
    
    public boolean isOrthogonal(Vector term) {
        return equalsZero(this.smul(term));
    }
    
    public boolean isComplanar(Vector term1, Vector term2) {
        return Vector.isComplanar(this, term1, term2);
    }
    
    public static boolean isComplanar(Vector term1, Vector term2, Vector term3) {
        return equalsZero(Vector.mixedProduct(term1, term2, term3));
    }
    
    // --- Операции посложнее ---
    
    /**
     * 
     * @param axis
     * @return
     */
    public Vector reflectAround(Vector normal) {
        if ( normal.isZero() ) {
            throw new IllegalArgumentException();
        }
        Vector nd = normal.normalize();
        
        // -This + 2*(This, Nd)*nd         
        return this.multiply(-1).add(nd.multiply(this.smul(nd)*2)).multiply(-1);
    }
    
    /*
    public Vector rotateAround(Vector axis, double angle) {
        Vector vprod = axis.vmul(this);
        double sprod = this.smul(axis);
        double cosHalf = Math.cos(angle/2);
        double sinHalf = Math.sin(angle/2);
        double cosAngle = Math.cos(angle);
        
        axis.normalizeIt();
        
        Vector result = axis
                .multiply(sprod * sinHalf * sinHalf)
                .add(this.multiply(cosAngle * cosAngle))
                .subtract(vprod.multiply(cosHalf * sinHalf))
                .add(axis.vmul(vprod).multiply(sinHalf * sinHalf));
        
        return result;
    }
     */
    
}
