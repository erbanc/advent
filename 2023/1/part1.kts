import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()
inputStream.bufferedReader().forEachLine { lineList.add(it) }
val calibrations = mutableListOf<String>()
lineList.forEach {
    var lineCorrected = it

    lineCorrected = lineCorrected.replace("seven", "s7n")
    lineCorrected = lineCorrected.replace("nine", "n9n")
    lineCorrected = lineCorrected.replace("eight", "e8t")
    lineCorrected = lineCorrected.replace("two", "t2o")
    lineCorrected = lineCorrected.replace("one", "o1e")
    lineCorrected = lineCorrected.replace("three", "t3e")
    lineCorrected = lineCorrected.replace("four", "f4r")
    lineCorrected = lineCorrected.replace("five", "fe5")
    lineCorrected = lineCorrected.replace("six", "s6x")
    calibrations.add(lineCorrected) }
val calibrationsOnlyNumbers = mutableListOf<String>()
calibrations.forEach { calibration ->
    var numbers = ""
    val chars = calibration.toCharArray()
    chars.filter { it.isDigit() }.forEach { numbers += it }
    calibrationsOnlyNumbers.add(numbers)
}

var result = 0
calibrationsOnlyNumbers.forEach {
    val code = String(charArrayOf(it.first(),it.last())).toInt()
    result+=code
}

print(result)