interface IntegerState {
    fun consume(char: Char): IntegerState
    val isAccepting: Boolean
}

sealed class ConcreteIntegerState : IntegerState {

    object Start : ConcreteIntegerState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): IntegerState = when (char) {
            in '1'..'9' -> Valid
            else -> Reject
        }
    }

    object Valid : ConcreteIntegerState() {
        override val isAccepting: Boolean = true

        override fun consume(char: Char): IntegerState = when (char) {
            in '0'..'9' -> Valid
            else -> Reject
        }
    }

    object Reject : ConcreteIntegerState() {
        override val isAccepting: Boolean = false

        // Once in Reject state, stay in Reject state
        override fun consume(char: Char): IntegerState = Reject
    }
}