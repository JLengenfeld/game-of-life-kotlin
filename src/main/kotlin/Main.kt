fun main() {
    var parentGeneration = initialGeneration()
    printGeneration(parentGeneration)
    var isEvolving = true

    while (isEvolving) {
        val nextGeneration = calculateNextGeneration(parentGeneration)
        printGeneration(nextGeneration)
        isEvolving = !nextGeneration.contentEquals(parentGeneration)
        parentGeneration = nextGeneration
    }
}

fun initialGeneration(): Array<IntArray> {
    return arrayOf(
        intArrayOf(0, 0, 0, 0, 0),
        intArrayOf(0, 0, 0, 1, 0),
        intArrayOf(0, 0, 0, 1, 0),
        intArrayOf(0, 0, 0, 1, 0),
        intArrayOf(0, 0, 0, 0, 0)
    )
}

fun printGeneration(generation: Array<IntArray>) {
    for (row in generation) {
        print("|")
        for (cell in row) {
            print(if (cell == 1) "*|" else " |")
        }
        println()
    }
    println()
    Thread.sleep(500)
}

fun calculateNextGeneration(parentGeneration: Array<IntArray>): Array<IntArray> {
    val nextGeneration = Array(parentGeneration.size) { IntArray(parentGeneration[0].size) }
    for (x in parentGeneration.indices) {
        for (y in parentGeneration[x].indices) {
            val neighbors = countNeighbors(x, y, parentGeneration)
            val isAlive = parentGeneration[x][y] == 1
            if (isAlive && (neighbors == 2 || neighbors == 3)) {
                nextGeneration[x][y] = 1
            } else if (!isAlive && neighbors == 3) {
                nextGeneration[x][y] = 1
            } else {
                nextGeneration[x][y] = 0
            }
        }
    }
    return nextGeneration
}

fun countNeighbors(x: Int, y: Int, generation: Array<IntArray>): Int {
    var neighbors = 0
    for (i in x - 1..x + 1) {
        for (j in y - 1..y + 1) {
            val isWithinBounds = i >= 0 && j >= 0 && i < generation.size && j < generation[i].size
            val isNotSelf = !((i == x) && (j == y))
            if (isWithinBounds && isNotSelf && generation[i][j] == 1) {
                neighbors++
            }
        }
    }
    return neighbors
}
