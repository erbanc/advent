import kotlin.math.min

var walls = listOf(2, 0, 4, 2, 0, 3, 2, 5, 0, 3, 0, 2)
var maximums = mutableListOf<Int>()
walls.forEachIndexed { i, wall ->
    if (i == 0) {
        if (wall > walls[1]) maximums.add(wall) else maximums.add(0)
    } else if (i == walls.size - 1) {
        if (wall > walls[i - 1]) maximums.add(wall) else maximums.add(0)
    } else if (wall > walls[i - 1] && wall > walls[i + 1]) maximums.add(wall) else maximums.add(0)
}
println(maximums.toString())
var waterLevels = mutableListOf<Int>()
var leftWall = 0
var rightWall = 0
maximums.forEachIndexed { i, maximum ->
    if (maximum == 0) {
        rightWall = maximums.subList(i, maximums.size).max()
        waterLevels.add(min(leftWall, rightWall))
    } else {
        if (leftWall > maximum) {
            waterLevels.add(min(leftWall, rightWall))
        } else {
            waterLevels.add(maximum)
            leftWall = maximum
        }
    }
}
println(waterLevels.toString())
println(walls.toString())
var wellVolume = 0
walls.forEachIndexed { i, wall -> wellVolume += waterLevels[i] - wall }
println(wellVolume)
