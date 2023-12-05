import java.io.File
import java.io.InputStream

class Turn(val green: Int, val red: Int, val blue: Int)

class Game(val id: Int, val turns: Set<Turn>, val totalRed: Int, val totalBlue: Int, val totalGreen: Int)

val inputStream: InputStream = File("input.txt").inputStream()
val games = mutableListOf<Game>()

val redThreshold = 12
val greenThreshold = 13
val blueThreshold = 14
var totalIdUnderThreshold = 0

inputStream.bufferedReader().forEachLine {

    val id = it.split(":")[0].filter { d -> d.isDigit() }.toInt()

    var totalRed = 0
    var totalGreen = 0
    var totalBlue = 0
    val turns = mutableSetOf<Turn>()
    it.split(":")[1].split(";").forEach {turn ->
        var numberRed = 0
        var numberGreen = 0
        var numberBlue = 0
        turn.split(",").forEach { set ->
            if (set.contains("blue")) {
                numberBlue = set.filter { d -> d.isDigit() }.toInt()
            } else if (set.contains("red")) {
                numberRed = set.filter { d -> d.isDigit() }.toInt()
            } else if (set.contains("green")) {
                numberGreen = set.filter { d -> d.isDigit() }.toInt()
            }
        }
        totalGreen += numberGreen
        totalBlue += numberBlue
        totalRed += numberRed
        turns.add(Turn(green = numberGreen, blue = numberBlue, red = numberRed))
    }

    val gamePossible = turns.none { turn -> turn.blue > blueThreshold || turn.red > redThreshold || turn.green > greenThreshold }

    if (gamePossible) {
        println("Game $id is possible")
        totalIdUnderThreshold += id
    } else {
        println("Game $id is impossible")
    }

    games.add(Game(totalRed = totalRed, totalBlue = totalBlue, totalGreen = totalGreen, turns = turns, id = id))
}

println(totalIdUnderThreshold)