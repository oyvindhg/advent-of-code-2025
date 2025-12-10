package aoc2025

import kotlin.time.measureTimedValue

object Day05 {
    private const val INPUT_FILENAME = "day05.txt"

    fun solve() {
        println("===Day 05===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val inventory = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(inventory) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(inventory) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): Inventory {
        val separatingLine = input.indexOfFirst { it.isBlank() }
        val inputRanges = input.subList(0, separatingLine)
        val ranges = inputRanges.map { inputRange ->
            val (from, to) = inputRange.split('-')
            IngredientRange(from.toLong(), to.toLong())
        }

        val inputIngredients = input.subList(separatingLine + 1, input.size)
        val ingredients = inputIngredients.map { it.toLong() }

        return Inventory(ranges, ingredients)
    }

    internal fun solveFirst(inventory: Inventory): Long {
        val fromList = inventory.ranges.map{ it.from }.sorted()
        val toList = inventory.ranges.map { it.to }.sorted()
        val ingredients = inventory.ingredients.sorted()

        var fromId = 0
        var toId = 0
        var currentlyActiveRanges = 0
        return ingredients.count { ingredient ->
            while (fromId < fromList.size && fromList[fromId] <= ingredient) {
                currentlyActiveRanges += 1
                fromId += 1
            }
            while (toId < toList.size && toList[toId] < ingredient) {
                currentlyActiveRanges -= 1
                toId += 1
            }
            currentlyActiveRanges > 0
        }.toLong()
    }

    internal fun solveSecond(inventory: Inventory): Long {
        val fromList = inventory.ranges.map{ it.from }.sorted()
        val toList = inventory.ranges.map { it.to }.sorted()

        var fromId = 0
        var currentlyActiveRanges = 0
        var currentActiveFrom = fromList[0]
        var freshIngredients: Long = 0
        toList.forEach { to ->
            while (fromId < fromList.size && fromList[fromId] <= to) {
                currentlyActiveRanges += 1
                fromId += 1
            }
            currentlyActiveRanges -= 1
            if (currentlyActiveRanges == 0) {
                freshIngredients += to - currentActiveFrom + 1
                if (fromId < fromList.size) {
                    currentActiveFrom = fromList[fromId]
                }
            }
        }
        return freshIngredients
    }

    internal data class IngredientRange(val from: Long, val to: Long)
    internal data class Inventory(val ranges: List<IngredientRange>, val ingredients: List<Long>)
}
