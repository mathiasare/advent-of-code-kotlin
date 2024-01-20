package y2023.day10

import util.readFileLines
import kotlin.math.abs
import kotlin.math.ceil

fun main() {
    day10()
}

fun day10() {
    val lines = readFileLines("inputD10")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}

fun solvePartOne(lines: List<String>): Int {
    val maze = readMaze(lines)
    val lenOfCycle = dfs(maze).path.size
    return ceil(lenOfCycle.toDouble() / 2).toInt()
}

fun solvePartTwo(lines: List<String>): Int {
    val maze = readMaze(lines)
    val path = dfs(maze).path
    return findAreaWithPick(path)
}

fun readMaze(lines: List<String>): Maze {
    var startNode: PipeNode? = null
    val matrix = mutableListOf<CharArray>()
    for ((idx, line) in lines.withIndex()) {
        if (line.contains('S')) {
            startNode = PipeNode('S', Coord(line.indexOf('S'), idx), 0)
        }
        matrix.add(line.toCharArray())
    }
    return Maze(matrix.toTypedArray(), startNode!!)
}

fun dfs(maze: Maze): PipeNode {
    val stack = mutableListOf<PipeNode>()
    val startNode = maze.startNode
    stack.addAll(findChildrenForStartNode(startNode, maze))

    while (stack.isNotEmpty()) {
        val curNode = stack.removeLast()

        if (curNode.coord == startNode.coord) {
            return curNode
        }

        stack.addAll(findChildren(curNode, maze))
    }
    throw IllegalStateException()
}

fun findChildren(curNode: PipeNode, maze: Maze): List<PipeNode> {
    val action = getTransitionFunctionFromSymbol(curNode.symbol)
    val parentCoord = curNode.path.last()
    val newPath = curNode.path.plus(curNode.coord).toMutableList()
    return action.invoke(curNode.coord)
        .filter { maze.isCoordInBounds(it) && it != parentCoord }
        .map { PipeNode(maze.getSymbolInCoord(it), it, curNode.orderNum + 1, newPath) }
}

fun getTransitionFunctionFromSymbol(c: Char): (Coord) -> List<Coord> {
    return when (c) {
        '-' -> { (x, y) -> listOf(Coord(x - 1, y), Coord(x + 1, y)) }
        '|' -> { (x, y) -> listOf(Coord(x, y - 1), Coord(x, y + 1)) }
        'F' -> { (x, y) -> listOf(Coord(x + 1, y), Coord(x, y + 1)) }
        'L' -> { (x, y) -> listOf(Coord(x + 1, y), Coord(x, y - 1)) }
        'J' -> { (x, y) -> listOf(Coord(x - 1, y), Coord(x, y - 1)) }
        '7' -> { (x, y) -> listOf(Coord(x - 1, y), Coord(x, y + 1)) }
        '.' -> { (_, _) -> emptyList() }
        else -> {
            throw IllegalStateException()
        }
    }
}

fun findChildrenForStartNode(startNode: PipeNode, maze: Maze): List<PipeNode> {
    if (startNode.symbol != 'S') {
        throw IllegalStateException()
    }

    val start = startNode.coord
    val childCoords = mutableListOf<Coord>()

    for (i in listOf(-1, 1)) {
        childCoords.add(Coord(start.x + i, start.y))
        childCoords.add(Coord(start.x, start.y + 1))
    }
    return childCoords
        .filter { maze.isCoordInBounds(it) && it != startNode.coord }
        .map { PipeNode(maze.getSymbolInCoord(it), it, startNode.orderNum + 1, path = mutableListOf(startNode.coord)) }
        .filter { isNodeReachableFromNode(it, startNode) }
}

fun isNodeReachableFromNode(from: PipeNode, to: PipeNode): Boolean {
    val reachableCoords = getTransitionFunctionFromSymbol(from.symbol).invoke(from.coord)
    for (coord in reachableCoords) {
        if (coord == to.coord) {
            return true
        }
    }
    return false
}

/**
 * Naive solution for Part 2 with ray tracing from each point
 * along the x-axis
 */
fun findAreaWithRayTracing(loop: List<Coord>, maze: Maze): Int {
    val groupedY = loop.groupBy { it.y }
    var area = 0

    for (y in groupedY.keys) {
        val loopPoints = groupedY[y]!!.map { it.x }.sorted()

        val start = loopPoints.first() + 1
        val end = loopPoints.last() - 1
        for (x in start..end) {
            if (x in loopPoints) {
                continue
            }

            val encounteredPoints = loopPoints.asSequence()
                .filter { it > x }
                // Filter out wall types that the ray would miss
                .filter { maze.getSymbolInCoord(Coord(it, y)) !in listOf('-', 'F', '7') }
                .count()

            if (encounteredPoints % 2 != 0) {
                area++
            }
        }
    }
    return area
}

/**
 * According to Pick's theorem:
 * num_inner_points = 1 + total_area - num_outer_points
 * According to the puzzle, the number of inner points should be the correct answer.
 */
fun findAreaWithPick(boundary: List<Coord>): Int {
    val totalArea = shoelaceArea(boundary)
    val numOfOuterPoints = boundary.size

    return 1 + totalArea - (numOfOuterPoints.toDouble() / 2).toInt()
}

/**
 * Finds the total area of polygon with boundary points included
 * using the shoelace algorithm
 */
fun shoelaceArea(boundary: List<Coord>): Int {
    // Ensure the loop is closed
    if (boundary.first() != boundary.last()) {
        boundary.addLast(boundary.first())
    }

    var area = 0.0

    for (i in 0 until boundary.size - 1) {
        val (x1, y1) = boundary[i]
        val (x2, y2) = boundary[i + 1]
        area += (x1 * y2 - x2 * y1)
    }

    return ceil(abs(area) / 2).toInt()
}

data class PipeNode(
    val symbol: Char,
    val coord: Coord,
    val orderNum: Int,
    var path: MutableList<Coord> = mutableListOf()
)

class Maze(private val matrix: Array<CharArray>, val startNode: PipeNode) {

    fun isCoordInBounds(coord: Coord): Boolean {
        return coord.y in matrix.indices && coord.x in matrix[0].indices
    }

    fun getSymbolInCoord(coord: Coord): Char {
        return matrix[coord.y][coord.x]
    }
}

data class Coord(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        other?.let {
            if (it is Coord) {
                return it.x == this.x && it.y == this.y
            }
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}


