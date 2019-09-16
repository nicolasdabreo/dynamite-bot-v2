# dynamite-bot-v2
Second attempt at a Rock-Paper-Scissors-Dynamite bot created for https://dynamite.softwire.com

# Background
Dynamite is a coding challenge organised by Softwire where bots would play Rock-Paper-Scissors-Dynamite, the first to 1000 wins.

This is both my first time using Kotlin as well as my first time doing true Object-Oriented Programming, coming from JavaScript, React and Elixir.

# Insight
This bot primarily uses a Markov Chain to analyse the opposing bot's strategy, therefore predicting their next move in reaction to the bot's own previous move and playing it against them.

Meanwhile the bot analyses its own current strategy effectiveness over a set period and alters strategy accordingly.

Unfortunately while running locally using my own botrunner the Java runner when it came to uploading him on the Dynamite server, the bot was incompatible and I didn't have enough time to figure out why.
