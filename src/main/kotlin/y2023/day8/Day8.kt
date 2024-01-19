package y2023.day8

import util.readFileLines
import kotlin.math.max

fun main() {
    day8()
}

fun day8() {
    val lines = readFileLines("inputD8")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}

fun solvePartOne(lines: List<String>): Int {
    val instructions = readInstructions(lines)
    val dirMap = readDirMap(lines)

    var key = "AAA"
    var iterator = instructions.iterator()
    var stepCount = 0

    while (key != "ZZZ") {
        if (!iterator.hasNext()) {
            iterator = instructions.iterator()
        }
        val move = iterator.next()
        key = dirMap.getMap(move)[key]!!
        stepCount++
    }
    return stepCount
}

fun solvePartTwo(lines: List<String>): Long {
    val instructions = readInstructions(lines)
    val dirMap = readDirMap(lines)
    val nodes = dirMap.left.keys.filter(::isStartNode)

    val counts = nodes.map {
        var iterator = instructions.iterator()
        var stepCount = 0L
        var node = it
        while (!isFinalNode(node)) {
            if (!iterator.hasNext()) {
                iterator = instructions.iterator()
            }
            val move = iterator.next()
            node = dirMap.getMap(move)[node]!!
            stepCount++
        }
        stepCount
    }
    return lcmOfLongs(counts)
}

fun isStartNode(key: String) = key.endsWith("A")
fun isFinalNode(key: String) = key.endsWith("Z")

/**
 * @see <a href="https://www.baeldung.com/kotlin/lcm">LCM</a>
 * */
fun lcm(a: Long, b: Long): Long {
    val larger = max(a, b)
    val maxLcm = a * b
    var lcm = larger
    while (lcm < maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun lcmOfLongs(numbers: List<Long>): Long {
    return numbers.subList(1, numbers.size)
        .fold(numbers[0]) { acc, el -> lcm(acc, el) }
}

fun readInstructions(lines: List<String>) = lines[0]

fun readDirMap(lines: List<String>): PathMapping {
    val leftMap = mutableMapOf<String, String>()
    val rightMap = mutableMapOf<String, String>()

    for (line in lines.subList(2, lines.size)) {
        val value = line.substring(0, 3)
        val left = line.substring(7, 10)
        val right = line.substring(12, 15)
        leftMap[value] = left
        rightMap[value] = right
    }
    return PathMapping(leftMap, rightMap)
}

data class PathMapping(val left: Map<String, String>, val right: Map<String, String>) {
    fun getMap(c: Char): Map<String, String> {
        if (c == 'L') {
            return left
        }
        return right
    }
}