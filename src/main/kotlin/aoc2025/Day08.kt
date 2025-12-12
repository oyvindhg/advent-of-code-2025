package aoc2025

import java.util.PriorityQueue
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.measureTimedValue

object Day08 {
    private const val INPUT_FILENAME = "day08.txt"

    fun solve() {
        println("===Day 08===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val boxes = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(boxes, 1000) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(boxes) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<Point> {
        return input.map { line ->
            val (x, y, z) = line.split(',').map { it.toInt() }
            Point(x, y, z)
        }
    }

    internal fun solveFirst(boxes: List<Point>, connectionCount: Int): Long {
        val boxDistanceMaxHeap = PriorityQueue<BoxDistance>(compareByDescending { it.distance })
        boxes.forEachIndexed { firstBoxNumber, firstBox ->
            for (secondBoxNumber in firstBoxNumber + 1 until boxes.size) {
                val secondBox = boxes[secondBoxNumber]
                val distance = calculateDistance(firstBox, secondBox)
                if (boxDistanceMaxHeap.size < connectionCount || distance < boxDistanceMaxHeap.first().distance) {
                    boxDistanceMaxHeap.add(BoxDistance(distance, firstBoxNumber, secondBoxNumber))
                }
                if (boxDistanceMaxHeap.size > connectionCount) {
                    boxDistanceMaxHeap.remove()
                }
            }
        }

        val connections = mutableMapOf<Int, MutableList<Int>>()
        for (boxDistance in boxDistanceMaxHeap) {
            val firstIdList = connections[boxDistance.firstBoxNumber]
            if (firstIdList != null) {
                firstIdList.add(boxDistance.secondBoxNumber)
            } else {
                connections[boxDistance.firstBoxNumber] = mutableListOf(boxDistance.secondBoxNumber)
            }

            val secondIdList = connections[boxDistance.secondBoxNumber]
            if (secondIdList != null) {
                secondIdList.add(boxDistance.firstBoxNumber)
            } else {
                connections[boxDistance.secondBoxNumber] = mutableListOf(boxDistance.firstBoxNumber)
            }
        }

        val visited = mutableSetOf<Int>()
        val clusterSizeMinHeap = PriorityQueue<Long>()

        boxes.forEachIndexed { initialBox, _ ->
            if (!visited.contains(initialBox)) {
                var clusterSize = 0L
                visited.add(initialBox)
                val toVisit = ArrayDeque(listOf(initialBox))
                while (toVisit.isNotEmpty()) {
                    clusterSize += 1
                    val currentBox = toVisit.removeFirst()
                    val nextBoxes = connections[currentBox]
                    if (nextBoxes != null) {
                        for (nextBox in nextBoxes) {
                            if (!visited.contains(nextBox)) {
                                visited.add(nextBox)
                                toVisit.addLast(nextBox)
                            }
                        }
                    }
                }
                if (clusterSizeMinHeap.size < 3 || clusterSize > clusterSizeMinHeap.first()) {
                    clusterSizeMinHeap.add(clusterSize)
                }
                if (clusterSizeMinHeap.size > 3) {
                    clusterSizeMinHeap.remove()
                }
            }
        }

        return clusterSizeMinHeap.reduce { result, clusterSize -> result * clusterSize }
    }

    internal fun solveSecond(boxes: List<Point>): Long {
        val boxRoots = mutableMapOf<Int, Int>()
        val boxDistances = boxes.flatMapIndexed { firstBoxNumber, firstBox ->
            boxRoots[firstBoxNumber] = firstBoxNumber
            (firstBoxNumber + 1 until boxes.size).map { secondBoxNumber ->
                val secondBox = boxes[secondBoxNumber]
                val distance = calculateDistance(firstBox, secondBox)
                BoxDistance(distance, firstBoxNumber, secondBoxNumber)
            }
        }
        val sortedBoxDistances = boxDistances.sortedBy { it.distance }

        sortedBoxDistances.forEachIndexed { index, (_, firstBoxNumber, secondBoxNumber) ->
            val firstBoxRoot = findRoot(boxRoots, firstBoxNumber)
            val secondBoxRoot = findRoot(boxRoots, secondBoxNumber)
            val smallestBoxNumber = min(firstBoxRoot, secondBoxRoot)
            boxRoots[firstBoxRoot] = smallestBoxNumber
            boxRoots[secondBoxRoot] = smallestBoxNumber
            if (checkAllRootsUnified(boxRoots)) {
                return (boxes[firstBoxNumber].x.toLong() * boxes[secondBoxNumber].x.toLong())
            }
        }
        throw IllegalStateException("No solution after all boxes merged")
    }

    private fun calculateDistance(firstPoint: Point, secondPoint: Point): Double {
        return sqrt(
            (firstPoint.x - secondPoint.x).toDouble().pow(2) +
                    (firstPoint.y - secondPoint.y).toDouble().pow(2) +
                    (firstPoint.z - secondPoint.z).toDouble().pow(2)
        )
    }

    private fun findRoot(boxRoots: Map<Int, Int>, box: Int): Int {
        var currentBox = box
        while (currentBox != boxRoots[currentBox]) {
            currentBox = boxRoots[currentBox]!!
        }
        return currentBox
    }

    private fun checkAllRootsUnified(boxRoots: Map<Int, Int>): Boolean {
        for (box in boxRoots.keys) {
            var currentBox = box
            while (currentBox != boxRoots[currentBox]) {
                currentBox = boxRoots[currentBox]!!
            }
            if (currentBox != 0) {
                return false
            }
        }
        return true
    }

    internal data class Point(val x: Int, val y: Int, val z: Int)

    private data class BoxDistance(val distance: Double, val firstBoxNumber: Int, val secondBoxNumber: Int)
}
