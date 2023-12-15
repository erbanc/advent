import java.io.File
import java.io.InputStream

class ConditionRecord(var springRow: String, var arrangement: List<Int>, var nbPossibilities: Long = 0)

val inputStream: InputStream = File("input.txt").inputStream()
val input: List<String> = inputStream.bufferedReader().readLines()

var conditionRecords = mutableListOf<ConditionRecord>()

input.forEach { condition ->

    var initialArrangement = condition.split(" ")[1]
    initialArrangement = "$initialArrangement,$initialArrangement,$initialArrangement,$initialArrangement,$initialArrangement"
    val arrangement = initialArrangement.split(",").map { it.toInt() }
    var initialSpringRow = condition.split(" ")[0]
    initialSpringRow = "$initialSpringRow?$initialSpringRow?$initialSpringRow?$initialSpringRow?$initialSpringRow"
    conditionRecords.add(ConditionRecord(initialSpringRow, arrangement))
}

var nbiterations: Long = 0

println(conditionRecords.sumOf { condition ->
    findPossibilities(condition.springRow, condition.arrangement, condition)
    println("Une de plus, ${condition.nbPossibilities} arrangements pour $nbiterations iterations")
    nbiterations = 0
    condition.nbPossibilities
})

fun findPossibilities(springRow: String, arrangements: List<Int>, conditionRecord: ConditionRecord) {

    nbiterations++
    val charArray = springRow.toCharArray()
    val currentArrangement = mutableListOf<Int>()
    var currentDamagedGroupSize = 0
    for (i in charArray.indices) {
        val j = currentArrangement.lastIndex
        if (currentArrangement.isNotEmpty() && currentArrangement[j] > arrangements[j]) {
            return
        }
        if (charArray[i] == '.') {
            if (currentDamagedGroupSize > 0) {
                currentArrangement.add(currentDamagedGroupSize)
                currentDamagedGroupSize = 0
            }
        } else if (charArray[i] == '#') {
            currentDamagedGroupSize++
            if (i == springRow.lastIndex) currentArrangement.add(currentDamagedGroupSize)
        } else if (charArray[i] == '?') {
            currentArrangement.add(currentDamagedGroupSize)
            break
        }
    }
    if (currentArrangement.size > arrangements.size) {
        return
    }

    for (i in currentArrangement.indices) {
        if (i < currentArrangement.lastIndex && currentArrangement[i] != arrangements[i]) {
            return
        }
    }
    if (currentArrangement == arrangements) {
        conditionRecord.nbPossibilities++
        return
    }

    if (!springRow.contains('?')) {
        return
    }

    findPossibilities(springRow.replaceFirst("?", "."), arrangements, conditionRecord)
    findPossibilities(springRow.replaceFirst("?", "#"), arrangements, conditionRecord)
    return
}
