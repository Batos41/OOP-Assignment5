sealed class ConcreteIntegerState : State {

    object Start : ConcreteIntegerState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            in '1'..'9' -> Valid
            else -> Reject
        }
    }

    object Valid : ConcreteIntegerState() {
        override val isCurrentlyValid: Boolean = true

        override fun consume(char: Char): State = when (char) {
            in '0'..'9' -> Valid
            else -> Reject
        }
    }

    object Reject : ConcreteIntegerState() {
        override val isCurrentlyValid: Boolean = false

        // Once in Reject state, stay in Reject state
        override fun consume(char: Char): State = Reject
    }
}