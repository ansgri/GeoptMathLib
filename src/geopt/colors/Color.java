/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geopt.colors;

import geopt.geometry.Vector;

/**
 *
 * Цвет в виде вектора.
 * 
 * @author AnSGri
 */
public class Color {
    // --- Закрытые поля ---
    
    private double red;
    private double green;
    private double blue;
    
    // --- Конструкторы ---
    
    public Color() {
        red = green = blue = 0;
    }
    
    public Color(double red, double green, double blue) {
        this.red = red;
        this.blue = blue;
        this.green = green;
        
    }
    
    public Color(java.awt.Color color) {
        this.red = color.getRed() / 255.0;
        this.green = color.getGreen() / 255.0;
        this.blue = color.getBlue() / 255.0;
    }
    
    public int toARGBColor() {
        int r = (int)(255 * getR());
        int g = (int)(255 * getG());
        int b = (int)(255 * getB());
        int a = 255;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
    
    @Override
    public Object clone() {
        return new Color(red, green, blue);
    }
    
    // --- Вспомогательные методы ---
    
    public void legalizeIt() {
        if ( red > 1 ) red = 1;
        if ( green > 1 ) green = 1;
        if ( blue > 1 ) blue = 1;
        
        if ( red < 0 ) red = 0;
        if ( green < 0 ) green = 0;
        if ( blue < 0 ) blue = 0;
    }
    
    public Color legalize() {
        return new Color(
                ensureInZeroToOne(red),
                ensureInZeroToOne(green),
                ensureInZeroToOne(blue)
            );
    }
    
    // --- Методы доступа ---
    
    public double getR() {
        return red;
    }
    
    public double getG() {
        return green;
    }
    
    public double getB() {
        return blue;
    }
    
    // --- Операции ---
    
    public Color add(Color term) {
        Color result = new Color(red + term.red, green + term.green, blue + term.blue);
        result.legalizeIt();
        return result;
    }
    
    public void combineItWith(Color term, double weight) {
        assert weight < 1 && weight >= 0;
        
        red += term.red * weight;
        green += term.green * weight;
        blue += term.blue * weight;
    }
    
    public Color multiply(double coeff){
        Color result = new Color(red * coeff, green * coeff, blue * coeff);
        result.legalizeIt();
        return result;
    }
    
    /**
     * 
     * Покомпонентное умножение.
     * 
     * @param term Второй сомножитель
     * @return Произведение цветов
     */
    public Color multiply(Color term) {
        Color result = new Color(red * term.red, green * term.green, blue * term.blue);
        result.legalizeIt();
        return result;
    }
    
    public Color sqrt() {
        return new Color(
                Math.sqrt(red),
                Math.sqrt(green),
                Math.sqrt(blue));
    }
    
    
    
    // --- Закрытые методы ---
    
    private double ensureInZeroToOne(double arg) {
        return arg > 1 ?
            1 : 
            arg < 0 ?
                0 :
                arg;
    }
    
    public static Color fromVector(Vector v) {
        return new Color(v.getX(), v.getY(), v.getZ());
    }

}
