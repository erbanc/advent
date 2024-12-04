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
        if (puzzle[x][y] == 'X') {
            numberOfXMAS += countXMASFrom(x, y, puzzle)
        }
    }
}

println(numberOfXMAS)

fun countXMASFrom(x: Int, y: Int, puzzle: MutableList<MutableList<Char>>): Int {

    var numberXMASFromPosition = 0

    // test left
    if (y >= 3 && puzzle[x][y-1] == 'M' && puzzle[x][y-2] == 'A' && puzzle[x][y-3] == 'S') {
        println("XMAS found to the left at ($x,$y)")
        numberXMASFromPosition++
    }

    // test right
    if ((y <= puzzle[0].size - 4) && puzzle[x][y+1] == 'M' && puzzle[x][y+2] == 'A' && puzzle[x][y+3] == 'S') {
        println("XMAS found to the right at ($x,$y)")
        numberXMASFromPosition++
    }

    // test up
    if (x >= 3 && puzzle[x-1][y] == 'M' && puzzle[x-2][y] == 'A' && puzzle[x-3][y] == 'S') {
        println("XMAS found to the up at ($x,$y)")
        numberXMASFromPosition++
    }

    // test down
    if ((x <= puzzle.size - 4) && puzzle[x+1][y] == 'M' && puzzle[x+2][y] == 'A' && puzzle[x+3][y] == 'S') {
        println("XMAS found to the down at ($x,$y)")
        numberXMASFromPosition++
    }

    // test up-right
    if (x >= 3 && y <= (puzzle[0].size - 4) && puzzle[x-1][y+1] == 'M' && puzzle[x-2][y+2] == 'A' && puzzle[x-3][y+3] == 'S') {
        println("XMAS found to the upright at ($x,$y)")
        numberXMASFromPosition++
    }

    // test up-left
    if (x >= 3 && y >= 3 && puzzle[x-1][y-1] == 'M' && puzzle[x-2][y-2] == 'A' && puzzle[x-3][y-3] == 'S') {
        println("XMAS found to the upleft at ($x,$y)")
        numberXMASFromPosition++
    }

    // test down-right
    if (x <= (puzzle.size - 4) && y <= (puzzle[0].size - 4) && puzzle[x+1][y+1] == 'M' && puzzle[x+2][y+2] == 'A' && puzzle[x+3][y+3] == 'S') {
        println("XMAS found to the downright at ($x,$y)")
        numberXMASFromPosition++
    }

    // test down-left
    if (x <= (puzzle.size - 4) && y >= 3 && puzzle[x+1][y-1] == 'M' && puzzle[x+2][y-2] == 'A' && puzzle[x+3][y-3] == 'S') {
        println("XMAS found to the downleft at ($x,$y)")
        numberXMASFromPosition++
    }

    return numberXMASFromPosition
}
