import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BinaryDetectorTest {

    private val detector = BinaryDetector()

    // --- 1. VALID BINARY NUMBERS ---

    @Test
    fun `valid binary numbers`() {
        assertTrue(detector.isValid("1"))
        assertTrue(detector.isValid("11"))
        assertTrue(detector.isValid("101"))
        assertTrue(detector.isValid("111111"))
        assertTrue(detector.isValid("10011010001"))
    }

    // --- 2. INVALID START STATE BRANCHES ---

    @Test
    fun `invalid start characters`() {
        assertFalse(detector.isValid(""))        // Empty string
        assertFalse(detector.isValid("01"))      // Starts with '0'
        assertFalse(detector.isValid("a1"))      // Starts with letter
    }

    // --- 3. INVALID ENDINGS & TRANSITIONS ---

    @Test
    fun `invalid binary endings`() {
        assertFalse(detector.isValid("10"))      // Ends in '0'
        assertFalse(detector.isValid("1000010")) // Ends in '0'
    }

    @Test
    fun `invalid characters in sequence`() {
        assertFalse(detector.isValid("100a01"))  // Invalid char after '0'
        assertFalse(detector.isValid("11a1"))    // Invalid char after '1'
        assertFalse(detector.isValid("101 1"))   // Space
    }

    // --- 4. DIRECT STATE & REJECT COVERAGE ---

    @Test
    fun `verify state properties and reject state persistence`() {
        val reject = ConcreteBinaryState.Reject
        assertFalse(reject.isCurrentlyValid)
        assertEquals(reject, reject.consume('1'))
        assertEquals(reject, reject.consume('0'))

        assertFalse(ConcreteBinaryState.Start.isCurrentlyValid)
        assertTrue(ConcreteBinaryState.EndsWithOne.isCurrentlyValid)
        assertFalse(ConcreteBinaryState.EndsWithZero.isCurrentlyValid)
    }
}