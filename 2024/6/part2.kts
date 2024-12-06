import java.io.File
import java.io.InputStream

class Guard(var facingDirection: DirectionEnum, var line: Int, var column: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Guard) return false

        return this.facingDirection == other.facingDirection && this.column == other.column && this.line == other.line
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

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

val startingGuardDirection = guard.facingDirection
val startingGuardLine = guard.line
val startingGuardColumn = guard.column
var positionOfNewObstacle = 0 to 0
var numberOfLoopingObstacles = 0
var possibilities = 0

for (ol in 0 until maze.size) {
    for (oc in 0 until maze[ol].size) {
        possibilities++
        val previousGuards: MutableList<Guard> = mutableListOf(Guard(startingGuardDirection, startingGuardLine, startingGuardColumn))
        var loopDetected = false
        guard.facingDirection = startingGuardDirection
        guard.column = startingGuardColumn
        guard.line= startingGuardLine
        if (maze[ol][oc] == '.') {
            maze[ol][oc] = '#'
        } else {
            continue
        }

        try {
            while (!loopDetected) {
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

                if (guard in previousGuards) {
                    numberOfLoopingObstacles++
                    println("loop detected when obstacle at $oc, $ol")
                    loopDetected = true
                }

                previousGuards.add(Guard(guard.facingDirection, guard.line, guard.column))
            }
        } catch (e: IndexOutOfBoundsException) {
            // ol, oc is not a blocking position
            val coucou = 0;
        }

        maze[ol][oc] = '.'
    }
}

println(numberOfLoopingObstacles)
println("end of processing, processed $possibilities possibilities")
