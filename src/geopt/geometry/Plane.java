/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geopt.geometry;

/**
 * 
 * Плоскость. Задается точкой и направлением.
 *
 * @author AnSGri.
 */
public class Plane {
    
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
    
    // --- ---
    
    

}
