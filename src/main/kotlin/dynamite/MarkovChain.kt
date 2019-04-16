package dynamite

import com.softwire.dynamite.game.Move

class MarkovChain(private val length: Int) {
    private val markovChain: Array<IntArray> = Array(length) { IntArray(length) }

    fun update(prev: Move, next: Move) {
        markovChain[prev.ordinal][next.ordinal] ++
    }

    fun nextMove(prev: Move?): Move {
        var nextIndex = 0

        for (i in 0 until Move.values().size) {
            val prevIndex = prev !!.ordinal

            if (markovChain[prevIndex][i] > markovChain[prevIndex][nextIndex]) {
                nextIndex = i
            }
        }

        return Move.values()[nextIndex]
    }
}