package y2023.day5

import util.readFileLines

fun main() {
    val lines = readFileLines("inputD5")
    println("Part 2: " + solvePartTwo(lines))
}

fun solvePartTwo(lines: List<String>): Long {
    val seedRanges = readSeedsV2(lines)
    val startingTable = readConversionTablesV2(lines)
    for (i in 0..Long.MAX_VALUE) {
        val seedVal = getSeedValue(i, startingTable)
        for (seedRange in seedRanges) {
            if (seedVal in seedRange) {
                return i
            }
        }
    }
    return -1L
}

fun getSeedValue(location: Long, table: ConversionTable): Long {
    if (!table.hasNext()) {
        return table.convertBack(location)
    }
    return getSeedValue(table.convertBack(location), table.next())
}

fun readSeedsV2(lines: List<String>): List<LongRange> {
    val seedRanges = mutableListOf<LongRange>()
    val nums = lines.first()
        .removePrefix("seeds: ")
        .split(" ")
        .map { it.toLong() }

    val iter = nums.iterator()

    while (true) {
        val start = iter.next()
        val range = start..<start + iter.next()
        seedRanges.add(range)

        if (!iter.hasNext()) {
            break
        }
    }
    return seedRanges
}

fun readConversionTablesV2(lines: List<String>): ConversionTable {
    val tables: List<ConversionTable> = mutableListOf()
    var table = ConversionTable()
    var i = 0
    while (i < lines.size) {
        val mappings = mutableListOf<RangeMapping>()
        if (lines[i].contains("map")) {
            table.name = lines[i].removeSuffix(" map:")
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
            tables.addFirst(table)
            table = ConversionTable()
        }
        i++
    }

    val startTable = tables[0]
    var prevTable = startTable
    for (j in 1..<tables.size) {
        prevTable.next = tables[j]
        prevTable = tables[j]
    }

    return startTable
}

fun readRangeMappingFromLine(line: String): RangeMapping {
    val (dest, source, size) = line.split(" ")
        .map { it.toLong() }
        .run {
            Triple(this[0], this[1], this[2])
        }
    val sourceRange = source..<(source + size)
    val destinationRange = dest..<(dest + size)
    val conversionRate = dest - source
    return RangeMapping(sourceRange, destinationRange, conversionRate)
}