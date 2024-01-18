package y2023.day5

import util.readFileLines

fun main() {
    val lines = readFileLines("inputD5")
    println("Part 1: " + solvePartOne(lines))
}

fun solvePartOne(lines: List<String>): Long {
    val startingTable = readConversionTablesV1(lines)
    val seeds = readSeedsV1(lines)
    return seeds.map { getLocationValue(it, startingTable) }.min()
}

fun getLocationValue(seed: Long, table: ConversionTable): Long {
    if (!table.hasNext()) {
        return table.convert(seed)
    }
    return getLocationValue(table.convert(seed), table.next())
}

fun readSeedsV1(lines: List<String>) = lines.first()
    .removePrefix("seeds: ")
    .split(" ")
    .map { it.toLong() }

fun readConversionTablesV1(lines: List<String>): ConversionTable {
    val startTable = ConversionTable()
    var table = startTable
    var i = 0
    while (i < lines.size) {
        val mappings = mutableListOf<RangeMapping>()
        if (lines[i].contains("map")) {
            i++
            while (i < lines.size) {
                val line = lines[i]

                if (line.isBlank()) {
                    break
                }

                mappings.add(readRangeMappingFromLine(line))
                i++
            }
            table.rangeMappings = mappings
            table.next = ConversionTable()
            table = table.next()
        }
        i++
    }
    table.next = null
    return startTable
}

open class RangeMapping(val sourceRange: LongRange, val destinationRange: LongRange, val conversionRate: Long) {

    fun inSourceRange(n: Long) = n in sourceRange

    fun convert(n: Long) = n + this.conversionRate

    fun convertBack(n: Long) = n - this.conversionRate

    fun inDestinationRange(n: Long) = n in destinationRange
}

class ConversionTable(
    var name: String = "",
    var rangeMappings: List<RangeMapping> = mutableListOf(),
    var next: ConversionTable? = null
) {
    fun convert(n: Long): Long {
        for (rangeMapping in rangeMappings) {
            if (rangeMapping.inSourceRange(n)) {
                return rangeMapping.convert(n)
            }
        }
        return n
    }

    fun convertBack(n: Long): Long {
        for (rangeMapping in rangeMappings) {
            if (rangeMapping.inDestinationRange(n)) {
                return rangeMapping.convertBack(n)
            }
        }
        return n
    }

    fun hasNext() = next != null

    fun next() = next!!

    override fun toString(): String {
        return "Name: ${name}, Num of mappings ${rangeMappings.size}"
    }
}