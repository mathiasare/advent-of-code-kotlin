package y2023.day6

import util.readFileLines
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    daySix()
}

fun daySix() {
    val lines = readFileLines("inputD6")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}

fun solvePartOne(lines: List<String>): Long {
    val inputPairs = readTimeAndDistancePairs(lines)
    return findProductOfWinningCombinations(inputPairs)
}

fun solvePartTwo(lines: List<String>): Long {
    val (time, distance) = readTimeAndDistanceV2(lines)
    return countNumberOfWinningCombinations(time, distance)
}

fun readTimeAndDistanceV2(lines: List<String>): Pair<Long, Long> {
    val time = lines[0].removePrefix("Time:").replace(" ", "").toLong()
    val distance = lines[1].removePrefix("Distance:").replace(" ", "").toLong()

    return Pair(time, distance)
}

fun findProductOfWinningCombinations(inputPairs: List<Pair<Long, Long>>) = inputPairs
    .map { (time, distance) -> countNumberOfWinningCombinations(time, distance) }
    .reduce { acc, i -> acc * i }

fun readTimeAndDistancePairs(lines: List<String>): List<Pair<Long, Long>> {
    val times = lines[0].removePrefix("Time:").trim().split(" ").filter { it.isNotBlank() }.map { it.toLong() }
    val distances = lines[1].removePrefix("Distance:").trim().split(" ").filter { it.isNotBlank() }.map { it.toLong() }

    return times.zip(distances)
}

fun countNumberOfWinningCombinations(time: Long, distanceToBeat: Long): Long {
    var x = distanceToSpeedFormula(time, distanceToBeat)
    var y = time - x
    if (x * y == distanceToBeat) {
        x++
        y--
    }

    val res = y - x + 1
    return res
}

fun distanceToSpeedFormula(time: Long, distance: Long): Long {
    return ceil((time - sqrt((time.toDouble().pow(2.0) - 4 * distance))).div(2))
        .toLong()
}