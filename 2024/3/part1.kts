import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
var corruptedOutput = ""
inputStream.bufferedReader().forEachLine { corruptedOutput += it }

val regex = Regex("mul\\([0-9]{1,3},[0-9]{1,3}\\)")
val listMatching = regex.findAll(corruptedOutput)

var result = 0
var enabledMul = true

listMatching.forEach { match ->
    val numbers = match.value.removePrefix("mul(").removeSuffix(")").split(',')
    result += numbers[0].toInt() * numbers[1].toInt()
}

println(result)
