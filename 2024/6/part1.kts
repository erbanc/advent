import java.io.File
import java.io.InputStream

class Guard(var facingDirection: DirectionEnum, var line: Int, var column: Int)

enum class DirectionEnum { WEST, EAST, NORTH, SOUTH }

val inputStream: InputStream = File("input.txt").inputStream()
var maze = mutableListOf(mutableListOf<Char>())
var guard = Guard(DirectionEnum.SOUTH, 0, 0)
inputStream.bufferedReader().forEachLine {
    if (maze[0].isEmpty()) {
        maze = mutableListOf(it.trim().toMutableList())
    } else {
        maze.add(it.trim().toMutableList())
    }
}

maze.forEachIndexed GuardSearch@{ l, line ->
    line.forEachIndexed { c, column ->
        run {
            when (column) {
                '^' -> {
                    guard = Guard(DirectionEnum.NORTH, l, c)
                    return@GuardSearch
                }

                '<' -> {
                    guard = Guard(DirectionEnum.WEST, l, c)
                    return@GuardSearch
                }

                '>' -> {
                    guard = Guard(DirectionEnum.EAST, l, c)
                    return@GuardSearch
                }

                'v' -> {
                    guard = Guard(DirectionEnum.SOUTH, l, c)
                    return@GuardSearch
                }
            }
        }
    }
}

var visitedPositions: MutableSet<Pair<Int, Int>> = mutableSetOf()

try {
    while (true) {
        visitedPositions += guard.line to guard.column
        val gl = guard.line
        val gc = guard.column

        when (guard.facingDirection) {
            DirectionEnum.SOUTH -> {
                val nextMaze = maze[gl + 1][gc]
                if (nextMaze == '#') {
                    guard.facingDirection = DirectionEnum.WEST
                } else {
                    guard.line++
                }
            }

            DirectionEnum.NORTH -> {
                val nextMaze = maze[gl - 1][gc]
                if (nextMaze == '#') {
                    guard.facingDirection = DirectionEnum.EAST
                } else {
                    guard.line--
                }
            }

            DirectionEnum.WEST -> {
                val nextMaze = maze[gl][gc - 1]
                if (nextMaze == '#') {
                    guard.facingDirection = DirectionEnum.NORTH
                } else {
                    guard.column--
                }
            }

            DirectionEnum.EAST -> {
                val nextMaze = maze[gl][gc + 1]
                if (nextMaze == '#') {
                    guard.facingDirection = DirectionEnum.SOUTH
                } else {
                    guard.column++
                }
            }
        }

        println("guard positions = ${guard.line}, ${guard.column}, visited ${visitedPositions.size} positions")
    }
} catch (e: IndexOutOfBoundsException) {
    println("Number of visited positions until out of maze : ${visitedPositions.size}")
}
