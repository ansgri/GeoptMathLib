/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geopt.geometry;

import java.io.Serializable;
import static geopt.geometry.Utils.*;

/**
 *
 * Геометрическая сфера.
 * 
 * @author AnSGri.
 */
public class Sphere implements Cloneable, Serializable {

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
        return new Sphere(getCenter(),getRadius());
    }
    
    // --- Переопределение сравнения и прочих стандартных. ---
    
    @Override
    public boolean equals(Object term) {
        if ( term instanceof Sphere ) {
            Sphere sph = (Sphere)term;
            return sph.getRadius() == getRadius() && sph.getCenter().equals(getCenter());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.getRadius()) ^ (Double.doubleToLongBits(this.getRadius()) >>> 32));
        hash = 97 * hash + (this.getCenter() != null ? this.getCenter().hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString() {
        return String.format("Sphere(%s, %g)", center, radius);
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
                .smul(rayToIntersect.origin.subtract(getCenter()));
        double cC = rayToIntersect.origin.subtract(getCenter()).square()
                - getRadius()*getRadius();
        
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

    // --- Методы доступа ---
    
    public double getRadius() {
        return radius;
    }

    public Vector getCenter() {
        return center;
    }
    
}
