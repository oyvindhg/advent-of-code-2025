package aoc2025

import com.google.ortools.Loader
import com.google.ortools.sat.*
import kotlin.time.measureTimedValue


object Day10 {
    private const val INPUT_FILENAME = "day10.txt"

    fun solve() {
        println("===Day 10===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val manuals = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(manuals) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(manuals) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<Manual> {
        return input.map { line ->
            val manualSections = line.split(" ")
            val lightsSection = manualSections.first()
            val lights = lightsSection.substring(1, lightsSection.length - 1)
            val buttons = manualSections.drop(1).dropLast(1).map { buttonSection ->
                val buttonsString = buttonSection.substring(1, buttonSection.length - 1)
                buttonsString.split(',').map { it.toInt() }
            }
            val joltageSection = manualSections.last()
            val joltages = joltageSection
                .substring(1, joltageSection.length - 1)
                .split(',')
                .map { it.toInt() }

            Manual(lights, buttons, joltages)
        }
    }

    internal fun solveFirst(manuals: List<Manual>): Int {
        return manuals.sumOf { manual ->
            runLightBfsSearch(manual)
        }
    }

    internal fun solveSecond(manuals: List<Manual>): Int {
        Loader.loadNativeLibraries()
        return manuals.sumOf { manual ->
            runJoltageCpSatSolver(manual)
        }
    }

    private fun runLightBfsSearch(manual: Manual): Int {
        val initialLights = ".".repeat(manual.lights.length)
        val seenLights = mutableSetOf(initialLights)
        val toVisitLights = ArrayDeque(listOf(Pair(initialLights, 0)))

        while (!toVisitLights.isEmpty()) {
            val (currentLights, currentTurn) = toVisitLights.removeFirst()
            for (toggledLights in manual.buttons) {
                val nextLights = currentLights.mapIndexed { lightNumber, light ->
                    if (!toggledLights.contains(lightNumber)) {
                        light
                    } else {
                        if (light == '#') '.' else '#'
                    }
                }.joinToString("")

                if (nextLights == manual.lights) {
                    return currentTurn + 1
                }

                if (!seenLights.contains(nextLights)) {
                    seenLights.add(nextLights)
                    toVisitLights.addLast(Pair(nextLights, currentTurn + 1))
                }
            }
        }
        throw IllegalStateException("No way to turn on the lights to reach the state in the manual")
    }

    private fun runJoltageCpSatSolver(manual: Manual): Int {
        val model = CpModel()

        val upperLimit = manual.joltages.max().toLong()
        val variables = manual.buttons.mapIndexed { buttonNumber, _ ->
            model.newIntVar(0, upperLimit, buttonNumber.toString())
        }.toTypedArray()

        manual.joltages.forEachIndexed { joltageMachineNumber, joltage ->
            val coefficients = manual.buttons.map { buttonMachineNumbers ->
                if (buttonMachineNumbers.contains(joltageMachineNumber)) {
                    1L
                } else {
                    0L
                }
            }.toLongArray()
            model.addEquality(
                LinearExpr.weightedSum(variables, coefficients),
                joltage.toLong()
            )
        }

        model.minimize(LinearExpr.sum(variables))

        val solver = CpSolver()
        val status = solver.solve(model)

        if (status != CpSolverStatus.OPTIMAL && status != CpSolverStatus.FEASIBLE) {
            throw IllegalStateException("No solution found")
        }

        return variables.sumOf {solver.value(it)}.toInt()
    }

    internal data class Manual(val lights: String, val buttons: List<List<Int>>, val joltages: List<Int>)
}
