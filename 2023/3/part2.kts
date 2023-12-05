import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()

class EnginePart(val y: Int, val startx: Int, val endx: Int, val value: Int, var isEnginePart: Boolean = false)
class Symbol(val y: Int, val x: Int, val linkedParts: MutableList<EnginePart> = mutableListOf(), var ratio: Int = 0)

val engineSchematic = mutableListOf<CharArray>()

inputStream.bufferedReader().forEachLine {
    engineSchematic.add(it.toCharArray())
}
val maxX = engineSchematic[0].size - 1
val maxY = engineSchematic.size - 1
val potentialEngineParts = mutableListOf<EnginePart>()
val symbols = mutableListOf<Symbol>()
for (y in 0..maxY) {
    for (x in 0..maxX) {
        val spot = engineSchematic[y][x]
        if (potentialEngineParts.any { part -> part.y == y && x >= part.startx && x <= part.endx }) {
            // already in part
        } else if (spot.isDigit()) {
            // detection of a new part
            var partValueString = spot.toString()
            var numberContinues = true
            var endX = x
            while (numberContinues && endX < maxX) {
                endX++
                val potentialSpot = engineSchematic[y][endX]
                if (potentialSpot.isDigit()) {
                    partValueString += potentialSpot
                } else {
                    numberContinues = false
                    endX--
                }
            }
            potentialEngineParts.add(EnginePart(y = y, startx = x, endx = endX, value = partValueString.toInt()))
        } else if (spot == '*') {
            symbols.add(Symbol(x = x, y = y))
        }
    }
}
var sumOfAllRatio = 0
for (potentialEnginePart in potentialEngineParts) {

    val minxsymbol = potentialEnginePart.startx - 1
    val maxxsymbol = potentialEnginePart.endx + 1
    val minysymbol = potentialEnginePart.y - 1
    val maxysymbol = potentialEnginePart.y + 1

    symbols.forEach { symbol ->
        if (symbol.x in minxsymbol..maxxsymbol && symbol.y in minysymbol..maxysymbol) {
            symbol.linkedParts.add(potentialEnginePart)
            if (symbol.linkedParts.size == 2) {
                sumOfAllRatio += symbol.linkedParts[0].value * symbol.linkedParts[1].value
            }
        }
    }
}

println(sumOfAllRatio)