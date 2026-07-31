class EmailDetector : StringDetector {

    override fun isValid(input: String): Boolean {
        if (input.isEmpty()) return false

        var currentState: EmailState = ConcreteEmailState.Part1

        for (char in input) {
            currentState = currentState.consume(char)

            if (currentState is ConcreteEmailState.Reject) {
                return false
            }
        }

        return currentState.isAccepting
    }
}