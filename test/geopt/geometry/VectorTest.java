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
public class VectorTest {

    public VectorTest() {
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
     * Test of abs method, of class Vector.
     */
    @Test
    public void abs() {
        System.out.println("abs");
        Vector[] instances = new Vector[] { 
            new Vector(4.0, 3.0, 0.0),
            new Vector(0.0, 6.0, 8.0)
        };
        double[] expResults = new double[] {
            5.0,
            10.0
        };
        for ( int i = 0; i < instances.length; i++ ) {
            assertEquals(expResults[i], instances[i].abs());
        }
    }

    /**
     * Test of smul method, of class Vector.
     */
    @Test
    public void smul() {
        System.out.println("smul");
        Vector term = new Vector(30, 7.0, -1.2);
        Vector instance = new Vector(0.1, 1.0, 5.0);
        double expResult = 4.0;
        double result = instance.smul(term);
        assertEquals(expResult, result);
    }

    /**
     * Test of vmul method, of class Vector.
     */
    @Test
    public void vmul() {
        System.out.println("vmul");
        Vector term = new Vector(0.0, 1.0, 0.0);
        Vector instance = new Vector(1.0, 0.0, 0.0);
        Vector expResult = new Vector(0.0, 0.0, 1.0);
        Vector result = instance.vmul(term);
        System.out.println(instance);
        System.out.println(term);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    @Test
    public void reflectAround() {
        System.out.println("reflectAround");
        Vector term1 = new Vector(0, 1.0, 1.0);
        Vector axis = new Vector(0, 0.0, -1.0);
        Vector expected = new Vector(0, 1.0, -1.0);
        Vector result = term1.reflectAround(axis);
        assertEquals(expected, result);
    }
    
    /**
     * Test of mixedProduct method, of class Vector.
     */
    @Test
    public void mixedProduct() {
        System.out.println("mixedProduct");
        Vector term1 = new Vector(1.0, 1.0, 0);
        Vector term2 = new Vector(-1.0, 1.0, 0.0);
        Vector term3 = new Vector(0.0, 0.0, 1.0);
        double expResult = 2.0;
        double result = Vector.mixedProduct(term1, term2, term3);
        assertEquals(expResult, result);
    }

    /**
     * Test of isCollinear method, of class Vector.
     */
    @Test
    public void isCollinear() {
        System.out.println("isCollinear");
        
        {
            Vector term = new Vector(Math.PI, 0, Math.PI/0.5);
            Vector instance = new Vector(1.0, 0, 2.0);
            boolean expResult = true;
            boolean result = instance.isCollinear(term);
            assertEquals(expResult, result);
        }
        
        {
            Vector term = new Vector(Math.PI, 0, 2);
            Vector instance = new Vector(1.0, 0.001, 2.0);
            boolean expResult = false;
            boolean result = instance.isCollinear(term);
            assertEquals(expResult, result);
        }        
    }

    /**
     * Test of isOrthogonal method, of class Vector.
     */
    @Test
    public void isOrthogonal() {
        System.out.println("isOrthogonal");
        
        Vector[] instances = new Vector[] { 
            new Vector(4.0, 3.0, 0.0),
            new Vector(0.0, 6.0, 8.0)
        };
        Vector[] terms = new Vector[] {
            new Vector(4.0, 2.1, 0.6),
            new Vector(0.0, -8.0, 6.0)
        };
        boolean[] expResults = new boolean[] {
            false,
            true
        };
        for ( int i = 0; i < instances.length; i++ ) {
            assertEquals(expResults[i], instances[i].isOrthogonal(terms[i]));
        }
    }

    /**
     * Test of isComplanar method, of class Vector.
     */
    @Test
    public void isComplanar() {
        System.out.println("isComplanar");
        
        Vector[] instances = new Vector[] { 
            new Vector(4.0, 3.0, 0.0),
            new Vector(0.0, 6.0, 8.0)
        };
        Vector[] terms1 = new Vector[] {
            new Vector(4.0, 4.0, -2),
            new Vector(1.0, -3.0, 6.0)
        };
        Vector[] terms2 = new Vector[] {
            terms1[0].add(instances[0].multiply(30.1)),
            new Vector(0.0, -8.0, 6.0)
        };        
        boolean[] expResults = new boolean[] {
            true,
            false
        };
        for ( int i = 0; i < instances.length; i++ ) {
            assertEquals(expResults[i], instances[i]
                    .isComplanar(terms1[i], terms2[i]));
        }
    }

}