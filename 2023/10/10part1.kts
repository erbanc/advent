import _10part1.DirectionEnum.*
import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()

class Pipe(val y: Int, val x: Int, val type:Char, var steps: Int? = null)

val network: List<List<Char>> = inputStream.bufferedReader().readLines().map{s -> s.toList()}.toList()

enum class DirectionEnum {
    UP, DOWN, LEFT, RIGHT
}

var start: Pipe? = null
for (i in network.indices) {
    for (j in network[i].indices) {
        if ('S' == network[i][j]) {
            start = Pipe(i, j, 'S', 0)
            break
        }
    }
}
var steps = 0
var currentPipe = start
var path: MutableList<Pipe> = mutableListOf()
var direction: DirectionEnum = UP // J'ai triché LOL

while(currentPipe!!.type != 'S' || steps == 0) {

    val nextPipe = getNextPipe(currentPipe)
    path.add(nextPipe)
    currentPipe = nextPipe
}

println("Trouvé ! Il a fallu $steps étapes")
println("Le point le plus éloigné est donc de ${steps/2} étapes")

fun getNextPipe(currentPipe: Pipe?): Pipe {
    val x = currentPipe!!.x
    val y = currentPipe.y
    val newDirection: DirectionEnum = when(currentPipe.type) {
        'S' -> UP
        '|' -> if (direction == UP) UP else DOWN
        'J' -> if (direction == RIGHT) UP else LEFT
        '7' -> if (direction == RIGHT) DOWN else LEFT
        '-' -> if (direction == RIGHT) RIGHT else LEFT
        'L' -> if (direction == LEFT) UP else RIGHT
        'F' -> if (direction == LEFT) DOWN else RIGHT
        else -> throw Exception("Whoops")
    }
    steps++
    direction = newDirection
    return when(newDirection) {
        LEFT -> Pipe(y, x - 1, network[y][x - 1], steps)
        RIGHT -> Pipe(y, x + 1, network[y][x + 1], steps)
        UP -> Pipe(y - 1, x, network[y - 1][x], steps)
        DOWN -> Pipe(y + 1, x, network[y + 1][x], steps)
    }
}
