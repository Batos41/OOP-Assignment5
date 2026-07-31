enum class DetectorType {
    INTEGER, FLOAT, BINARY, EMAIL, PASSWORD
}

object DetectorFactory {
    fun createDetector(type: DetectorType): StringDetector = when (type) {
        DetectorType.INTEGER -> IntegerDetector()
        DetectorType.FLOAT -> FloatDetector()
        DetectorType.BINARY -> BinaryDetector()
        DetectorType.EMAIL -> EmailDetector()
        DetectorType.PASSWORD -> PasswordDetector()
    }
}