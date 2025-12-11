package aoc2025

import kotlin.math.pow
import kotlin.time.measureTimedValue

object Day03 {
    private const val INPUT_FILENAME = "day03.txt"

    fun solve() {
        println("===Day 03===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val batteries = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(batteries) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(batteries) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<String> {
        return input
    }

    private fun updateHighestBatteries(highestBatteries: MutableList<Int>, currentBattery: Int, currentIndex: Int) {
        if (currentIndex == highestBatteries.size || highestBatteries[currentIndex] > currentBattery) {
            return
        }
        updateHighestBatteries(highestBatteries, highestBatteries[currentIndex], currentIndex + 1)
        highestBatteries[currentIndex] = currentBattery
    }

    private fun findLargestJoltage(batteries: List<String>, batterySelectionCount: Int): Long {
        var totalJoltage: Long = 0
        for (bank in batteries) {
            val highestBatteries = mutableListOf<Int>()
            bank.drop(bank.length - batterySelectionCount).forEach { battery ->
                highestBatteries.add(battery.digitToInt())
            }
            bank.dropLast(batterySelectionCount).reversed().forEach { battery ->
                updateHighestBatteries(highestBatteries, battery.digitToInt(), 0)
            }

            var joltage: Long = 0
            highestBatteries.reversed().forEachIndexed { batteryNumber, battery ->
                joltage += 10.0.pow(batteryNumber).toLong() * battery
            }
            totalJoltage += joltage
        }
        return totalJoltage
    }

    internal fun solveFirst(batteries: List<String>): Long {
        return findLargestJoltage(batteries, 2)
    }

    internal fun solveSecond(batteries: List<String>): Long {
        return findLargestJoltage(batteries, 12)
    }
}
