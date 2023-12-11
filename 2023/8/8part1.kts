import java.io.File
import java.io.InputStream

class CrossRoad(val id: String, val left: String, val right: String)

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
var steps = 0
var currentCrossRoad = crossroads["AAA"]!!
var i = 0
while (currentCrossRoad.id != "ZZZ") {
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

println(steps)
