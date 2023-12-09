import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()

val histories: List<List<Int>> = inputStream.bufferedReader().readLines().map { it.split(" ").map { s -> s.toInt() } }.toList()

var sumHistoryValues = 0
for (history in histories) {
    val listDifferences = mutableListOf(history)
    var currentDifferences = history
    var isAllZeros = false
    while(!isAllZeros) {
        val differences = mutableListOf<Int>()
        for (i in 1..<currentDifferences.size) {
            differences.add(currentDifferences[i] - currentDifferences[i-1])
        }
        isAllZeros = differences.all { it == 0 }
        if (!isAllZeros) {
            listDifferences.add(differences)
        }
        currentDifferences = differences
    }
    val historyValue = getHistoryValueFromDifferencesList(listDifferences)
    sumHistoryValues += historyValue
}

println(sumHistoryValues)

fun getHistoryValueFromDifferencesList(listDifferences: MutableList<List<Int>>): Int {
    var historyValueTemp = 0
    listDifferences.reversed().forEach { differences ->
        historyValueTemp = differences.first() - historyValueTemp
    }
    return historyValueTemp
}
