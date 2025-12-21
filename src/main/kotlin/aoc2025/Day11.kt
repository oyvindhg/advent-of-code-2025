package aoc2025

import kotlin.time.measureTimedValue

object Day11 {
    private const val INPUT_FILENAME = "day11.txt"

    fun solve() {
        println("===Day 11===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val serverRack = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(serverRack) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(serverRack) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<DeviceConnections> {
        return input.map { line ->
            val devices = line.split(" ")
            val from = devices.first().substring(0, 3)
            val to = devices.drop(1)
            DeviceConnections(from, to)
        }
    }

    internal fun solveFirst(serverRack: List<DeviceConnections>): Long {
        val connections = serverRack.associate { it.from to it.to }
        return countPathsRecursive(
            connections,
            DeviceNode("you", visitedDac = false, visitedFft = false),
            false,
            mutableMapOf()
        )
    }

    internal fun solveSecond(serverRack: List<DeviceConnections>): Long {
        val connections = serverRack.associate { it.from to it.to }
        return countPathsRecursive(
            connections,
            DeviceNode("svr", visitedDac = false, visitedFft = false),
            true,
            mutableMapOf()
        )
    }

    private fun countPathsRecursive(
        connections: Map<String, List<String>>,
        deviceNode: DeviceNode,
        mustBeConverted: Boolean,
        pathCountCache: MutableMap<DeviceNode, Long>
    ): Long {
        val cachedPathCount = pathCountCache[deviceNode]
        if (cachedPathCount != null) {
            return cachedPathCount
        }

        if (deviceNode.device == "out") {
            return if (!mustBeConverted || (deviceNode.visitedDac && deviceNode.visitedFft)) {
                1L
            } else {
                0L
            }
        }

        val nextVisitedDac = deviceNode.visitedDac || deviceNode.device == "dac"
        val nextVisitedFft = deviceNode.visitedFft || deviceNode.device == "fft"

        val nextDevices = connections[deviceNode.device]!!

        val pathCount = nextDevices.sumOf { nextDevice ->
            countPathsRecursive(
                connections,
                DeviceNode(nextDevice, nextVisitedDac, nextVisitedFft),
                mustBeConverted,
                pathCountCache
            )
        }

        pathCountCache[deviceNode] = pathCount

        return pathCount
    }

    internal data class DeviceConnections(val from: String, val to: List<String>)

    private data class DeviceNode(val device: String, val visitedDac: Boolean, val visitedFft: Boolean)
}
