package y2023.day11

import util.readFileLines
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

fun main() {
    day11()
}

fun day11() {
    val lines = readFileLines("inputD11")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}

fun solvePartOne(lines: List<String>): Long {
    val galaxyMap = readGalaxyMap(lines)
    return findDistanceBetweenAllGalaxies(galaxyMap)
}

fun solvePartTwo(lines: List<String>): Long {
    val galaxyMap = readGalaxyMap(lines)
    return findDistanceBetweenAllGalaxies(galaxyMap, 6)
}

fun findDistanceBetweenAllGalaxies(galaxyMap: GalaxyMap, magnificationLvl: Int = 0): Long {
    var distanceSum = 0L
    val measuredGalaxies = mutableSetOf<Position>()

    for (from in galaxyMap.galaxyPos) {
        for (to in galaxyMap.galaxyPos) {
            if (measuredGalaxies.contains(to)) {
                continue
            }
            distanceSum += galaxyMap.distanceBetweenGalaxies(from, to, magnificationLvl)
        }
        measuredGalaxies.add(from)
    }
    return distanceSum
}

fun readGalaxyMap(lines: List<String>): GalaxyMap {
    val xGaps = mutableSetOf<Long>()
    val yGaps = mutableSetOf<Long>()
    val galaxies = mutableListOf<Position>()

    xGaps.addAll(0L..lines[0].length.toLong())

    for ((y, line) in lines.withIndex()) {
        val yLong = y.toLong()
        if (line.all { it == '.' }) {
            yGaps.add(yLong)
        }

        for (x in line.indices) {
            if (line[x] != '#') {
                continue
            }

            val xLong = x.toLong()
            if (xLong in xGaps) {
                xGaps.remove(xLong)
            }
            galaxies.add(Position(xLong, yLong))
        }
    }
    return GalaxyMap(galaxies, xGaps, yGaps)
}

class GalaxyMap(val galaxyPos: List<Position>, val xGaps: Set<Long>, val yGaps: Set<Long>) {

    fun distanceBetweenGalaxies(galaxy1: Position, galaxy2: Position, magnificationLvl: Int = 0): Long {
        var distance = manhattanDistance(galaxy1, galaxy2)
        val xGapCount =
            xGaps.asSequence().filter { it > min(galaxy1.x, galaxy2.x) && it < max(galaxy1.x, galaxy2.x) }.count()
        val yGapCount =
            yGaps.asSequence().filter { it > min(galaxy1.y, galaxy2.y) && it < max(galaxy1.y, galaxy2.y) }.count()

        val expansionMultiplier = 10.0.pow(magnificationLvl.toDouble()).toLong()

        distance += (xGapCount * expansionMultiplier)
        distance += (yGapCount * expansionMultiplier)

        if (expansionMultiplier > 1) {
            distance -= xGapCount
            distance -= yGapCount
        }
        return distance
    }

    private fun manhattanDistance(a: Position, b: Position) = abs(a.x - b.x) + abs(a.y - b.y)
}

data class Position(val x: Long, val y: Long)
