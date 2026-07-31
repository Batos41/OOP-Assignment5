/**
 * Strategy interface for all pattern detectors.
 */
fun interface StringDetector {
    fun isValid(input: String): Boolean
}