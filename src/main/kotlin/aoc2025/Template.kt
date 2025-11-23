package aoc2025

import kotlin.time.measureTimedValue

object Template {
    private const val INPUT_FILENAME = "template.txt"

    fun solve() {
        val input = INPUT_FILENAME.readPuzzle()
        val data = parse(input)

        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(data) }
        println("===Template===\n")
        println("---Task 1---")
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(data) }
        println("---Task 2---")
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<String> {
        return input
    }

    internal fun solveFirst(data: List<String>): Int {
        for (line in data) {
            println(line)
        }
        return 0
    }

    internal fun solveSecond(data: List<String>): Int {
        for (line in data) {
            println(line)
        }
        return 0
    }
}
