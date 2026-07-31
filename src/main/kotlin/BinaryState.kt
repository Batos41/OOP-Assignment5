sealed class ConcreteBinaryState : State {

    object Start : ConcreteBinaryState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            '1' -> EndsWithOne
            else -> Reject
        }
    }

    object EndsWithOne : ConcreteBinaryState() {
        override val isCurrentlyValid: Boolean = true

        override fun consume(char: Char): State = when (char) {
            '1' -> EndsWithOne
            '0' -> EndsWithZero
            else -> Reject
        }
    }

    object EndsWithZero : ConcreteBinaryState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            '1' -> EndsWithOne
            '0' -> EndsWithZero
            else -> Reject
        }
    }

    object Reject : ConcreteBinaryState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = Reject
    }
}