import _10part2.DirectionEnum.*
import java.io.File
import java.io.InputStream

fun findNbWallsEncounteredWhenFiringWestButALittleBitToBottom(x: Int, y: Int): Int {
    var nbToReturn = 0
    for (i in x..network[0].lastIndex) {
        if (path.any { t -> t.x == i && t.y == y }) {
            when(network[y][i]) {
                'F', '7', '|', 'S' -> nbToReturn++
            }
        }
    }
    return nbToReturn
}


val inputStream: InputStream = File("input.txt").inputStream()

class Pipe(val y: Int, val x: Int, val type:Char, var steps: Int? = null)

val network: MutableList<MutableList<Char>> = inputStream.bufferedReader().readLines().map{s -> s.toMutableList()}.toMutableList()

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
        'S' -> DOWN
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

val enclosedBits = mutableListOf<Pair<Int, Int>>()

// Check each outside row / column to find linked bits
for (i in network.indices) {
    chainTurnToOIfLinked(i, 0)
}
for (i in network.indices) {
    chainTurnToOIfLinked(i, network.size - 1)
}
for (i in network[0].indices) {
    chainTurnToOIfLinked(0, i)
}
for (i in network[0].indices) {
    chainTurnToOIfLinked(network[0].size - 1, i)
}

fun chainTurnToOIfLinked(x: Int, y: Int) {
    try {
        if (path.any { p -> p.x == x && p.y == y} || network[y][x] == 'O') {
            return // not outside
        }
    } catch (e: Exception) {
        return
    }

    network[y][x] = 'O'
    println("BZZZZ Turned ($x, $y) to O")

    if (x < network[0].size - 1) chainTurnToOIfLinked(x + 1, y)
    if (x > 0) chainTurnToOIfLinked(x - 1, y)
    if (y < network.size - 1) chainTurnToOIfLinked(x, y + 1)
    if (y > 0) chainTurnToOIfLinked(x, y - 1)
}

println("BZZZ Process OVEEEEER")

var nbEnclosedBits = 0
for (y in network.indices) {
    println()
    for (x in network[y].indices) {
        if (network[y][x] != 'O' && path.none { p -> p.x == x && p.y == y}) {
            val nbWallsEncounteredWhenFiringWestButALittleBitToBottom = findNbWallsEncounteredWhenFiringWestButALittleBitToBottom(x, y)
            if (nbWallsEncounteredWhenFiringWestButALittleBitToBottom % 2 == 0) {
                network[y][x] = '0'
            } else {
                nbEnclosedBits++
                network[y][x] = 'I'
            }
        }
        print("${network[y][x]}")
    }
    println()
}
println("THERE ARE $nbEnclosedBits ENCLOSED BITS. THAT'S A LOOOOT OF BITS")
