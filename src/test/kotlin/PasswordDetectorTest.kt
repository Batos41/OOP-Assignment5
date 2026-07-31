import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PasswordDetectorTest {

    private val detector = PasswordDetector()

    // --- 1. VALID PASSWORDS ---

    @Test
    fun `valid complex passwords`() {
        assertTrue(detector.isValid("aaaaH!aa"))
        assertTrue(detector.isValid("1234567*9J"))
        assertTrue(detector.isValid("asdpoihj;loikjasdf;ijp;lij2309jasd;lfkm20ij@aH"))
        assertTrue(detector.isValid("!aaaaaaaH")) // Special first, ends in upper
        assertTrue(detector.isValid("H!aaaaaaa")) // Upper first, ends in normal char
    }

    // --- 2. INVALID LENGTH BRANCHES ---

    @Test
    fun `invalid passwords due to length`() {
        assertFalse(detector.isValid(""))          // Empty string
        assertFalse(detector.isValid("a"))         // Too short
        assertFalse(detector.isValid("H!12347"))   // 7 chars (too short despite meeting rules)
    }

    // --- 3. MISSING REQUIREMENTS BRANCHES ---

    @Test
    fun `invalid passwords missing capital or special char`() {
        assertFalse(detector.isValid("aaaaaaa!"))  // No capital letter
        assertFalse(detector.isValid("aaaHaaaaa")) // No special character
        assertFalse(detector.isValid("aaaaaaaaa")) // Missing both
    }

    // --- 4. ENDS IN SPECIAL CHAR BRANCHES ---

    @Test
    fun `invalid passwords ending in special character`() {
        assertFalse(detector.isValid("Abbbbbbb!")) // Ends with !
        assertFalse(detector.isValid("1234567J*")) // Ends with *
        assertFalse(detector.isValid("aH123456@")) // Ends with @
    }

    // --- 5. DIRECT STATE COVERAGE ---

    @Test
    fun `verify state properties and transitions`() {
        assertFalse(ConcretePasswordState.Start.isCurrentlyValid)
        assertFalse(ConcretePasswordState.Neither.isCurrentlyValid)
        assertFalse(ConcretePasswordState.HasUpper.isCurrentlyValid)
        assertFalse(ConcretePasswordState.HasSpecial.isCurrentlyValid)
        assertTrue(ConcretePasswordState.BothEndsInValid.isCurrentlyValid)
        assertFalse(ConcretePasswordState.BothEndsInSpecial.isCurrentlyValid)

        // Exercise Neither state self-loop
        val state = ConcretePasswordState.Start.consume('a')
        assertEquals(ConcretePasswordState.Neither, state.consume('b'))
    }

    @Test
    fun `exercise self loop in BothEndsInValid and BothEndsInSpecial`() {
        // 1. "H!" gets us to BothEndsInValid
        // 2. "a" tests the 'else' branch in BothEndsInValid (remains in BothEndsInValid)
        // 3. "!" moves us to BothEndsInSpecial
        // 4. "!" tests the 'isSpecial' branch in BothEndsInSpecial (self-loop)
        // 5. "a" moves us back to BothEndsInValid (non-special after special)
        assertTrue(detector.isValid("H!a!a123"))
    }

    @Test
    fun `explicit branch coverage for BothEndsInValid`() {
        val state = ConcretePasswordState.BothEndsInValid

        // Branch 1: Special character
        assertEquals(ConcretePasswordState.BothEndsInSpecial, state.consume('!'))

        // Branch 2: Non-special character (the missing branch!)
        assertEquals(ConcretePasswordState.BothEndsInValid, state.consume('a'))
    }

    @Test
    fun `exercise consecutive special characters in BothEndsInSpecial`() {
        // 1. "H!" gets us into BothEndsInSpecial
        // 2. The second "!" triggers the isSpecial branch in BothEndsInSpecial (self-loop)
        // 3. "a" moves us out to BothEndsInValid so the overall password remains valid
        assertTrue(detector.isValid("H!!a1234"))
    }

    @Test
    fun `explicit branch coverage for BothEndsInSpecial`() {
        val state = ConcretePasswordState.BothEndsInSpecial

        // Branch 1: Special character (consecutive special char self-loop)
        assertEquals(ConcretePasswordState.BothEndsInSpecial, state.consume('!'))

        // Branch 2: Non-special character (recovers back to valid)
        assertEquals(ConcretePasswordState.BothEndsInValid, state.consume('a'))
    }
}