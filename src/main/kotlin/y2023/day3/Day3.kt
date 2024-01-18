package y2023.day3

import util.readFileLines

fun main() {
    dayThree()
}

fun dayThree() {
    val lines = readFileLines("inputD3")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}
