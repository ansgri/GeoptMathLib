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
public class PlaneTest {

    public PlaneTest() {
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
     * Test of intersectRay method, of class Plane.
     */
    @Test
    public void intersectRay() {
        System.out.println("Plane#intersectRay");
        
        Ray[] raysToIntersect = new Ray[] {
            new Ray(new Vector(0, 0, 0), new Vector(0, 0, 1)),
            new Ray(new Vector(0.3, 0.2, 0.1), new Vector(-1, -1, -1)),
            new Ray(new Vector(1, 1, 1), new Vector(-1, 0, -1))
        };
        
        Plane[] instances = new Plane[] {
            new Plane(new Vector(1, 0, 0), new Vector(1, 0, 1)),
            new Plane(new Vector(1, 0, 0), new Vector(1, 0, 1)),  
            new Plane(new Vector(1, 0, 0), new Vector(1, 0, 1))        
        };
                
        Vector[] expResults = new Vector[] {
            new Vector(0, 0, 1),
            null,
            new Vector(0.5, 1, 0.5)
        };
        
        for ( int i = 0; i < instances.length; i++ ) {
            Vector result = instances[i].intersectRay(raysToIntersect[i]);
            System.out.print("result: ");
            System.out.println(result);
            assertEquals(expResults[i], result);
        }
        
    }

}