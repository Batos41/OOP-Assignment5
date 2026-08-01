sealed class ConcretePasswordState : State {

    companion object {
        const val SPECIAL_CHARS = "!@#$%&*"
        fun isSpecial(c: Char) = c in SPECIAL_CHARS
        fun isUpper(c: Char) = c in 'A'..'Z'
    }

    object Start : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            isUpper(char) -> HasUpper
            isSpecial(char) -> HasSpecial
            else -> Neither
        }
    }

    object Neither : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            isUpper(char) -> HasUpper
            isSpecial(char) -> HasSpecial
            else -> Neither
        }
    }

    object HasUpper : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            isSpecial(char) -> BothEndsInSpecial
            else -> HasUpper
        }
    }

    object HasSpecial : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            isUpper(char) -> BothEndsInValid
            else -> HasSpecial
        }
    }

    object BothEndsInValid : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = true

        override fun consume(char: Char): State = when {
            isSpecial(char) -> BothEndsInSpecial
            else -> BothEndsInValid
        }
    }

    object BothEndsInSpecial : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            isSpecial(char) -> BothEndsInSpecial
            else -> BothEndsInValid
        }
    }

    object Reject : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = Reject
    }
}