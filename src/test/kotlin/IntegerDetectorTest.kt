import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IntegerDetectorTest {

    private val detector = IntegerDetector()

    // --- 1. VALID INTEGERS (Covering Accept States) ---

    @Test
    fun `valid single digit integers`() {
        assertTrue(detector.isValid("1"))
        assertTrue(detector.isValid("5"))
        assertTrue(detector.isValid("9"))
    }

    @Test
    fun `valid multi-digit integers`() {
        assertTrue(detector.isValid("123"))
        assertTrue(detector.isValid("100")) // Valid state consuming '0'
        assertTrue(detector.isValid("3452342352434534524346"))
    }

    // --- 2. START STATE BRANCH COVERAGE ---

    @Test
    fun `invalid start characters`() {
        assertFalse(detector.isValid(""))        // Empty string guard
        assertFalse(detector.isValid("0"))       // 'else' branch: starts with '0'
        assertFalse(detector.isValid("0123"))    // 'else' branch: starts with '0'
        assertFalse(detector.isValid("a123"))    // 'else' branch: starts with letter
        assertFalse(detector.isValid("-123"))    // 'else' branch: starts with minus sign
        assertFalse(detector.isValid(" 123"))    // 'else' branch: starts with space
    }

    // --- 3. VALID STATE BRANCH COVERAGE ---

    @Test
    fun `invalid characters in valid state`() {
        assertFalse(detector.isValid("132a"))    // 'else' branch: letter inside integer
        assertFalse(detector.isValid("12.3"))    // 'else' branch: period inside integer
        assertFalse(detector.isValid("12 3"))    // 'else' branch: space inside integer
        assertFalse(detector.isValid("123!"))    // 'else' branch: symbol inside integer
    }

    // --- 4. DIRECT STATE & REJECT STATE COVERAGE ---

    @Test
    fun `verify state properties and reject state persistence`() {
        // Direct test for Reject state transitions & properties
        val reject = ConcreteIntegerState.Reject
        assertFalse(reject.isCurrentlyValid)
        assertEquals(reject, reject.consume('a'))
        assertEquals(reject, reject.consume('1'))

        // Verify isAccepting properties on states directly
        assertFalse(ConcreteIntegerState.Start.isCurrentlyValid)
        assertTrue(ConcreteIntegerState.Valid.isCurrentlyValid)
    }
}