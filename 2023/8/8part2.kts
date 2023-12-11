import java.io.File
import java.io.InputStream

class CrossRoad(val id: String, val left: String, val right: String, var minStepsForZ: Int = 0)

val inputStream: InputStream = File("input.txt").inputStream()
val lines = inputStream.bufferedReader().readLines()
val instructions = lines[0].toCharArray()
val crossroads = mutableMapOf<String,CrossRoad>()
for (line in lines.subList(2, lines.size)) {
    val id = line.split("=")[0].trim()
    crossroads[id] = CrossRoad(
        id = id,
        left = line.split("=")[1].trim().split(",")[0].trim().removePrefix("("),
        right = line.split("=")[1].trim().split(",")[1].trim().removeSuffix(")")
    )
}
var steps:Long = 0
var currentCrossRoads = crossroads.filterKeys { k -> k.endsWith("A") }

var listMinSteps = mutableListOf<Long>()
currentCrossRoads.forEach { (_, v) ->
    var steps = 0
    var currentCrossRoad = v
    var i = 0
    while (!currentCrossRoad.id.endsWith("Z")) {
        val instruction = try {
            instructions[i]
        } catch (e: Exception) {
            i = 0
            instructions[i]
        }
        val nextCrossroadId = if (instruction == 'L') currentCrossRoad.left else currentCrossRoad.right
        val nextCrossRoad = crossroads[nextCrossroadId]!!
        currentCrossRoad = nextCrossRoad
        i++
        steps++
    }

    listMinSteps.add(steps.toLong())
}

println(findLCMOfList(listMinSteps))

fun findLCMOfList(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i])
    }
    return result
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}