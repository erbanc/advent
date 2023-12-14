import java.io.File
import java.io.InputStream
import kotlin.math.abs


class Galaxy(var positionX: Long, var positionY: Long, val number: Long) {

    fun getDistanceFrom(galaxyFarFarAway: Galaxy): Long {

        val xDiff = abs(positionX - galaxyFarFarAway.positionX)
        val yDiff = abs(positionY - galaxyFarFarAway.positionY)

        return xDiff + yDiff
    }
}

class Universe(val galaxies: List<Galaxy>,  val xSize: Long, val ySize: Long, private val age: Long = 1000000) {

    fun expand() {

        val emptyColumns = mutableSetOf<Long>()
        val emptyRows = mutableSetOf<Long>()
        for (x in 0..xSize) {
            if (galaxies.none { g -> g.positionX == x }) {
                emptyColumns.add(x)
            }
        }
        for (y in 0..ySize) {
            if (galaxies.none { g -> g.positionY == y }) {
                emptyRows.add(y)
            }
        }

        for (galaxy in galaxies) {
            val nbColumnsToExpandBefore = emptyColumns.filter { x -> galaxy.positionX > x }.size
            val nbRowsToExpandBefore = emptyRows.filter { y -> galaxy.positionY > y }.size

            val newX = galaxy.positionX + age * nbColumnsToExpandBefore - nbColumnsToExpandBefore
            val newY = galaxy.positionY + age * nbRowsToExpandBefore - nbRowsToExpandBefore
            galaxy.positionX = newX
            galaxy.positionY = newY
        }
    }
}


val inputStream: InputStream = File("input.txt").inputStream()
val image: List<List<Char>> = inputStream.bufferedReader().readLines().map{s -> s.toList()}.toList()

val universeX = image[0].size.toLong()
val universeY = image.size.toLong()
val galaxies = mutableListOf<Galaxy>()

var galaxyNumber: Long = 0
for (y in image.indices) {
    for (x in image[y].indices) {
        if ('#' == image[y][x]) {
            galaxyNumber++
            galaxies.add(Galaxy(x.toLong(), y.toLong(), galaxyNumber))
        }
    }
}

val universe = Universe(galaxies = galaxies, xSize = universeX, ySize = universeY)

universe.expand()

val galaxyPairs = mutableListOf<Pair<Galaxy, Galaxy>>()

for (i in 0..< universe.galaxies.size - 1) {
    for (j in i + 1..< universe.galaxies.size) {
        galaxyPairs.add(Pair(universe.galaxies[i], universe.galaxies[j]))
    }
}

println("There are ${galaxyPairs.size} pairs of galaxies")

val totalDistances = galaxyPairs.sumOf { pair -> pair.first.getDistanceFrom(pair.second) }

println("The sum of all distances for each pair is $totalDistances")