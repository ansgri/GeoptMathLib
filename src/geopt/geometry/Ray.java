

package geopt.geometry;

import static geopt.geometry.Utils.*;

/**
 *
 * Приложенный луч.
 * 
 * @author AnSGri.
 */
public class Ray implements Cloneable {
    
    // --- Конструкторы ---
    
    public Ray() {
        origin = new Vector(0.0, 0.0, 0.0);
        direction = new Vector(1.0, 0.0, 0.0);
    }
    
    public Ray(Vector aOrigin, Vector aDirection)
            throws IllegalArgumentException {
        
        if ( aDirection.isZero() ) {
            throw new IllegalArgumentException();
        }
        
        origin = (Vector)aOrigin.clone();
        direction = aDirection.normalize();
    }
    
    // --- Переопределение равенства и прочих стандартных. ---
    
    @Override
    public String toString() {
        return "Ray(Origin".concat(origin.toString())
                .concat("Direction").concat(direction.toString()).concat(")");
    }
    
    @Override
    public boolean equals(Object term) {
        if ( term instanceof Ray ) {
            Ray r = (Ray)term;
            return r.direction.equals(direction)
                    && r.origin.equals(origin);
        } else { return false; }
    }

    @Override // Это не я писал, оно само.
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.origin != null ? this.origin.hashCode() : 0);
        hash = 17 * hash + (this.direction != null ? this.direction.hashCode() : 0);
        return hash;
    }
    
    // --- Cloneable members ---
    
    @Override
    public Object clone() {
        return new Ray(origin, direction);
    }
    
    // --- Закрытые поля ---
    
    protected Vector origin;
    protected Vector direction;
    
    // --- ---

    // --- Методы доступа к полям ---
    
    public Vector getOrigin() {
        return (Vector)origin.clone();
    }
    
    public Vector getDirection() {
        return (Vector)direction.clone();
    }
    
    // --- Элементарные геометрические операции ---
    
    public Ray invert() {
        return new Ray(origin, direction.multiply(-1.0));
    }
    
    /* ////
     * 
     * Написать поворот вокруг оси на угол.
     */
    
    // --- Проверка геометрических отношений ---
    
    public boolean isCollinear(Ray term) {
        return direction.isCollinear(term.direction);
    }
    
    public boolean containsPoint(Vector point) {
        Vector diff = point.subtract(origin);
        return direction.isCodirectional(diff);        
    }
    
    // --- Сравнение Приоритетов Точек ---
    
    /**
     * Сравнение Приоритетов Точек - проекция которой ближе
     * к началу луча.
     */    
    
    public static final int CP_FIRST_GREATER = 1; // Первая ближе. Или равны.
    public static final int CP_SECOND_GREATER = -1; // Вторая ближе.
    public static final int CP_INDETERMINATE = 0; // Ни одна не подходит.
    
    public int comparePriorities(Vector firstPoint, Vector secondPoint) {
        Vector relativeFirst = firstPoint.subtract(origin);
        Vector relativeSecond = secondPoint.subtract(origin);
        
        double firstProj = direction.smul(relativeFirst);
        double secondProj = direction.smul(relativeSecond);
        
        if ( !equalsZero(firstProj) ) {
            if ( firstProj >= secondProj ) {
                return CP_FIRST_GREATER;
            } else { 
                return CP_SECOND_GREATER; 
            }
        } else if ( !equalsZero(secondProj) ) {
            return CP_SECOND_GREATER;
        } else { 
            return CP_INDETERMINATE; 
        }
    }
    
    // --- ---
    
}
