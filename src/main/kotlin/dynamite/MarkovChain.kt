package dynamite

import com.softwire.dynamite.game.Move

class MarkovChain {
  private var markovChain: Array<IntArray>? = null

  init {
    val length = Move.values().size
    markovChain = Array(length) { IntArray(length) }

    for (i in 0 until length) {
      for (j in 0 until length) {
        markovChain !![i][j] = 0
      }
    }
  }

  fun update(prev: Move, next: Move) {
    markovChain !![prev.ordinal][next.ordinal] ++
  }

  fun nextMove(prev: Move?): Move {
    var nextIndex = 0

    for (i in 0 until Move.values().size) {
      val prevIndex = prev !!.ordinal

      if (markovChain !![prevIndex][i] > markovChain !![prevIndex][nextIndex]) {
        nextIndex = i
      }
    }

    return Move.values()[nextIndex]
  }
}