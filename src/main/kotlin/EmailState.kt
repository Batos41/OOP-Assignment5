sealed class ConcreteEmailState : State {

    object Part1 : ConcreteEmailState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            ' ', '@' -> Reject
            else -> Part1Valid
        }
    }

    object Part1Valid : ConcreteEmailState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            ' ' -> Reject
            '@' -> AfterAt
            else -> Part1Valid
        }
    }

    object AfterAt : ConcreteEmailState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            ' ', '@', '.' -> Reject
            else -> Part2Valid
        }
    }

    object Part2Valid : ConcreteEmailState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            ' ', '@' -> Reject
            '.' -> AfterDot
            else -> Part2Valid
        }
    }

    object AfterDot : ConcreteEmailState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when (char) {
            ' ', '@', '.' -> Reject
            else -> Part3Valid
        }
    }

    object Part3Valid : ConcreteEmailState() {
        override val isCurrentlyValid: Boolean = true

        override fun consume(char: Char): State = when (char) {
            ' ', '@', '.' -> Reject
            else -> Part3Valid
        }
    }

    object Reject : ConcreteEmailState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = Reject
    }
}