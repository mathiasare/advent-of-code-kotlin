package y2023.day3

import util.readFileLines


fun main() {
    val lines = readFileLines("inputD3")
    println("Part 1: " + solvePartOne(lines))
}

fun solvePartOne(lines: List<String>): Int {
    var sumVal = 0
    var prevLine = ""

    for ((lineIdx, line) in lines.withIndex()) {
        var foundAdj = false
        var intBuffer = ""
        val nextIdx = lineIdx + 1
        val nextLine = if (nextIdx < lines.size) lines[nextIdx] else ""

        for ((charIdx, c) in line.withIndex()) {
            if (c.isDigit() && !foundAdj) {
                val matrix = listOf(prevLine, line, nextLine)
                foundAdj = hasAdjacentSymbol(charIdx, matrix)
            }

            if (c.isDigit()) {
                intBuffer += c
                continue
            }

            if (intBuffer.isNotBlank() && foundAdj) {
                sumVal += intBuffer.toInt()
            }

            foundAdj = false
            intBuffer = ""
        }
        if (intBuffer.isNotBlank() && foundAdj) {
            sumVal += intBuffer.toInt()
        }

        prevLine = line
    }
    return sumVal
}

fun isSymbol(c: Char): Boolean {
    return !c.isDigit() && c != '.'
}

fun hasAdjacentSymbol(idx: Int, lines: List<String>): Boolean {
    for (line in lines) {

        if (line.isBlank()) {
            continue
        }

        for (i in -1..1) {
            val cur = idx + i
            if (cur >= 0 && cur < line.length && isSymbol(line[cur])) {
                return true
            }
        }
    }
    return false
}
