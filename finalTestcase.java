import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Jamal Aden
 *
 */
public class finalTestcase {

    /*
     * Tests of reduceToGCD
     */

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(21);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isEven
     */

    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of powerMod
     */

    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test

    public void testPowerMod_3_1_2() {

        NaturalNumber n = new NaturalNumber2(3);

        NaturalNumber p = new NaturalNumber2(1);

        NaturalNumber m = new NaturalNumber2(2);

        CryptoUtilities.powerMod(n, p, m);

        assertEquals("1", n.toString());

        assertEquals("1", p.toString());

        assertEquals("2", m.toString());

    }

    @Test

    public void testPowerMod_3_2_2() {

        NaturalNumber n = new NaturalNumber2(3);

        NaturalNumber p = new NaturalNumber2(2);

        NaturalNumber m = new NaturalNumber2(2);

        CryptoUtilities.powerMod(n, p, m);

        assertEquals("1", n.toString());

        assertEquals("2", p.toString());

        assertEquals("2", m.toString());

    }

    @Test

    public void testPowerMod_3_3_2() {

        NaturalNumber n = new NaturalNumber2(3);

        NaturalNumber p = new NaturalNumber2(3);

        NaturalNumber m = new NaturalNumber2(2);

        CryptoUtilities.powerMod(n, p, m);

        assertEquals("1", n.toString());

        assertEquals("3", p.toString());

        assertEquals("2", m.toString());

    }

    @Test

    public void testPowerMod_3_6_5() {

        NaturalNumber n = new NaturalNumber2(3);

        NaturalNumber p = new NaturalNumber2(6);

        NaturalNumber m = new NaturalNumber2(5);

        CryptoUtilities.powerMod(n, p, m);

        assertEquals("4", n.toString());

        assertEquals("6", p.toString());

        assertEquals("5", m.toString());

    }

    @Test

    public void testPowerMod_5_3_3() {

        NaturalNumber n = new NaturalNumber2(5);

        NaturalNumber p = new NaturalNumber2(3);

        NaturalNumber m = new NaturalNumber2(3);

        CryptoUtilities.powerMod(n, p, m);

        assertEquals("2", n.toString());

        assertEquals("3", p.toString());

        assertEquals("3", m.toString());

    }

    @Test

    public void testPowerMod_4_4_19() {

        NaturalNumber n = new NaturalNumber2(4);

        NaturalNumber p = new NaturalNumber2(4);

        NaturalNumber m = new NaturalNumber2(19);

        CryptoUtilities.powerMod(n, p, m);

        assertEquals("9", n.toString());

        assertEquals("4", p.toString());

        assertEquals("19", m.toString());

    }

    @Test

    public void testPowerMod_2_3_2() {

        NaturalNumber n = new NaturalNumber2(2);

        NaturalNumber p = new NaturalNumber2(3);

        NaturalNumber m = new NaturalNumber2(2);

        CryptoUtilities.powerMod(n, p, m);

        assertEquals("0", n.toString());

        assertEquals("3", p.toString());

        assertEquals("2", m.toString());

    }

    @Test

    public void testPowerMod_5_3_7() {

        NaturalNumber n = new NaturalNumber2(5);

        NaturalNumber p = new NaturalNumber2(3);

        NaturalNumber m = new NaturalNumber2(7);

        CryptoUtilities.powerMod(n, p, m);

        assertEquals("6", n.toString());

        assertEquals("3", p.toString());

        assertEquals("7", m.toString());

    }

    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

}