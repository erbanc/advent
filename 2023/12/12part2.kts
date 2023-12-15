import java.io.File
import java.io.InputStream

class ConditionRecord(var springRow: List<Boolean?>, var arrangement: List<Int>, var nbPossibilities: Long = 0)

val inputStream: InputStream = File("input.txt").inputStream()
val input: List<String> = inputStream.bufferedReader().readLines()

var conditionRecords = mutableListOf<ConditionRecord>()

input.forEach { condition ->

    var initialArrangement = condition.split(" ")[1]
    initialArrangement = "$initialArrangement,$initialArrangement,$initialArrangement,$initialArrangement,$initialArrangement"
    val arrangement = initialArrangement.split(",").map { it.toInt() }
    var initialSpringRow = condition.split(" ")[0]
    initialSpringRow = "$initialSpringRow?$initialSpringRow?$initialSpringRow?$initialSpringRow?$initialSpringRow"
    val springRow = initialSpringRow.toCharArray().map { c ->
        when (c) {
            '.' -> true
            '#' -> false
            '?' -> null
            else -> throw Exception()
        }
    }
    conditionRecords.add(ConditionRecord(springRow, arrangement))
}

var nbiterations: Long = 0

println(conditionRecords.sumOf { condition ->
    findPossibilities(condition.springRow, condition.arrangement, condition)
    println("Une de plus, ${condition.nbPossibilities} arrangements pour $nbiterations iterations")
    nbiterations = 0
    condition.nbPossibilities
})

fun findPossibilities(springRow: List<Boolean?>, arrangements: List<Int>, conditionRecord: ConditionRecord) {

    nbiterations++
    val currentArrangement = mutableListOf<Int>()
    var currentDamagedGroupSize = 0
    for (i in springRow.indices) {
        val j = currentArrangement.lastIndex
        if (currentArrangement.isNotEmpty() && currentArrangement[j] > arrangements[j]) {
            return
        }
        if (springRow[i] == true) {
            if (currentDamagedGroupSize > 0) {
                currentArrangement.add(currentDamagedGroupSize)
                currentDamagedGroupSize = 0
            }
        } else if (springRow[i] == false) {
            currentDamagedGroupSize++
            if (i == springRow.lastIndex) currentArrangement.add(currentDamagedGroupSize)
        } else if (springRow[i] == null) {
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

    if (springRow.none { s -> s == null }) {
        return
    }

    var indice = 0
    for (i in springRow.indices) {
        if (springRow[i] == null) {
            indice = i
            break
        }
    }
    val ifWorks = springRow.toMutableList()
    ifWorks[indice] = true
    val ifNotWorks = springRow.toMutableList()
    ifNotWorks[indice] = false

    findPossibilities(ifWorks, arrangements, conditionRecord)
    findPossibilities(ifNotWorks, arrangements, conditionRecord)
    return
}
