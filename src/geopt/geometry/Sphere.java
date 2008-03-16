/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geopt.geometry;

import static geopt.geometry.Utils.*;

/**
 *
 * Геометрическая сфера.
 * 
 * @author AnSGri.
 */
public class Sphere implements Cloneable{

    // --- Конструктор ---
    
    /**
     * 
     * Создание сферы по центру и радиусу.
     * 
     * @param center Центр сферы
     * @param radius Радиус сферы
     * @throws java.lang.IllegalArgumentException Если радиус неположительный.
     */
    public Sphere(Vector center, double radius)
            throws IllegalArgumentException {
        
        if ( equalsZero(radius) || radius < 0 ) {
            throw new IllegalArgumentException();
        }
        
        this.center = (Vector)center.clone();
        this.radius = radius;
    }
    
    // --- Cloneable members ---
    
    @Override
    public Object clone() {
        return new Sphere(center, radius);
    }
    
    // --- Переопределение сравнения и прочих стандартных. ---
    
    @Override
    public boolean equals(Object term) {
        if ( term instanceof Sphere ) {
            Sphere sph = (Sphere)term;
            return sph.radius == radius && sph.center.equals(center);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.radius) ^ (Double.doubleToLongBits(this.radius) >>> 32));
        hash = 97 * hash + (this.center != null ? this.center.hashCode() : 0);
        return hash;
    }
    
    // --- Закрытые поля ---
    
    private double radius;
    private Vector center;
    
    // --- Пересечения ---
    
    /**
     * 
     * Нахождение ближайшего пересечения луча со сферой.
     * 
     * @param rayToIntersect Пересекаемый луч.
     * @return Ближайшая точка пересечения со сферой, или null, если таковых нет.
     */
    public Vector intersectRay(Ray rayToIntersect) {
        double cA = rayToIntersect.direction.square();
        double cB = 2 * rayToIntersect.direction
                .smul(rayToIntersect.origin.subtract(center));
        double cC = rayToIntersect.origin.subtract(center).square()
                - radius*radius;
        
        double discriminant = cB*cB - 4*cA*cC;
        
        if ( discriminant < 0 ) {
            return null;
        }
        
        double tX1 = (-cB - Math.sqrt(discriminant)) / (2 * cA);
        double tX2 = (-cB + Math.sqrt(discriminant)) / (2 * cA);
        
        double t;
        
        if ( tX1 > 0 ) t = tX1;
        else if ( tX2 > 0 ) t = tX2;
        else return null;
        
        return rayToIntersect.origin 
                    .add(rayToIntersect.direction.multiply(t));
    }
    
}
