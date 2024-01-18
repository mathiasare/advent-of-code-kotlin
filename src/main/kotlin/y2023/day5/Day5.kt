package y2023.day5

import util.readFileLines

fun main() {
    dayFive()
}

fun dayFive() {
    val lines = readFileLines("inputD5")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}
