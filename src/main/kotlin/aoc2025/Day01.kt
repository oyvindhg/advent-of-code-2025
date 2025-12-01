package aoc2025

import kotlin.math.abs
import kotlin.time.measureTimedValue

object Day01 {
    private const val INPUT_FILENAME = "day01.txt"

    fun solve() {
        println("===Day 01===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val rotations = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(rotations) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(rotations) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<Int> {
        return input.map { line ->
            val rotationDirection = line[0]
            val rotationAmount = line.substring(1).toInt()
            when (rotationDirection) {
                'R' -> rotationAmount
                'L' -> -rotationAmount
                else -> error("Illegal direction: $rotationDirection")
            }
        }
    }

    internal fun solveFirst(rotations: List<Int>): Int {
        var password = 0
        var currentPosition = 50
        for (rotation in rotations) {
            currentPosition = (rotation + currentPosition) % 100
            if (currentPosition == 0) {
                password += 1
            }
        }
        return password
    }

    internal fun solveSecond(rotations: List<Int>): Int {
        var password = 0
        var currentPosition = 50
        for (rotation in rotations) {
            val nextPosition = currentPosition + rotation
            if ((nextPosition <= 0 && currentPosition > 0) || (nextPosition >= 0 && currentPosition < 0)) {
                password += 1
            }
            password += abs(nextPosition / 100)
            currentPosition = nextPosition % 100
        }
        return password
    }
}
