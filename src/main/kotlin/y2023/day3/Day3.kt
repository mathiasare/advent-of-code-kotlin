package y2023.day3

import util.readFileLines

fun main() {
    day3()
}

fun day3() {
    val lines = readFileLines("inputD3")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}
