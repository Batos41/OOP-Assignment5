import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EmailDetectorTest {

    private val detector = EmailDetector()

    // --- 1. VALID EMAIL ADDRESSES ---

    @Test
    fun `valid email addresses`() {
        assertTrue(detector.isValid("a@b.c"))
        assertTrue(detector.isValid("joseph.ditton@usu.edu"))
        assertTrue(detector.isValid("{}*$.&$*(@*$%&.*&*"))
        assertTrue(detector.isValid("user.name+tag@domain.com")) // Period in Part 1 is allowed
    }

    // --- 2. INVALID PART 1 BRANCHES ---

    @Test
    fun `invalid part 1`() {
        assertFalse(detector.isValid(""))            // Empty input
        assertFalse(detector.isValid("@b.c"))        // Part 1 empty
        assertFalse(detector.isValid(" joseph@b.c"))  // Leading space
        assertFalse(detector.isValid("joseph @b.c")) // Space in Part 1
        assertFalse(detector.isValid("user.name+tag@sub.domain.com")) // 2 periods after Part 1
    }

    // --- 3. INVALID PART 2 BRANCHES ---

    @Test
    fun `invalid part 2`() {
        assertFalse(detector.isValid("a@.c"))        // Part 2 empty
        assertFalse(detector.isValid("a@b@c.com"))   // Multiple @ symbols
        assertFalse(detector.isValid("a@ b.c"))      // Space after @
        assertFalse(detector.isValid("a@b .c"))      // Space in Part 2
    }

    // --- 4. INVALID PART 3 BRANCHES ---

    @Test
    fun `invalid part 3`() {
        assertFalse(detector.isValid("a@b."))        // Part 3 empty
        assertFalse(detector.isValid("a.b@b.b.c"))   // Too many periods after @
        assertFalse(detector.isValid("a@b.c.d"))     // Too many periods after @
        assertFalse(detector.isValid("a@b. c"))      // Space after dot
        assertFalse(detector.isValid("a@b.c "))      // Trailing space in Part 3
        assertFalse(detector.isValid("a@b.c@d"))     // @ symbol in Part 3
    }

    // --- 5. DIRECT STATE & REJECT COVERAGE ---

    @Test
    fun `verify state properties and reject state persistence`() {
        val reject = ConcreteEmailState.Reject
        assertFalse(reject.isCurrentlyValid)
        assertEquals(reject, reject.consume('a'))

        assertFalse(ConcreteEmailState.Part1.isCurrentlyValid)
        assertFalse(ConcreteEmailState.Part1Valid.isCurrentlyValid)
        assertFalse(ConcreteEmailState.Part2Valid.isCurrentlyValid)
        assertFalse(ConcreteEmailState.AfterAt.isCurrentlyValid)
        assertFalse(ConcreteEmailState.AfterDot.isCurrentlyValid)
        assertTrue(ConcreteEmailState.Part3Valid.isCurrentlyValid)
    }

    // --- 6. MISSING BRANCH COVERAGE FOR ASCII 46 (.) AND ASCII 64 (@) ---

    @Test
    fun `invalid consecutive special chars after at and after dot`() {
        // Char 64 (@) in AfterAt: Consecutive '@' symbols
        assertFalse(detector.isValid("a@@b.c"))

        // Char 64 (@) in AfterDot: '@' directly after the period
        assertFalse(detector.isValid("a@b.@c"))

        // Char 46 (.) in AfterDot: Consecutive periods directly after the period
        assertFalse(detector.isValid("a@b..c"))
    }
}