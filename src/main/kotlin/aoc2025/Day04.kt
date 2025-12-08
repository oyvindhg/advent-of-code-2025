package aoc2025

import kotlin.time.measureTimedValue

object Day04 {
    private const val INPUT_FILENAME = "day04.txt"

    fun solve() {
        println("===Day 04===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val diagram = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(diagram) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(diagram) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<String> {
        return input
    }

    private fun countAccessiblePaperRolls(diagram: List<String>, canRemove: Boolean): Int {
        val accessiblePaperRollMaxNeighbors = 4
        val paperRolls = mutableSetOf<Pair<Int, Int>>()
        diagram.forEachIndexed { row, line ->
            line.forEachIndexed { col, value ->
                if (value == '@') {
                    paperRolls.add(row to col)
                }
            }
        }

        var accessiblePaperRollCount = 0
        var continueRemovingPaperRolls = true
        while (continueRemovingPaperRolls) {
            continueRemovingPaperRolls = false
            val removablePaperRolls = mutableSetOf<Pair<Int, Int>>()
            paperRolls.forEach { (row, col) ->
                var neighborPaperRollCount = 0
                for (rowDiff in -1..1) {
                    for (colDiff in -1..1) {
                        if (rowDiff != 0 || colDiff != 0) {
                            if (paperRolls.contains(row + rowDiff to col + colDiff)) {
                                neighborPaperRollCount += 1
                            }
                        }
                    }
                }
                if (neighborPaperRollCount < accessiblePaperRollMaxNeighbors) {
                    accessiblePaperRollCount += 1
                    if (canRemove) {
                        continueRemovingPaperRolls = true
                        removablePaperRolls.add(row to col)
                    }
                }
            }
            paperRolls.removeAll(removablePaperRolls)
        }
        return accessiblePaperRollCount
    }

    internal fun solveFirst(diagram: List<String>): Int {
        return countAccessiblePaperRolls(diagram, false)
    }

    internal fun solveSecond(diagram: List<String>): Int {
        return countAccessiblePaperRolls(diagram, true)
    }
}
