package geopt.geometry;

import java.io.Serializable;

/**
 * Цилиндр.
 * !!! НЕ РАБОТАЕТ.
 * 
 * @author AnSGri.
 */
public class Cylinder implements Cloneable, Serializable {
    
    // --- Закрытые поля ---
    
    private static final long serialVersionUID = 1L;
    
    private Ray axis;
    private double radius;
    
    // --- Конструктор ---
    
    /**
     * 
     * Задание цилиндра его осью и радиусом.
     * 
     * @param axis Ось цилиндра.
     * @param radius Радиус цилиндра.
     */
    public Cylinder(Ray axis, double radius) {
        this.axis = axis;
        this.radius = radius;
    }
    
    // --- Стандартные переопределения ---

    @Override
    public Object clone() {
        return new Cylinder(axis, radius);
    }
    
    @Override
    public String toString() {
        return "Cylinder(" + axis.toString() + "; " + Double.toString(radius) + ")";
    }
    
    @Override
    public boolean equals(Object term) {
        if ( term instanceof Cylinder ) {
            Cylinder sph = (Cylinder)term;
            return sph.getRadius() == radius && sph.getAxis().equals(axis);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.axis != null ? this.axis.hashCode() : 0);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.radius) ^ (Double.doubleToLongBits(this.radius) >>> 32));
        return hash;
    }
    
    // --- Методы доступа ---
    
    public Ray getAxis() {
        return axis;
    }

    public void setAxis(Ray axis) {
        this.axis = axis;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    
    // --- Собственно геометрия ---
    
    /**
     * 
     * Нахождение точки пересечения луча с цилиндром.
     * 
     * !!! Вот он, корень зла, из-за которого наши трубы не работают.
     * 
     * @param rayToIntersect Пересекаеый луч
     * @return Ближайшее пересечение; null, если такового нет.
     */
    public Vector intersectRay(Ray rayToIntersect) {
        
        // Пояснения к этому ужасному коду см. в Cylinder.intersectRay.jpg
        
        Vector a = axis.getDirection();
        Vector d = rayToIntersect.getDirection().normalize();
        Vector ptM = rayToIntersect.getOrigin();
        Vector ptA = axis.origin;
        Vector vMA = ptA.subtract(ptM);
        Vector vMA2 = Utils.projectOnPlane(vMA, a);
        Vector d2 = Utils.projectOnPlane(d, a);
        
        Sphere approxSphere = new Sphere(ptM.add(vMA2), radius);
        Vector ptX2 = approxSphere.intersectRay(new Ray(ptM, d2));
        
        if ( ptX2 == null ) {
            return null;
        }
        
        Vector vMX2 = ptX2.subtract(ptM);
        Vector vMX = d.multiply(vMX2.abs() / d2.abs()); // <--- скорее всего, ошибка.
        
        return ptM.add(vMX);
    }
    
    /**
     * 
     * Восстановление нормали из заданной точки.
     * 
     * @param intersection Точка, которая обязана лежать на цилиндре.
     * @return Нормаль к цилиндру из данной точки.
     */
    public Vector getNormalAt(Vector intersection) {
        return
                Utils.projectOnPlane(intersection.subtract(axis.getOrigin()), axis.getDirection())
                .normalize();
    }
    
    /**
     * 
     * Проверка того, находится ли данная точка внутри цилиндра.
     * 
     * @param pointToTest Проверяемая точка
     * @return true, если расстояние от точки до оси не больше радиуса.
     */
    public boolean isPointInside(Vector pointToTest) {
        return Utils.projectOnPlane(pointToTest.subtract(axis.getOrigin()), axis.getDirection())
                .abs() <= radius;
    }
    

}
