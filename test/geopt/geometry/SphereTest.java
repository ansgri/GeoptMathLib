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

/**
 *
 * @author ASG
 */
public class SphereTest {

    public SphereTest() {
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
     * Test of intersectRay method, of class Sphere.
     */
    @Test
    public void intersectRay() {
        System.out.println("Sphere#intersectRay");
        
        Ray[] raysToIntersect = new Ray[] {
            new Ray(new Vector(-2, 0, -2), new Vector(1, 0, 1)),
            new Ray(new Vector(-0.1, 0, -0.1), new Vector(1, 0, 1)),
            new Ray(new Vector(-2, 0, -2), new Vector(1, 1, 2)),
            new Ray(new Vector(1, 0, 1), new Vector(1, 1, 2))        
        };
        
        Sphere[] instances = new Sphere[] {
            new Sphere(new Vector(0, 0, 0), 1),
            new Sphere(new Vector(0, 0, 0), 1),
            new Sphere(new Vector(0, 0, 0), 1),
            new Sphere(new Vector(0, 0, 0), 1)
        };
        
        Vector[] expResults = new Vector[] {
            new Vector(-Math.sqrt(0.5), 0, -Math.sqrt(0.5)),
            new Vector(Math.sqrt(0.5), 0, Math.sqrt(0.5)),
            null,
            null
        };
        
        for ( int i = 0; i < instances.length; i++ ) {
            Vector result = instances[i].intersectRay(raysToIntersect[i]);
            System.out.print("result: ");
            System.out.println(result);
            //System.out.println(result.subtract(expResults[i]));
            assertEquals(expResults[i], result);
        }
    }

}