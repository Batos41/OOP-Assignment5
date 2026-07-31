interface FloatState {
    fun consume(char: Char): FloatState
    val isAccepting: Boolean
}

sealed class ConcreteFloatState : FloatState {

    object Start : ConcreteFloatState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): FloatState = when (char) {
            '0' -> LeadingZero
            in '1'..'9' -> IntegerPart
            '.' -> Period
            else -> Reject
        }
    }

    object LeadingZero : ConcreteFloatState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): FloatState = when (char) {
            '.' -> Period
            else -> Reject
        }
    }

    object IntegerPart : ConcreteFloatState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): FloatState = when (char) {
            in '0'..'9' -> IntegerPart
            '.' -> Period
            else -> Reject
        }
    }

    object Period : ConcreteFloatState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): FloatState = when (char) {
            in '0'..'9' -> FractionPart
            else -> Reject
        }
    }

    object FractionPart : ConcreteFloatState() {
        override val isAccepting: Boolean = true

        override fun consume(char: Char): FloatState = when (char) {
            in '0'..'9' -> FractionPart
            else -> Reject
        }
    }

    object Reject : ConcreteFloatState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): FloatState = Reject
    }
}