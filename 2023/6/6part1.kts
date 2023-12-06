import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()

class Race(
    val maxTime: Int,
    val record: Int,
    private var boatSpeed: Int = 0,
    private var seconds: Int = 0,
    var distance: Int = 0,
    var winningSecondsToHold: MutableList<Int> = mutableListOf()
) {

    fun holdAndRelease(seconds: Int) {
        this.boatSpeed += seconds
        this.seconds += seconds
        release()
    }

    private fun release() {
        while (seconds < maxTime) {
            distance += boatSpeed
            seconds++
        }
    }

    fun reset() {
        boatSpeed = 0
        seconds = 0
        distance = 0
    }
}

val races = mutableListOf<Race>()

inputStream.bufferedReader().forEachLine {
    races.add(Race(it.split(",")[0].toInt(), it.split(",")[1].toInt()))
}

var points: Int = 1

races.forEach { race ->
    for (secondsToHold in 1..race.maxTime) {
        race.holdAndRelease(secondsToHold)
        if (race.distance > race.record) {
            race.winningSecondsToHold.add(secondsToHold)
        }
        race.reset()
    }

    points *= race.winningSecondsToHold.size
}

println(points)