sealed class ConcreteFloatState : State {

    object Start : ConcreteFloatState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            '0' -> LeadingZero
            in '1'..'9' -> IntegerPart
            '.' -> Period
            else -> Reject
        }
    }

    object LeadingZero : ConcreteFloatState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            '.' -> Period
            else -> Reject
        }
    }

    object IntegerPart : ConcreteFloatState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            in '0'..'9' -> IntegerPart
            '.' -> Period
            else -> Reject
        }
    }

    object Period : ConcreteFloatState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            in '0'..'9' -> FractionPart
            else -> Reject
        }
    }

    object FractionPart : ConcreteFloatState() {
        override val isCurrentlyValid: Boolean = true

        override fun consume(char: Char): State = when (char) {
            in '0'..'9' -> FractionPart
            else -> Reject
        }
    }

    object Reject : ConcreteFloatState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = Reject
    }
}