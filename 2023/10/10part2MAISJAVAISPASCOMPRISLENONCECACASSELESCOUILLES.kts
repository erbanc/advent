import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()

class Pipe(val y: Int, val x: Int, val type: Char, var steps: Int? = null)

val network: MutableList<MutableList<Char>> =
    inputStream.bufferedReader().readLines().map { s -> s.toMutableList() }.toMutableList()

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
    if (network[y][x] != '.') {
        return // not outside
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
for (row in network) {
    println()
    for (char in row) {
        print("$char")
        if ('.' == char) {
            nbEnclosedBits++
        }
    }
    println()
}
println("THERE ARE $nbEnclosedBits ENCLOSED BITS. THAT'S A LOOOOT OF BITS")
