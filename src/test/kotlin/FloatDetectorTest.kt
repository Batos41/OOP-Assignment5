import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FloatDetectorTest {

    private val detector = FloatDetector()

    // --- 1. VALID PATTERNS (Covering Accept States) ---

    @Test
    fun `valid floating point values`() {
        assertTrue(detector.isValid("1.0"))
        assertTrue(detector.isValid("123.34"))
        assertTrue(detector.isValid("0.20000"))
        assertTrue(detector.isValid("12349871234.12340981234098"))
        assertTrue(detector.isValid(".123"))
        assertTrue(detector.isValid("0.0"))
    }

    // --- 2. START STATE BRANCH COVERAGE ---

    @Test
    fun `invalid start characters`() {
        assertFalse(detector.isValid(""))        // Empty string short-circuit
        assertFalse(detector.isValid("a.123"))    // 'else' branch on Start
        assertFalse(detector.isValid(" -1.0"))    // 'else' branch on Start (leading space/minus)
    }

    // --- 3. LEADING ZERO STATE BRANCH COVERAGE ---

    @Test
    fun `invalid follow ups for leading zero`() {
        assertFalse(detector.isValid("00.123"))  // 'else' branch: zero after zero
        assertFalse(detector.isValid("012.4"))   // 'else' branch: digit after leading zero
        assertFalse(detector.isValid("0a.4"))    // 'else' branch: letter after leading zero
    }

    // --- 4. INTEGER PART STATE BRANCH COVERAGE ---

    @Test
    fun `invalid characters inside integer part`() {
        assertFalse(detector.isValid("123a.4"))  // 'else' branch in IntegerPart
        assertFalse(detector.isValid("123"))     // No period (remains in IntegerPart, returns isAccepting = false)
    }

    // --- 5. PERIOD STATE BRANCH COVERAGE ---

    @Test
    fun `invalid follow ups after period`() {
        assertFalse(detector.isValid("123."))    // Nothing after period
        assertFalse(detector.isValid(".a"))      // 'else' branch in Period
        assertFalse(detector.isValid("0..1"))    // Second period directly after first
    }

    // --- 6. FRACTION PART STATE BRANCH COVERAGE ---

    @Test
    fun `invalid characters in fraction part`() {
        assertFalse(detector.isValid("123.123.")) // 'else' branch: second period after fraction
        assertFalse(detector.isValid("123.02a"))  // 'else' branch: letter after fraction
        assertFalse(detector.isValid("1.0 "))     // Trailing space
    }

    // --- 7. DIRECT STATE & REJECT STATE COVERAGE ---

    @Test
    fun `verify state properties and reject state persistence`() {
        // Direct test for Reject state transitions & properties to ensure 100% branch coverage
        val reject = ConcreteFloatState.Reject
        assertFalse(reject.isAccepting)
        assertEquals(reject, reject.consume('a'))
        assertEquals(reject, reject.consume('1'))

        // Verify isAccepting properties on non-accepting states explicitly
        assertFalse(ConcreteFloatState.Start.isAccepting)
        assertFalse(ConcreteFloatState.LeadingZero.isAccepting)
        assertFalse(ConcreteFloatState.IntegerPart.isAccepting)
        assertFalse(ConcreteFloatState.Period.isAccepting)
        assertTrue(ConcreteFloatState.FractionPart.isAccepting)
    }
}