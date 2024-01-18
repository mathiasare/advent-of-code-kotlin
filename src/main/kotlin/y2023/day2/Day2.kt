package y2023.day2

import util.readFileLines

val colorPairRegex = "([0-9]+) ([a-z]+),?".toRegex()

fun main() {
    dayTwo()
}

fun dayTwo() {
    val lines = readFileLines("inputD2")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}

fun solvePartOne(lines: List<String>): Int {
    return lines
        .map(::readGame)
        .filter(::isValidGame)
        .sumOf { it.id }
}

fun solvePartTwo(lines: List<String>): Int {
    return lines
        .map(::readGame)
        .map(::findMinCubeSet)
        .map(::powerOfCubeSet)
        .sum()
}

fun findMinCubeSet(game: Game): CubeSet {
    return game.rounds.run {
        CubeSet(
            maxBy { it.red }.red,
            maxBy { it.blue }.blue,
            maxBy { it.green }.green
        )
    }
}

fun powerOfCubeSet(cubeSet: CubeSet) = cubeSet.red * cubeSet.green * cubeSet.blue

fun isValidGame(game: Game): Boolean {
    return !game.rounds.any {
        it.red > 12 || it.green > 13 || it.blue > 14
    }
}

fun readGame(line: String): Game {
    return line.split(":").run {
        val id = first().removePrefix("Game ").toInt()
        val roundsData = last().split(";")
        Game(id, roundsData.map(::readRound))
    }
}

fun readRound(roundsData: String): CubeSet {
    val round = CubeSet(0, 0, 0)
    for (matchResult in colorPairRegex.findAll(roundsData)) {
        val (count, color) = matchResult.destructured
        when (color) {
            "red" -> round.red = count.toInt()
            "blue" -> round.blue = count.toInt()
            "green" -> round.green = count.toInt()
        }
    }
    return round
}

data class Game(
    val id: Int,
    val rounds: List<CubeSet>
)

data class CubeSet(var red: Int, var blue: Int, var green: Int)
