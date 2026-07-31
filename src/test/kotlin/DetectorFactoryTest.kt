import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DetectorFactoryTest {

    @Test
    fun `factory creates correct detector instances for each type`() {
        assertTrue(DetectorFactory.createDetector(DetectorType.INTEGER) is IntegerDetector)
        assertTrue(DetectorFactory.createDetector(DetectorType.FLOAT) is FloatDetector)
        assertTrue(DetectorFactory.createDetector(DetectorType.BINARY) is BinaryDetector)
        assertTrue(DetectorFactory.createDetector(DetectorType.EMAIL) is EmailDetector)
        assertTrue(DetectorFactory.createDetector(DetectorType.PASSWORD) is PasswordDetector)
    }

    @Test
    fun `ensure all enum values are mapped in factory`() {
        // Guarantees 100% coverage on the factory switch/when statement across all enum variants
        for (type in DetectorType.entries) {
            val detector = DetectorFactory.createDetector(type)
            assertNotNull(detector)
        }
    }
}