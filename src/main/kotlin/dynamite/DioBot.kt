package dynamite

import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.*
import kotlin.random.Random

const val MAX_DYNAMITES: Int = 100

class DioBot(private var myDynamites: Int = MAX_DYNAMITES, private var yourDynamites: Int = MAX_DYNAMITES, private var draws: Int = 0) : Bot{
  private val score = Score()
  private val markov = MarkovChain()

  private fun checkDynamites(move: Move): Move {
    if (yourDynamites == 0 && move === Move.W) {
      return randomRPSMove()
    } else if (myDynamites == 0 && move === Move.D) {
      return randomRPSMove()
    }

    return move
  }

  override fun makeMove(gamestate: Gamestate): Move {
    val currentRound = gamestate.rounds.size

    if (currentRound > 1) {
      val lastRound = gamestate.rounds[currentRound - 1]
      update(gamestate, lastRound)
      if(lastRound.p1 === Move.D) myDynamites--
      if(lastRound.p2 === Move.D) yourDynamites--
    }

    if (currentRound < 2) return randomRPSMove()

    val predictedMove = markov.nextMove(gamestate.rounds.last().p2)

    val nextMove = beatPredictedMove(predictedMove)

    return checkDynamites(nextMove)
  }

  fun update(gamestate: Gamestate, lastRound: Round) {
    score.update(lastRound.p1, lastRound.p2)
    markov.update(gamestate.rounds[gamestate.rounds.size - 2].p2, lastRound.p2)

    draws = if (lastRound.p1 == lastRound.p2) draws + 1 else 0
  }
}

fun beatPredictedMove(yourMove: Move): Move {
  val strategy = Random.nextFloat()
  val useBalloon = 0.2f
  val useDynamite = 0.8f

  return when (yourMove) {
    Move.S -> if (strategy < useDynamite) return Move.R else Move.D
    Move.R -> if (strategy < useDynamite) return Move.P else Move.D
    Move.P -> if (strategy < useDynamite) return Move.S else Move.D
    Move.D -> if (strategy < useBalloon) return Move.W else randomMove()
    else   -> randomMove()
  }
}

fun randomMove(): Move {
  val options = arrayOf<Move>(Move.R, Move.P, Move.S, Move.D)
  val randomNumber = (0..3).shuffled().first()

  return options[randomNumber]
}

fun randomRPSMove(): Move {
  val options = arrayOf<Move>(Move.R, Move.P, Move.S)
  val randomNumber = (0..2).shuffled().first()

  return options[randomNumber]
}
