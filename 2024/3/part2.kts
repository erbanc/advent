import java.io.File
import java.io.InputStream
import kotlin.math.abs

val inputStream: InputStream = File("input.txt").inputStream()
val reports = mutableListOf<String>()
inputStream.bufferedReader().forEachLine { reports.add(it) }

var numberSafeReports = 0
reports.forEach {
    val levels = it.split(" ").map { it.toInt() }
    val isSafeAsIs = isSafe(levels)
    if (isSafeAsIs) {
        numberSafeReports++
        return@forEach
    }

    for (i in levels.indices) {
        val levelsMinusOne = levels.toMutableList()
        levelsMinusOne.removeAt(i)
        val isSafeWithoutIndexI = isSafe(levelsMinusOne)

        if (isSafeWithoutIndexI) {
            numberSafeReports++
            return@forEach
        }
    }
}

print(numberSafeReports)

fun isSafe(levels: List<Int>): Boolean {


    val containsDuplicates = levels.toSet().size != levels.size
    if (containsDuplicates) {
        return false
    }

    val isAscending = levels.sorted() == levels
    val isDescending = levels.sortedDescending() == levels

    if (!isAscending && !isDescending) {
        return false
    }

    for (i in 0..levels.size - 2) {
        if (abs(levels[i] - levels[i + 1]) > 3) {
            return false
        }
    }

    return true
}
