package aoc2025

import kotlin.time.measureTimedValue

object Day06 {
    private const val INPUT_FILENAME = "day06.txt"

    fun solve() {
        println("===Day 06===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val worksheet = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(worksheet) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(worksheet) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): Worksheet {
        val operatorLocations = input.last()
            .mapIndexed { index, character -> Pair(index, character) }
            .filter{!it.second.isWhitespace()}
            .map { it.first }

        val operators = input.last().trim().split("\\s+".toRegex())

        val numberGroup = input.dropLast(1).map { row ->
            operatorLocations.mapIndexed { index, location ->
                if (index == operatorLocations.size - 1) {
                    row.substring(location)
                } else {
                    row.substring(location, operatorLocations[index + 1] - 1)
                }
            }
        }

        val rows = numberGroup.size
        val cols = numberGroup[0].size

        // Transpose in order to get a section (column) based list instead of row based
        val numberSections = List(cols) { col ->
            List(rows) { row ->
                numberGroup[row][col]
            }
        }

        return Worksheet(numberSections, operators)
    }

    internal fun solveFirst(worksheet: Worksheet): Long {
        val numbers = worksheet.numberSections.map { col ->
            col.map { number ->
                number.trim().toLong()
            }
        }

        return doMath(numbers, worksheet.operators)
    }

    internal fun solveSecond(worksheet: Worksheet): Long {
        val numbers = worksheet.numberSections.map { col ->
            col[0].indices.map { numberIndex ->
                col.indices.map { digitIndex ->
                    col[digitIndex][numberIndex]
                }.joinToString("").trim().toLong()
            }
        }

        return doMath(numbers, worksheet.operators)
    }

    private fun doMath(numbers: List<List<Long>>, operators: List<String>): Long {
        return numbers.foldIndexed(0L) { col, solution, column ->
            val columnResult = when (operators[col]) {
                "+" -> {
                    column.reduce { acc, number -> acc + number }
                }

                "*" -> {
                    column.reduce { acc, number -> acc * number }
                }

                else -> throw IllegalArgumentException("Invalid operator: ${operators[col]}")
            }
            solution + columnResult
        }
    }

    internal data class Worksheet(val numberSections: List<List<String>>, val operators: List<String>)
}
