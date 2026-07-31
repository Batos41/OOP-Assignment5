/**
 * Generic state interface for all Deterministic Finite Automata (DFA).
 */
interface State {
    fun consume(char: Char): State
    val isCurrentlyValid: Boolean
}