class PasswordDetector : StringDetector {

    override fun isValid(input: String): Boolean {
        // Enforce length requirement directly in the detector
        if (input.length < 8) return false

        var currentState: PasswordState = ConcretePasswordState.Start

        for (char in input) {
            currentState = currentState.consume(char)
        }

        return currentState.isAccepting
    }
}