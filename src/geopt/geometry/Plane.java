package geopt.geometry;

import java.io.Serializable;

/**
 * 
 * Плоскость. Задается точкой и направлением.
 *
 * @author AnSGri.
 */
public class Plane implements Cloneable, Serializable{
    
    private static final long serialVersionUID = 1L;
        
    // --- Конструктор ---
    
    public Plane(Vector aSomePoint, Vector aNormalVector) 
            throws IllegalArgumentException {
        
        if ( aNormalVector.isZero() ) {
            throw new IllegalArgumentException();
        }
        
        somePoint = (Vector)aSomePoint.clone();
        normalVector = aNormalVector.normalize();
    }
    
    // --- Cloneable members ---
    
    @Override
    public Object clone() {
        return new Plane(somePoint, normalVector);
    }
    
    // --- Переопределение сравнения и прочих стандартных. ---
    
    @Override
    public String toString() {
        return "Plane(Point".concat(somePoint.toString())
                .concat("Normal").concat(normalVector.toString()).concat(")");
    }
    
    @Override
    public boolean equals(Object term) {
        if ( term instanceof Plane ) {
            Plane p = (Plane)term;
            
            if ( p.normalVector.isCollinear(normalVector) ) {
                Vector diff = somePoint.subtract(p.somePoint);
                return diff.isOrthogonal(normalVector);
                
            } else { return false; }
            
        } else { return false; }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.somePoint != null ? this.somePoint.hashCode() : 0);
        hash = 41 * hash + (this.normalVector != null ? this.normalVector.hashCode() : 0);
        return hash;
    }
    
    // --- Закрытые поля ---
    
    private Vector somePoint;
    private Vector normalVector;
    
    // --- Методы доступа к закрытым полям ---
    
    public Vector getSomePoint() {
        return somePoint;
    }
    
    public Vector getNormalVector() {
        return normalVector;
    }
    
    // --- Пересечения ---
    
    /**
     * 
     * Нахождение пересечения плоскости с лучом.
     * 
     * @param rayToIntersect Пересекающий луч.
     * @return Точка пересечения или null, если таковых нет.
     */
    public Vector intersectRay(Ray rayToIntersect) {
        try {
            double denom = rayToIntersect.direction.smul(normalVector);
            if ( Utils.equalsZero(denom) ) {
                return null;
            }
            
            double tX = somePoint.subtract(rayToIntersect.origin)
                    .smul(normalVector) / denom;
            if ( tX < 0 ) {
                return null;
            }
            
            return rayToIntersect.origin 
                    .add(rayToIntersect.direction.multiply(tX));
            
        } catch (Exception ex) {
            return null;
        }
    }
    
    

}
