
package geopt.geometry;

/**
 *
 * Вспомогательный класс для выполнения математических операций.
 * 
 * @author AnSGri
 */
public class Utils {
    
    // --- Сравнение чисел в пределах погрешности ---
    
    // Максимально допустимое относительное отклонение для
    // чисел, для того чтобы они считались равными.
    private static double relativeTolerance = 1.0e-15;
    
    // Установка максимально допустимого относительного отклонения двух
    // равных чисел.
    public static void setRelativeTolerance(double tolerance) {
        relativeTolerance = tolerance;
    }
    
    // Проверка на равенство в пределах погрешности.
    public static boolean doubleEquals(double term1, double term2) {
        try {
            if ( term1==0 || term2==0 ) {
                return equalsZero(term1) && equalsZero(term2);
            }
            return Math.abs(term1/term2 - 1.0) < relativeTolerance;
        } catch (Exception ex) {
            return false;
        }
    }
    
    // Проверка на равенство нулю в пределах погрешности.
    public static boolean equalsZero(double term) {
        return Math.abs(term) < relativeTolerance;
    }
    
    public static boolean equalsOne(double term) {
        return Math.abs(term - 1) < relativeTolerance;
    }
    
    
    // ---  ---

    /**
     * 
     * Преломление вектора 
     * на поверхности с относительным показателем преломления n
     * и внешней нормалью normal.
     * Учитывается эффект полного внутреннего отражения.
     * Изменение интенсивности не учитывается.
     * n = n1/n2, где n1 - показатель преломления с внутренней стороны поверхности,
     * n2 соответственно с внешней.
     * 
     * n != 0
     * normal.abs() == 1
     * 
     * @param direction
     * @param normal
     * @param n
     * @return
     */
    public static Vector refract(Vector direction, Vector normal, double n) {
        assert equalsOne(normal.abs());
        assert n != 0;
        
        double normalProjection = normal.smul(direction);
        if ( normalProjection < 0 ) {
            n = 1 / n;            
        }
        
        Vector b = normal.multiply(normalProjection);
        Vector c = direction.subtract(b);
        double phi1 = Math.atan2(c.abs(), b.abs());
        double newSine = n * Math.sin(phi1);
        if ( newSine > 1 ) {
            return direction.reflectAround(normal);
        }
        
        double phi2 = Math.asin(newSine);
        
        Vector result = b.normalize().add(c.normalize().multiply(Math.tan(phi2)));
        
        return result;        
    }
    
}
