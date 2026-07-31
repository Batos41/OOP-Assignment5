class IntegerDetector : StringDetector {

    override fun isValid(input: String): Boolean {
        // Empty strings are invalid per assignment spec
        if (input.isEmpty()) return false

        var currentState: IntegerState = ConcreteIntegerState.Start

        for (char in input) {
            currentState = currentState.consume(char)

            // Early exit / short-circuit if we hit the dead state
            if (currentState is ConcreteIntegerState.Reject) {
                return false
            }
        }

        return currentState.isAccepting
    }
}