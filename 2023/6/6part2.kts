import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input2.txt").inputStream()

class Race(
    val maxTime: Long,
    val record: Long,
    private var boatSpeed: Long = 0,
    private var seconds: Long = 0,
    var distance: Long = 0,
    var numberOfWinningSecondsToHold: Long = 0
) {

    fun holdAndRelease(seconds: Long) {
        this.boatSpeed += seconds
        this.seconds += seconds
        release()
    }

    private fun release() {

        distance = boatSpeed * (maxTime - seconds)
    }

    fun reset() {
        boatSpeed = 0
        seconds = 0
        distance = 0
    }
}

val races = mutableListOf<Race>()

inputStream.bufferedReader().forEachLine {
    races.add(Race(it.split(",")[0].toLong(), it.split(",")[1].toLong()))
}

races.forEach { race ->
    for (secondsToHold in 1..race.maxTime) {
        race.holdAndRelease(secondsToHold)
        if (race.distance > race.record) {
            race.numberOfWinningSecondsToHold++
        }
        race.reset()
    }
    println(race.numberOfWinningSecondsToHold)
}