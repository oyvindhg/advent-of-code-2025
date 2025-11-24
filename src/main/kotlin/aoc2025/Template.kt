package aoc2025

import kotlin.time.measureTimedValue

object Template {
    private const val INPUT_FILENAME = "template.txt"

    fun solve() {
        println("===Template===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val data = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(data) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(data) }
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
