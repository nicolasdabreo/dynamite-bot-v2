package dynamite

import com.softwire.dynamite.game.Move
import kotlin.random.Random

class MoveMaker {
    val Score = ScoreKeeper()

    fun beatPredictedMove(move: Move): Move {
        val movesThatBeatYours: List<Move> = Move.values().filter { m -> m !== Move.D && Score.getResultsOf(m, move) == 1 }

        return randomChoice(movesThatBeatYours)
    }

    fun randomMove(): Move { return randomChoice(listOf<Move>(Move.R, Move.P, Move.S, Move.D)) }

    fun randomRPSMove(): Move { return randomChoice(listOf<Move>(Move.R, Move.P, Move.S)) }

    private fun randomNumber(limit: Int): Int { return Random.nextInt(0, limit) }

    private fun randomChoice(arr: List<Move>): Move { return arr[randomNumber(arr.size)] }
}