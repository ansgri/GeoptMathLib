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
        Vector[] directions = new Vector[] {
            new Vector(1, -1, 0),
            new Vector(1, -1, 0),
            new Vector(1, -1, 0)
        };
        Vector[] normals = new Vector[] {
            new Vector(0, 1, 0),
            new Vector(0, -1, 0),
            new Vector(0, -1, 0)
        };
        double[] ns = new double[] {
            1.5,
            1.0/1.5,
            3.0
        };
        double[] phi2s = new double[] {
            asin(sqrt(2) / 3),
            asin(sqrt(2) / 3),
            0.0
        };
        Vector[] expResults = new Vector[] {
            new Vector(sin(phi2s[0]), -cos(phi2s[0]), 0),
            new Vector(sin(phi2s[1]), -cos(phi2s[1]), 0),
            new Vector(1, 1, 0)
        };
        
        for ( int i = 0; i < directions.length; i++ ) {

            Vector result = Utils.refract(directions[i], normals[i], ns[i]);
            System.out.println("expected: ".concat(expResults[i].toString()));
            System.out.println("was: ".concat(result.toString()));

            assertTrue(expResults[i].isCodirectional(result));
        }
    }
    
    @Test
    public void projectOnVector() {
        System.out.println("projectOnVector");
        Vector[] vectors = new Vector[] {
            new Vector(1, 0, 0),
            new Vector(0, 1, 0),
            new Vector(1, 4, 1)
        };
        Vector[] expected = new Vector[] {
            new Vector(0, 0, 0),
            new Vector(0, 1, 0),
            new Vector(0, 4, 0)
        };
        
        Vector axis = new Vector(0, 130.6, 0);
        
        for ( int i = 0; i < vectors.length; i++ ) {
            assertEquals(expected[i], Utils.projectOnVector(vectors[i], axis));
        }
    }

}