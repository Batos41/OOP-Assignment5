private const val SPECIAL_CHARS = "!@#$%&*"

private val Char.isSpecial: Boolean
    get() = this in SPECIAL_CHARS

private val Char.isUpperChar: Boolean
    get() = this in 'A'..'Z'

sealed class ConcretePasswordState : State {

    object Start : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            char.isUpperChar -> HasUpper
            char.isSpecial -> HasSpecial
            else -> Neither
        }
    }

    object Neither : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            char.isUpperChar -> HasUpper
            char.isSpecial -> HasSpecial
            else -> Neither
        }
    }

    object HasUpper : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            char.isSpecial -> BothEndsInSpecial
            else -> HasUpper
        }
    }

    object HasSpecial : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            char.isUpperChar -> BothEndsInValid
            else -> HasSpecial
        }
    }

    object BothEndsInValid : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = true

        override fun consume(char: Char): State = when {
            char.isSpecial -> BothEndsInSpecial
            else -> BothEndsInValid
        }
    }

    object BothEndsInSpecial : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = when {
            char.isSpecial -> BothEndsInSpecial
            else -> BothEndsInValid
        }
    }

    object Reject : ConcretePasswordState() {
        override val isCurrentlyValid: Boolean = false

        override fun consume(char: Char): State = Reject
    }
}