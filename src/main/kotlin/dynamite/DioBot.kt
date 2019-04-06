package dynamite

import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.*
import com.softwire.dynamite.runner.DynamiteRunner
import com.softwire.dynamite.game.Round
import kotlin.random.Random

const val MAX_DYNAMITES = 100
const val WINNING_SCORE = 1000

class DioBot(private var myDynamites: Int = MAX_DYNAMITES, private var yourDynamites: Int = MAX_DYNAMITES) : Bot {

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

    val nextMove = guesstimator.nextMove(gamestate)

    return checkDynamites(nextMove)
  }

}

class Guesstimator(var draws: Int = 0) {

  fun nextMove(gamestate: Gamestate): Move {
//    Make random move on first turn
    if (gamestate.rounds.size == 0) return randomMove()

//    Beat a repeater bot
    if (checkForRepeater(gamestate)) return counterRepeaterMove(gamestate.rounds[0].p2)

    return randomMove()
  }

  fun update(myMove: Move, yourMove: Move) {
//    This should use the planned scorer to decide when to change strategy

    draws = if (myMove == yourMove) draws + 1 else 0
  }

  private fun checkForRepeater(gamestate: Gamestate): Boolean {
    val yourFirstMove = gamestate.rounds[0].p2

    for (i in gamestate.rounds.indices) {
      if (gamestate.rounds[i].p2 !== yourFirstMove) return false
    }

    return true
  }

}

// MOVES

fun counterRepeaterMove(yourMove: Move): Move {
  if (yourMove === Move.R) return Move.P
  if (yourMove === Move.P) return Move.S
  if (yourMove === Move.S) return Move.R
  if (yourMove === Move.D) return Move.W
  return Move.P
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
