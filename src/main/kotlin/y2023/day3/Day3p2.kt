package y2023.day3

import util.readFileLines

fun main() {
    val lines = readFileLines("inputD3")
    println("Part 2: " + solvePartTwo(lines))
}

fun solvePartTwo(lines: List<String>): Int {
    return findSumOfGearNumberProducts(findGearNumbers(lines))
}

fun findSumOfGearNumberProducts(gearMap: Map<Pair<Int, Int>, List<Int>>): Int {
    return gearMap
        .filter { it.value.size == 2 }
        .map { it.value.first() * it.value.last() }
        .sum()
}

fun findGearNumbers(lines: List<String>): Map<Pair<Int, Int>, List<Int>> {
    val gearMap = mutableMapOf<Pair<Int, Int>, List<Int>>()
    var prevLine = ""

    for ((lineIdx, line) in lines.withIndex()) {
        var foundGear: Pair<Int, Int>? = null
        var intBuffer = ""
        val nextIdx = lineIdx + 1
        val nextLine = if (nextIdx < lines.size) lines[nextIdx] else ""
        val matrix = listOf(prevLine, line, nextLine)

        for ((charIdx, c) in line.withIndex()) {

            if (c.isDigit() && foundGear == null) {
                foundGear = findAdjacentGear(charIdx, lineIdx, matrix)
            }

            if (c.isDigit()) {
                intBuffer += c
                continue
            }

            if (intBuffer.isNotBlank()) {
                foundGear?.let {
                    gearMap.putIfAbsent(it, mutableListOf())
                    gearMap[it]?.apply { addLast(intBuffer.toInt()) }
                }
                intBuffer = ""
            }
            foundGear = null
        }

        if (intBuffer.isNotBlank()) {
            foundGear?.let {
                gearMap.putIfAbsent(it, mutableListOf())
                gearMap[it]?.apply { addLast(intBuffer.toInt()) }
            }
        }

        prevLine = line
    }
    return gearMap
}

fun findAdjacentGear(charIdx: Int, lineIdx: Int, lines: List<String>): Pair<Int, Int>? {
    var y = lineIdx - 1
    for (line in lines) {
        if (line.isNotBlank()) {
            for (i in -1..1) {
                val x = charIdx + i
                if (x >= 0 && x < line.length && isGear(line[x])) {
                    return Pair(x, y)
                }
            }
        }
        y++
    }
    return null
}

fun isGear(c: Char): Boolean {
    return c == '*'
}