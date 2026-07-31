class BinaryDetector : StringDetector {

    override fun isValid(input: String): Boolean {
        if (input.isEmpty()) return false

        var currentState: BinaryState = ConcreteBinaryState.Start

        for (char in input) {
            currentState = currentState.consume(char)

            if (currentState is ConcreteBinaryState.Reject) {
                return false
            }
        }

        return currentState.isAccepting
    }
}