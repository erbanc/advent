import java.io.File
import java.io.InputStream

class Turn(val green: Int, val red: Int, val blue: Int)

class Game(val id: Int, val turns: Set<Turn>, val totalRed: Int, val totalBlue: Int, val totalGreen: Int)

val inputStream: InputStream = File("input.txt").inputStream()
val games = mutableListOf<Game>()

val redThreshold = 12
val greenThreshold = 13
val blueThreshold = 14
var totalPower = 0

inputStream.bufferedReader().forEachLine {

    val id = it.split(":")[0].filter { d -> d.isDigit() }.toInt()

    var minRed = 0
    var minGreen = 0
    var minBlue = 0
    val turns = mutableSetOf<Turn>()
    it.split(":")[1].split(";").forEach { turn ->
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
        if (numberRed > minRed) {
            minRed = numberRed
        }
        if (numberBlue > minBlue) {
            minBlue = numberBlue
        }
        if (numberGreen > minGreen) {
            minGreen = numberGreen
        }
        turns.add (Turn(green = numberGreen, blue = numberBlue, red = numberRed))
    }

    val gamePower = minGreen * minBlue * minRed

    totalPower += gamePower
}

println(totalPower)