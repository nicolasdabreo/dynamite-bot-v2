package dynamite

import com.softwire.dynamite.game.Gamestate
import com.softwire.dynamite.game.Move
import kotlin.random.Random

class Guesstimator(var draws: Int = 0, var strategy: Int = 0) {
  private val score = Score()
  private val markov = MarkovChain()

  fun nextMove(gamestate: Gamestate): Move {
    if (gamestate.rounds.size == 0) return randomMove()

    if (draws >= 2) return drawsPredictor(draws)

    if (isRepeater(gamestate)) return beatPredictedMove(gamestate.rounds[0].p2)

    val predictedMove = markov.nextMove(gamestate.rounds.last().p2)

    return beatPredictedMove(predictedMove)
  }

  fun update(myMove: Move, yourMove: Move) {
    score.update(myMove, yourMove)
    markov.update(myMove, yourMove)

    draws = if (myMove == yourMove) draws + 1 else 0
  }

  private fun drawsPredictor(draws: Int): Move {
    val strategy = Random.nextFloat()
    val useRandom = 0.2f
    val useDynamite = 0.5f

    return when {
      strategy < useRandom -> randomMove()
      (strategy - useRandom) < useDynamite -> Move.D
      else -> Move.W
    }
  }

  private fun isRepeater(gamestate: Gamestate): Boolean {
    val yourFirstMove = gamestate.rounds[0].p2

    for (i in gamestate.rounds.indices) {
      if (gamestate.rounds[i].p2 !== yourFirstMove) return false
    }

    return true
  }
}
