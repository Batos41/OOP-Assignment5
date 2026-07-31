interface BinaryState {
    fun consume(char: Char): BinaryState
    val isAccepting: Boolean
}

sealed class ConcreteBinaryState : BinaryState {

    object Start : ConcreteBinaryState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): BinaryState = when (char) {
            '1' -> EndsWithOne
            else -> Reject
        }
    }

    object EndsWithOne : ConcreteBinaryState() {
        override val isAccepting: Boolean = true

        override fun consume(char: Char): BinaryState = when (char) {
            '1' -> EndsWithOne
            '0' -> EndsWithZero
            else -> Reject
        }
    }

    object EndsWithZero : ConcreteBinaryState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): BinaryState = when (char) {
            '1' -> EndsWithOne
            '0' -> EndsWithZero
            else -> Reject
        }
    }

    object Reject : ConcreteBinaryState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): BinaryState = Reject
    }
}