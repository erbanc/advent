import java.io.File
import java.io.InputStream
import kotlin.math.abs


class Galaxy(var positionX: Int, var positionY: Int, val number: Int) {

    fun getDistanceFrom(galaxyFarFarAway: Galaxy): Int {

        val xDiff = abs(positionX - galaxyFarFarAway.positionX)
        val yDiff = abs(positionY - galaxyFarFarAway.positionY)

        return xDiff + yDiff
    }
}

class Universe(val galaxies: List<Galaxy>,  val xSize: Int, val ySize: Int) {

    fun expand() {

        val emptyColumns = mutableSetOf<Int>()
        val emptyRows = mutableSetOf<Int>()
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
            val newX = galaxy.positionX + emptyColumns.filter { x -> galaxy.positionX > x }.size
            val newY = galaxy.positionY + emptyRows.filter { y -> galaxy.positionY > y }.size
            galaxy.positionX = newX
            galaxy.positionY = newY
        }
    }
}


val inputStream: InputStream = File("input.txt").inputStream()
val image: List<List<Char>> = inputStream.bufferedReader().readLines().map{s -> s.toList()}.toList()

val universeX = image[0].size
val universeY = image.size
val galaxies = mutableListOf<Galaxy>()

var galaxyNumber: Int = 0
for (y in image.indices) {
    for (x in image[y].indices) {
        if ('#' == image[y][x]) {
            galaxyNumber++
            galaxies.add(Galaxy(x, y, galaxyNumber))
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