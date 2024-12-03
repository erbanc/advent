import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val reports = mutableListOf<String>()
inputStream.bufferedReader().forEachLine { reports.add(it) }

var numberSafeReports = 0
reports.forEach {
    val levels = it.split(" ").map{ it.toInt() }

    var containsDuplicates = levels.toSet().size != levels.size
    if (containsDuplicates) {
        return@forEach
    }

    var isAscending = levels.sorted() == levels
    var isDescending = levels.sortedDescending() == levels

    if (!isAscending && !isDescending) {
        return@forEach
    }

    for (i in 0..levels.size - 2) {
        if (abs(levels[i] - levels[i + 1]) > 3) {
            return@forEach
        }
    }

    numberSafeReports++
}

print(numberSafeReports)
