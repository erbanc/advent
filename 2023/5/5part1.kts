import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()

class Map(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long)

class Almanac(val source: String, val destination: String, val maps: List<Map>) {

    fun findDestination(value: Long): Long {

        val map = maps.find { value in it.sourceRangeStart..<it.sourceRangeStart + it.rangeLength } ?: return value
        return map.destinationRangeStart + (value - map.sourceRangeStart)
    }
}

var seeds: MutableList<Long> = mutableListOf()

val doc = mutableListOf<String>()
inputStream.bufferedReader().forEachLine {
    doc.add(it)
}

doc[0].split(":")[1].split(" ").filter { it.isNotBlank() }.forEach { seeds.add(it.toLong()) }

val seedToSoilMaps = mutableListOf<Map>()
for (i in 3..18) {
    val line = doc[i].split(" ")
    seedToSoilMaps.add(Map(destinationRangeStart = line[0].toLong(), sourceRangeStart = line[1].toLong(), rangeLength = line[2].toLong()))
}
val seedToSoil = Almanac(source = "seed", destination = "soil", maps = seedToSoilMaps)

val soilToFertilizerMaps = mutableListOf<Map>()
for (i in 21..38) {
    val line = doc[i].split(" ")
    soilToFertilizerMaps.add(Map(destinationRangeStart = line[0].toLong(), sourceRangeStart = line[1].toLong(), rangeLength = line[2].toLong()))
}
val soilToFertilizer = Almanac(source = "soil", destination = "fertilizer", maps = soilToFertilizerMaps
)

val fertilizerToWaterMaps = mutableListOf<Map>()
for (i in 41..80) {
    val line = doc[i].split(" ")
    fertilizerToWaterMaps.add(Map(destinationRangeStart = line[0].toLong(), sourceRangeStart = line[1].toLong(), rangeLength = line[2].toLong()))
}
val fertilizerToWater = Almanac(source = "fertilizer", destination = "water", maps = fertilizerToWaterMaps)

val waterToLightMaps = mutableListOf<Map>()
for (i in 83..98) {
    val line = doc[i].split(" ")
    waterToLightMaps.add(Map(destinationRangeStart = line[0].toLong(), sourceRangeStart = line[1].toLong(), rangeLength = line[2].toLong()))
}
val waterToLight = Almanac(source = "water", destination = "light", maps = waterToLightMaps)

val lightToTemperatureMaps = mutableListOf<Map>()
for (i in 101..140) {
    val line = doc[i].split(" ")
    lightToTemperatureMaps.add(Map(destinationRangeStart = line[0].toLong(), sourceRangeStart = line[1].toLong(), rangeLength = line[2].toLong()))
}
val lightToTemperature = Almanac(source = "light", destination = "temperature", maps = lightToTemperatureMaps)

val temperatureToHumidityMaps = mutableListOf<Map>()
for (i in 143..180) {
    val line = doc[i].split(" ")
    temperatureToHumidityMaps.add(Map(destinationRangeStart = line[0].toLong(), sourceRangeStart = line[1].toLong(), rangeLength = line[2].toLong()))
}
val temperatureToHumidity = Almanac(source = "temperature", destination = "humidity", maps = temperatureToHumidityMaps)

val humidityToLocationMaps = mutableListOf<Map>()
for (i in 183..218) {
    val line = doc[i].split(" ")
    humidityToLocationMaps.add(Map(destinationRangeStart = line[0].toLong(), sourceRangeStart = line[1].toLong(), rangeLength = line[2].toLong()))
}
val humidityToLocation = Almanac(source = "humidity", destination = "location", maps = humidityToLocationMaps)


var lowestLocation = Long.MAX_VALUE

seeds.forEach {
    val location =
    humidityToLocation.findDestination(
            temperatureToHumidity.findDestination(
                    lightToTemperature.findDestination(
                            waterToLight.findDestination(
                                    fertilizerToWater.findDestination(
                                            soilToFertilizer.findDestination(
                                                    seedToSoil.findDestination(it)
                                            )
                                    )
                            )
                    )
            )
    )

    if (location < lowestLocation) {
        lowestLocation = location
        println("lowest is now $lowestLocation !!")
    }
}
println(lowestLocation)