interface PasswordState {
    fun consume(char: Char): PasswordState
    val isAccepting: Boolean
}

sealed class ConcretePasswordState : PasswordState {

    companion object {
        const val SPECIAL_CHARS = "!@#$%&*"
        fun isSpecial(c: Char) = c in SPECIAL_CHARS
        fun isUpper(c: Char) = c in 'A'..'Z'
    }

    object Start : ConcretePasswordState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): PasswordState = when {
            isUpper(char) -> HasUpper
            isSpecial(char) -> HasSpecial
            else -> Neither
        }
    }

    object Neither : ConcretePasswordState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): PasswordState = when {
            isUpper(char) -> HasUpper
            isSpecial(char) -> HasSpecial
            else -> Neither
        }
    }

    object HasUpper : ConcretePasswordState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): PasswordState = when {
            isSpecial(char) -> BothEndsInSpecial
            else -> HasUpper
        }
    }

    object HasSpecial : ConcretePasswordState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): PasswordState = when {
            isUpper(char) -> BothEndsInValid
            else -> HasSpecial
        }
    }

    object BothEndsInValid : ConcretePasswordState() {
        override val isAccepting: Boolean = true

        override fun consume(char: Char): PasswordState = when {
            isSpecial(char) -> BothEndsInSpecial
            else -> BothEndsInValid
        }
    }

    object BothEndsInSpecial : ConcretePasswordState() {
        override val isAccepting: Boolean = false

        override fun consume(char: Char): PasswordState = when {
            isSpecial(char) -> BothEndsInSpecial
            else -> BothEndsInValid
        }
    }
}