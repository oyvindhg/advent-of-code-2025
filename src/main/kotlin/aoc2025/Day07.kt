package aoc2025

import kotlin.time.measureTimedValue

object Day07 {
    private const val INPUT_FILENAME = "day07.txt"

    fun solve() {
        println("===Day 07===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val manifold = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(manifold) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(manifold) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<String> {
        return input
    }

    internal fun solveFirst(manifold: List<String>): Long {
        return runManifoldSimulation(manifold).splitCount
    }

    internal fun solveSecond(manifold: List<String>): Long {
        return runManifoldSimulation(manifold).timelines
    }

    private fun runManifoldSimulation(manifold: List<String>): ManifoldSimulationResult {
        var previousLine = manifold.first().map { letter ->
            when (letter) {
                'S' -> 1L
                '.' -> 0L
                else -> throw IllegalArgumentException("Invalid location marker: $letter")
            }
        }

        var splitCount = 0L
        manifold.drop(1).forEach { line ->
            val currentLine = MutableList(line.length) { 0L }
            line.forEachIndexed { index, letter ->
                when (letter) {
                    '^' -> {
                        if (previousLine[index] > 0) {
                            splitCount += 1
                            currentLine[index - 1] += previousLine[index]
                            currentLine[index + 1] += previousLine[index]
                        }
                    }

                    '.' -> currentLine[index] += previousLine[index]

                    else -> throw IllegalArgumentException("Invalid location marker: $letter")
                }
            }
            previousLine = currentLine
        }
        val timelines = previousLine.sum()
        return ManifoldSimulationResult(splitCount, timelines)
    }

    private data class ManifoldSimulationResult(val splitCount: Long, val timelines: Long)
}
