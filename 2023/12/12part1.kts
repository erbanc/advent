import java.io.File
import java.io.InputStream

data class Spring(var operational: Boolean?) {

    override fun toString() = this.operational.toString()
}

class ConditionRecord(var springRow: List<Spring>, var arrangement: List<Int>, var nbPossibilities: Long = 0)

val inputStream: InputStream = File("input.txt").inputStream()
val input: List<String> = inputStream.bufferedReader().readLines()

var conditionRecords = mutableListOf<ConditionRecord>()

input.forEach { condition ->

    val arrangement = condition.split(" ")[1].split(",").map { it.toInt() }
    val springRow = condition.split(" ")[0].toCharArray().map { c ->
        when (c) {
            '.' -> Spring(true)
            '#' -> Spring(false)
            '?' -> Spring(null)
            else -> throw Exception()
        }
    }
    conditionRecords.add(ConditionRecord(springRow, arrangement))
}

println(conditionRecords.sumOf { condition -> getPossibilities(condition.springRow)
    .map { possibility -> getContiguous(possibility)
    }.filter { it.toString() == condition.arrangement.toString() }.size.toLong()
})

fun getPossibilities(springRow: List<Spring>): MutableList<List<Spring>> {

    if (springRow.none { s -> s.operational == null }) {
        return mutableListOf(springRow)
    }

    var indice = 0
    for (i in springRow.indices) {
        if (springRow[i].operational == null) {
            indice = i
            break
        }
    }
    val ifWorks = springRow.mapTo(mutableListOf()) { it.copy() }
    ifWorks[indice].operational = true
    val ifNotWorks = springRow.mapTo(mutableListOf()) { it.copy() }
    ifNotWorks[indice].operational = false

    val allPossibilities = getPossibilities(ifWorks) + getPossibilities(ifNotWorks)

    return allPossibilities.toMutableList()
}

fun getContiguous(possibility: List<Spring>): List<Int> {
    val listGroups = mutableListOf<Int>()
    var defectivesInGroup = 0
    for (i in possibility.indices) {
        if (possibility[i].operational == true) {
            if (defectivesInGroup > 0) listGroups.add(defectivesInGroup)
            defectivesInGroup = 0
        } else {
            defectivesInGroup++
            if (i == possibility.lastIndex) listGroups.add(defectivesInGroup)
        }
    }
    return listGroups
}
