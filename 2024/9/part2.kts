import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
var diskmap: MutableList<String> = mutableListOf()
var isFile = true
var fileId: Int = 0
inputStream.bufferedReader().readLine().toMutableList().forEach { char ->
    val value = char.toString().toInt()
    if (value != 0) {
        for (i in 1..value) {
            if (isFile) {
                diskmap += fileId.toString()
            } else {
                diskmap += "."
            }
        }
    }
    if (isFile) {
        fileId++
    }
    isFile = !isFile
}

while (diskmap.contains(".")) {
    val lastString = diskmap.last()

    if (lastString != ".") {
        val index = diskmap.indices.first { diskmap[it] == "." }

        diskmap[index] = diskmap.last()
    }

    diskmap.removeLast()
}

var index: Long = 0
val checkSum: Long = diskmap.sumOf {
    val value = it.toInt() * index
    index++
    value
}

println(checkSum)
