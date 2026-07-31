interface EmailState {
    fun consume(char: Char): EmailState
    val isAccepting: Boolean
}

sealed class ConcreteEmailState : EmailState {

    object Part1 : ConcreteEmailState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): EmailState = when (char) {
            ' ', '@' -> Reject
            else -> Part1Valid
        }
    }

    object Part1Valid : ConcreteEmailState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): EmailState = when (char) {
            ' ' -> Reject
            '@' -> AfterAt
            else -> Part1Valid
        }
    }

    object AfterAt : ConcreteEmailState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): EmailState = when (char) {
            ' ', '@', '.' -> Reject
            else -> Part2Valid
        }
    }

    object Part2Valid : ConcreteEmailState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): EmailState = when (char) {
            ' ', '@' -> Reject
            '.' -> AfterDot
            else -> Part2Valid
        }
    }

    object AfterDot : ConcreteEmailState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): EmailState = when (char) {
            ' ', '@', '.' -> Reject
            else -> Part3Valid
        }
    }

    object Part3Valid : ConcreteEmailState() {
        override val isAccepting: Boolean = true

        override fun consume(char: Char): EmailState = when (char) {
            ' ', '@', '.' -> Reject
            else -> Part3Valid
        }
    }

    object Reject : ConcreteEmailState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): EmailState = Reject
    }
}