package y2023.day5

import util.readFileLines

fun main() {
    day5()
}

fun day5() {
    val lines = readFileLines("inputD5")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}
