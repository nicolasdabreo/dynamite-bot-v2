package runner

import com.softwire.dynamite.bot.Bot
import com.softwire.dynamite.runner.DynamiteRunner
import dynamite.DioBot

object BotRunner {
    @JvmStatic
    fun main(args: Array<String>) {
        DynamiteRunner.playGames(DynamiteRunner.Factory<Bot> { DioBot() })
    }
}