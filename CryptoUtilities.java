import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 *
 * @author Jamal Aden
 *
 */
public final class CryptoUtilities {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities() {
    }

    /**
     * Useful constant, not a magic number: 3.
     */
    private static final int THREE = 3;

    /**
     * Pseudo-random number generator.
     */
    private static final Random GENERATOR = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     *
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires n > 0
     * @ensures <pre>
     * randomNumber = [a random number uniformly distributed in [0, n]]
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        final int base = 10;
        NaturalNumber result;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d]
             */
            int x = (int) ((d + 1) * GENERATOR.nextDouble());
            result = new NaturalNumber2(x);
            n.multiplyBy10(d);
        } else {
            /*
             * Incoming n has more than one digit, so generate a random number
             * (NaturalNumber) uniformly distributed in [0, n], and another
             * (int) uniformly distributed in [0, 9] (i.e., a random digit)
             */
            result = randomNumber(n);
            int lastDigit = (int) (base * GENERATOR.nextDouble());
            result.multiplyBy10(lastDigit);
            n.multiplyBy10(d);
            if (result.compareTo(n) > 0) {
                /*
                 * In this case, we need to try again because generated number
                 * is greater than n; the recursive call's argument is not
                 * "smaller" than the incoming value of n, but this recursive
                 * call has no more than a 90% chance of being made (and for
                 * large n, far less than that), so the probability of
                 * termination is 1
                 */
                result = randomNumber(n);
            }
        }
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {

        /*
         * Use Euclid's algorithm; in pseudocode: if m = 0 then GCD(n, m) = n
         * else GCD(n, m) = GCD(m, n mod m)
         */
        if (!m.isZero()) {
            // n2  used to not change n when finding the remainder.
            NaturalNumber n2 = n.newInstance();
            n2.copyFrom(n);
            NaturalNumber modulo = n2.divide(m);

            // Creates a recursive call to reduceToGCD with m and the remainder.
            if (!modulo.isZero()) {
                reduceToGCD(m, modulo);
            }
            n.transferFrom(m);
        }

    }

    /**
     * Reports whether n is even.
     *
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures isEven = (n mod 2 = 0)
     */
    public static boolean isEven(NaturalNumber n) {
        // Initially assume the number is even
        boolean isEven = true;
        //Divide last digit by 10
        int last = n.divideBy10();
        if (last % 2 != 0) {
            isEven = false;
            // if last digit is odd update isEven to false
        }
        n.multiplyBy10(last);
        return isEven;
        // return isEven after checking it
    }

    /**
     * Updates n to its p-th power modulo m.
     *
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires m > 1
     * @ensures n = #n ^ (p) mod m
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p,
            NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";
        if (p.isZero()) {
            // base case condition
            n.setFromInt(1);
        } else {
            // Creates copy of n to use in method later
            NaturalNumber originalN = n.newInstance();
            originalN.copyFrom(n);

            // checks if p is even by checking if it is divisible by 2.
            if (isEven(p)) {
                // For an even p, divide it by 2 for the recursive call.
                NaturalNumber pDiv2 = p.newInstance();
                pDiv2.copyFrom(p);
                pDiv2.divide(new NaturalNumber2(2));
                powerMod(n, pDiv2, m); // Recursive call to handle n^(p/2)

                NaturalNumber temp = n.newInstance();
                temp.copyFrom(n);
                n.multiply(temp);
            } else {
                // For odd cases subtract it by 1 to make it even.
                NaturalNumber pMinusOne = p.newInstance();
                pMinusOne.copyFrom(p);
                pMinusOne.decrement();

                powerMod(n, pMinusOne, m); // Recursive call for p-1

                n.multiply(originalN);
            }

            // makes sure n is within the bounds of m.
            NaturalNumber mResult = n.divide(m);
            n.copyFrom(mResult);
        }
    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     *
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires n > 2 and 1 < w < n - 1
     * @ensures <pre>
     * isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w,
            NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();

        // sets isWitnessTo to false by default
        boolean isWitnessTo = false;
        NaturalNumber one = n.newInstance();
        one.setFromInt(1);
        NaturalNumber two = n.newInstance();
        two.setFromInt(2);
        NaturalNumber nSubtract = n.newInstance();
        nSubtract.copyFrom(n);
        nSubtract.decrement();

        // Create copies of w for mod operations
        NaturalNumber wTwoMod = w.newInstance();
        wTwoMod.copyFrom(w);
        NaturalNumber wMinus = w.newInstance();
        wMinus.copyFrom(w);

        // Performing powerMod operations on copies of w and n-1
        powerMod(wTwoMod, two, n); // w^2 mod n
        powerMod(wMinus, nSubtract, n); // w^(n-1) mod n

        // Checks if w is  a witness to n being composite or not
        if (wTwoMod.compareTo(one) == 0 || wMinus.compareTo(one) != 0) {
            isWitnessTo = true;
            // in this case Evaluates to true rather than the default false
        }

        return isWitnessTo;
        // Returns isWitnessTo
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        boolean isPrime;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            /*
             * odd n >= 5: simply check whether 2 is a witness that n is
             * composite (which works surprisingly well :-)
             */
            isPrime = !isWitnessToCompositeness(new NaturalNumber2(2), n);
        }
        return isPrime;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use the ability to generate random numbers (provided by the
         * randomNumber method above) to generate several witness candidates --
         * say, 10 to 50 candidates -- guessing that n is prime only if none of
         * these candidates is a witness to n being composite (based on fact #3
         * as described in the project description); use the code for isPrime1
         * as a guide for how to do this, and pay attention to the requires
         * clause of isWitnessToCompositeness
         */
        boolean primeCandidate = true;
        final int minimumPrime = 2;
        //  checks if n is less than or equal to 2
        if (n.compareTo(new NaturalNumber2(minimumPrime)) <= 0) {
            primeCandidate = n.compareTo(new NaturalNumber2(minimumPrime)) == 0;
        } else if (isEven(n)) {
            // if n is even and > than 2 prime would evaluate to being false
            primeCandidate = false;
        } else {
            //N subtract is n-1 helps create randomnity
            NaturalNumber nSubtract = n.newInstance();
            nSubtract.copyFrom(n);
            nSubtract.decrement();
            NaturalNumber one = n.newInstance(); // signifies 1
            one.setFromInt(1);

            final int witnessCount = 50; // quantity of witnessCount to test
            for (int i = 0; i < witnessCount && primeCandidate; i++) {
                NaturalNumber candidate = randomNumber(nSubtract);

                // Make sure the candidate is in the expected range
                if (candidate.compareTo(one) > 0
                        && candidate.compareTo(nSubtract) < 0) {
                    if (isWitnessToCompositeness(candidate, n)) {
                        primeCandidate = false;
                    }
                }
            }
        }
        // Returns result of primal test
        return primeCandidate;
    }

    /**
     * Generates a likely prime number at least as large as some given number.
     *
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires n > 1
     * @ensures n >= #n and [n is very likely a prime number]
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use isPrime2 to check numbers, starting at n and increasing through
         * the odd numbers only (why?), until n is likely prime
         */

        NaturalNumber two = new NaturalNumber2(2);

        if (isEven(n)) {
            n.increment();
        } else {

            n.add(two);
        }

        while (!isPrime2(n)) {
            n.add(two); // This replaces the two increment() calls
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */
        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println("  expected value = "
                + (double) testSamples / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */
        while (true) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                break;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
                }
            }
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}