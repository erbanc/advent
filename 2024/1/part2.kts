import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()
inputStream.bufferedReader().forEachLine { lineList.add(it) }
var leftList = mutableListOf<Int>()
var rightList = mutableListOf<Int>()
lineList.forEach {
    val line = it.split("   ")
    leftList.add(line.first().toInt())
    rightList.add(line.last().toInt())
}

// Calculate the distances
var score = 0
leftList.forEach { left ->
    val numberOfOccurencesInRight = rightList.filter { it == left }.size
    score += numberOfOccurencesInRight * left
}

print(score)
