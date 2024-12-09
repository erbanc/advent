import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
var grid = mutableListOf(mutableListOf<Char>())
inputStream.bufferedReader().forEachLine {
    if (grid[0].isEmpty()) {
        grid = mutableListOf(it.trim().toMutableList())
    } else {
        grid.add(it.trim().toMutableList())
    }
}

var placementOfAntiNodes = mutableSetOf<Pair<Int, Int>>()

var allMatchingAntennas = mutableMapOf<Char, Set<Pair<Int, Int>>>()

for (line in grid.indices) {
    for (column in grid[0].indices) {
        val frequency = grid[line][column]
        if (frequency != '.') {
            val listPositionsForDetectedFrequency = allMatchingAntennas[frequency] ?: setOf()
            allMatchingAntennas[frequency] = listPositionsForDetectedFrequency + Pair(line, column)
        }
    }
}

for (antennaPositions in allMatchingAntennas) {
    val frequency = antennaPositions.key
    println("Analyse des ${antennaPositions.value.size} positions détéctées pour la fréquence $frequency..")
    antennaPositions.value.forEachIndexed { index, position ->
        for (i in index + 1..antennaPositions.value.indices.last) {
            val otherPosition = antennaPositions.value.elementAt(i)
            val lineFirstAntiNode = 2 * position.first - otherPosition.first
            val columnFirstAntiNode = 2 * position.second - otherPosition.second
            if (lineFirstAntiNode in 0..grid.lastIndex && columnFirstAntiNode in 0..grid[0].lastIndex) {
                placementOfAntiNodes.add(Pair(lineFirstAntiNode, columnFirstAntiNode))
                println("Antinode alpha trouvé en ($lineFirstAntiNode, $columnFirstAntiNode) pour la fréquence $frequency entre les positions (${position.first}, ${position.second}) et (${otherPosition.first}, ${otherPosition.second})")
            }

            val lineSecondAntiNode = 2 * otherPosition.first - position.first
            val columnSecondAntiNode = 2 * otherPosition.second - position.second
            if (lineSecondAntiNode in 0..grid.lastIndex && columnSecondAntiNode in 0..grid[0].lastIndex) {
                placementOfAntiNodes.add(Pair(lineSecondAntiNode, columnSecondAntiNode))
                println("Antinode beta trouvé en ($lineSecondAntiNode, $columnSecondAntiNode) pour la fréquence $frequency entre les positions (${position.first}, ${position.second}) et (${otherPosition.first}, ${otherPosition.second})")
            }
        }
    }
}

println("Nombre total d'Antinodes : ${placementOfAntiNodes.size}")
