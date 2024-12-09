import java.io.File
import java.io.InputStream

class CalibrationEquation(var testValue: Long, var calibrationNumbers: List<Long>) {

    fun isTrue(tempValue: Long = 0, deepIndex: Int = 0): Boolean {

        if (deepIndex == calibrationNumbers.lastIndex + 1) {
            return tempValue == testValue
        }

        // explore + option
        val isTruePlusOption = isTrue(tempValue + calibrationNumbers[deepIndex], deepIndex + 1)

        // explore * option
        val isTrueMultiplyOption = isTrue(tempValue * calibrationNumbers[deepIndex], deepIndex + 1)

        return isTruePlusOption || isTrueMultiplyOption
    }
}


val inputStream: InputStream = File("input.txt").inputStream()
var equations = ArrayList<CalibrationEquation>()
inputStream.bufferedReader().forEachLine {
    val testValue = it.split(":")[0].trim().toLong()
    val calibrationNumbers = it.split(":")[1].trim().split(" ").map { number -> number.toLong() }

    equations.add(CalibrationEquation(testValue, calibrationNumbers))
}

var totalMatchingTestValues:Long = 0

equations.forEach {
    if (it.isTrue()) {
        totalMatchingTestValues += it.testValue
    }
}

print(totalMatchingTestValues)
