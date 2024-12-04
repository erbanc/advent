import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
var puzzle = mutableListOf(mutableListOf<Char>())
inputStream.bufferedReader().forEachLine {
    if (puzzle[0].isEmpty()) {
        puzzle = mutableListOf(it.trim().toMutableList())
    } else {
        puzzle.add(it.trim().toMutableList())
    }
}

var numberOfXMAS = 0

for (x in puzzle.indices) {
    for (y in puzzle[0].indices) {
        if (puzzle[x][y] == 'A') {
            if (isXMAS(x, y, puzzle)) numberOfXMAS++
        }
    }
}

println(numberOfXMAS)

fun isXMAS(x: Int, y: Int, puzzle: MutableList<MutableList<Char>>): Boolean {

    if (x < 1 || y < 1 || x > puzzle.size - 2 || y > puzzle[0].size - 2) return false

    val topLeft = puzzle[x - 1][y - 1]
    val topRight = puzzle[x - 1][y + 1]
    val downLeft = puzzle[x + 1][y - 1]
    val downRight = puzzle[x + 1][y + 1]

    if ((topLeft == 'S' && downRight == 'M') ||  (topLeft == 'M' && downRight == 'S')) {
        if ((downLeft == 'S' && topRight == 'M') || (downLeft == 'M' && topRight == 'S')) {
            return true
        }
    }
    return false
}
