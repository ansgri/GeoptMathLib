/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geopt.geometry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static java.lang.Math.*;

/**
 *
 * @author ASG
 */
public class UtilsTest {

    public UtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    
    
    /**
     * Test of refract method, of class Utils.
     */
    @Test
    public void refract() {
        System.out.println("refract");
        Vector direction = new Vector(1, -1, 0);
        Vector normal = new Vector(0, 1, 0);
        double n1 = 1.0;
        double n2 = 1.5;
        double phi2 = asin(sqrt(2) / 3);
        Vector expResult = new Vector(sin(phi2), -cos(phi2), 0);
        Vector result = Utils.refract(direction, normal, n1, n2);
        System.out.println("expected: ".concat(expResult.toString()));
        System.out.println("was: ".concat(result.toString()));
        
        assertTrue(expResult.isCodirectional(result));
        // TODO review the generated test code and remove the default call to fail.
    }

}