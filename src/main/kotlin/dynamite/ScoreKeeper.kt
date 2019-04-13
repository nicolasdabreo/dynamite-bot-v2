package dynamite

import com.softwire.dynamite.game.Move

private val ROCK: Map<Move, Int> = hashMapOf(
  Move.R to 0,
  Move.P to -1,
  Move.S to 1,
  Move.D to -1,
  Move.W to 1
)

private val PAPER: Map<Move, Int> = hashMapOf(
  Move.R to 1,
  Move.P to 0,
  Move.S to -1,
  Move.D to -1,
  Move.W to 1
)

private val SCISSORS: Map<Move, Int> = hashMapOf(
  Move.R to -1,
  Move.P to 1,
  Move.S to 0,
  Move.D to -1,
  Move.W to 1
)

private val DYNAMITE: Map<Move, Int> = hashMapOf(
  Move.R to 1,
  Move.P to 1,
  Move.S to 1,
  Move.D to 0,
  Move.W to -1
)

private val WATER_BALLOON: Map<Move, Int> = hashMapOf(
  Move.R to -1,
  Move.P to -1,
  Move.S to -1,
  Move.D to 1,
  Move.W to 0
)

class Score(private var myScore: Int = 0, private var yourScore: Int = 0, private var pointsForRound: Int = 0) {
  private val results: Map<Move, Map<Move, Int>> = hashMapOf(
    Move.R to ROCK,
    Move.P to PAPER,
    Move.S to SCISSORS,
    Move.D to DYNAMITE,
    Move.W to WATER_BALLOON
  )

  fun update(myMove: Move, yourMove: Move) {
    pointsForRound += 1

    if (results.getValue(myMove).getValue(yourMove) == 1) {
      myScore += pointsForRound
      pointsForRound = 0
    }

    if (results.getValue(yourMove).getValue(myMove) == 1) {
      yourScore += pointsForRound
      pointsForRound = 0
    }
  }
}
