import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()
inputStream.bufferedReader().forEachLine { lineList.add(it) }
var leftList = mutableListOf<String>()
var rightList = mutableListOf<String>()
lineList.forEach {
    val line = it.split("   ")
    leftList.add(line.first())
    rightList.add(line.last())
}

// Sorting the lists
leftList = leftList.stream().sorted().toList()
rightList = rightList.stream().sorted().toList()

// Calculate the distances
val distances = mutableListOf<Int>()
leftList.forEachIndexed { i, leftS ->
    val right = rightList[i].toInt()
    val left = leftS.toInt()
    val distance = abs(left - right)
    distances.add(distance)
}


var result = distances.sum().toString();

print(result)
