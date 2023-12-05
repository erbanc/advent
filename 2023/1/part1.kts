import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
val lineList = mutableListOf<String>()
inputStream.bufferedReader().forEachLine { lineList.add(it) }
val calibrations = mutableListOf<String>()
lineList.forEach {
    calibrations.add(it) }
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