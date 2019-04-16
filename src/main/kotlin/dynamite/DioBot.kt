package dynamite

import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.game.*

const val MAX_DYNAMITES: Int = 100

class DioBot(private var myDynamites: Int = MAX_DYNAMITES, private var yourDynamites: Int = MAX_DYNAMITES, private var draws: Int = 0) : Bot {
    private val Score = ScoreKeeper()
    private val MakeMove = MoveMaker()
    private val Markov = MarkovChain(Move.values().size)

    private fun checkDynamites(move: Move): Move {
        if (yourDynamites == 0 && move === Move.W) {
            return MakeMove.randomRPSMove()
        } else if (myDynamites == 0 && move === Move.D) {
            return MakeMove.randomRPSMove()
        }

        return move
    }

    override fun makeMove(gamestate: Gamestate): Move {
        val currentRound = gamestate.rounds.size

        if (currentRound > 1) {
            val lastRound = gamestate.rounds[currentRound - 1]
            update(gamestate, lastRound)
            if (lastRound.p1 === Move.D) myDynamites--
            if (lastRound.p2 === Move.D) yourDynamites--
        }

        if (currentRound < 2) return MakeMove.randomRPSMove()

        val predictedMove = Markov.nextMove(gamestate.rounds.last().p2)

        val nextMove = MakeMove.beatPredictedMove(predictedMove)

        return checkDynamites(nextMove)
    }

    private fun update(gamestate: Gamestate, lastRound: Round) {
        Score.update(lastRound.p1, lastRound.p2)
        Markov.update(gamestate.rounds[gamestate.rounds.size - 2].p2, lastRound.p2)

        draws = if (lastRound.p1 == lastRound.p2) draws + 1 else 0
    }
}
