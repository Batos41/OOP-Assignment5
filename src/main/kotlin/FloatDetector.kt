class FloatDetector : StringDetector {

    override fun isValid(input: String): Boolean {
        if (input.isEmpty()) return false

        var currentState: FloatState = ConcreteFloatState.Start

        for (char in input) {
            currentState = currentState.consume(char)

            if (currentState is ConcreteFloatState.Reject) {
                return false
            }
        }

        return currentState.isAccepting
    }
}