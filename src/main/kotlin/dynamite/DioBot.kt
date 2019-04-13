package dynamite

import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.*
import kotlin.random.Random

var myDynamites: Int = 100
var yourDynamites: Int = 100

class DioBot: Bot {
  private val guesstimator = Guesstimator()

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

    if (currentRound > 0) {
      val lastRound = gamestate.rounds[currentRound - 1]
      guesstimator.update(lastRound.p1, lastRound.p2)
      if(lastRound.p1 === Move.D) myDynamites--
      if(lastRound.p2 === Move.D) yourDynamites--
    }

    if (gamestate.rounds.size == 0) return randomMove()

    val nextMove = guesstimator.nextMove(gamestate)

    return checkDynamites(nextMove)
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

fun randomNumber(floor: Int): Int { return Random.nextInt(1, floor) }

fun randomMove(): Move {
  val options = arrayOf<Move>(Move.R, Move.P, Move.S, Move.D)

  return options[randomNumber(options.size)]
}

fun randomRPSMove(): Move {
  val options = arrayOf<Move>(Move.R, Move.P, Move.S)

  return options[randomNumber(options.size)]
}
