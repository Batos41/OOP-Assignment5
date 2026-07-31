/**
 * Abstract Strategy & Base Context for all pattern detectors.
 */
abstract class StringDetector(
    private val initialState: State,
    private val rejectState: State? = null
) {
    open fun isValid(input: String): Boolean {
        if (input.isEmpty()) return false

        var currentState = initialState

        for (char in input) {
            currentState = currentState.consume(char)

            // Short-circuit if a reject state is defined
            if (rejectState != null && currentState == rejectState) {
                return false
            }
        }

        return currentState.isCurrentlyValid
    }
}

class IntegerDetector : StringDetector(
    initialState = ConcreteIntegerState.Start,
    rejectState = ConcreteIntegerState.Reject
)

class FloatDetector : StringDetector(
    initialState = ConcreteFloatState.Start,
    rejectState = ConcreteFloatState.Reject
)

class BinaryDetector : StringDetector(
    initialState = ConcreteBinaryState.Start,
    rejectState = ConcreteBinaryState.Reject
)

class EmailDetector : StringDetector(
    initialState = ConcreteEmailState.Part1,
    rejectState = ConcreteEmailState.Reject
)

class PasswordDetector : StringDetector(
    initialState = ConcretePasswordState.Start
    // No rejectState passed!
) {
    override fun isValid(input: String): Boolean {
        if (input.length < 8) return false
        return super.isValid(input)
    }
}