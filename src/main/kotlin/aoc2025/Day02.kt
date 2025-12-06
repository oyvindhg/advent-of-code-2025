package aoc2025

import kotlin.time.measureTimedValue

object Day02 {
    private const val INPUT_FILENAME = "day02.txt"

    fun solve() {
        println("===Day 02===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val ranges = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(ranges) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(ranges) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<Pair<Long, Long>> {
        assert(input.size == 1) { "Input has ${input.size} lines, should have 1" }

        val ranges = input.first().split(',')

        return ranges.map { range ->
            val (start, end) = range.split('-')
            Pair(start.toLong(), end.toLong())
        }
    }

    private fun isInvalid(number: Long, allowAnyChunkLength: Boolean): Boolean {
        if (number < 10) {
            return false
        }
        val digits = number.toString()
        val minChunkLength = if (allowAnyChunkLength) {
            1
        } else {
            digits.length / 2
        }
        val maxChunkLength = digits.length / 2
        for (chunkLength in minChunkLength..maxChunkLength) {
            val chunks = digits.chunked(chunkLength)
            if (chunks.distinct().size == 1 && (allowAnyChunkLength || chunks.size == 2)) {
                return true
            }
        }
        return false
    }

    private fun sumInvalidIds(ranges: List<Pair<Long, Long>>, allowAnyChunkLength: Boolean): Long {
        var invalidIdSum: Long = 0
        for (range in ranges) {
            val (start, end) = range

            for (number in start..end) {
                if (isInvalid(number, allowAnyChunkLength)) {
                    invalidIdSum += number
                }
            }
        }
        return invalidIdSum
    }

    internal fun solveFirst(ranges: List<Pair<Long, Long>> ): Long {
        return sumInvalidIds(ranges, false)
    }

    internal fun solveSecond(ranges: List<Pair<Long, Long>> ): Long {
        return sumInvalidIds(ranges, true)
    }
}
