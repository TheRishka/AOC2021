fun main() {
    fun fillBoards(inputBoards: List<String>) =
        inputBoards.filter { it.isNotBlank() }.chunked(5).flatMap { board ->
            listOf(board.map { row ->
                row.split(' ').filter { it.isNotBlank() }.map {
                    it.trim().toInt()
                }
            })
        }.map { intsBoard ->
            val cols = mutableListOf<List<Bing>>()
            val rows = mutableListOf<List<Bing>>()
            intsBoard.forEachIndexed { index, board ->
                rows += board.map {
                    Bing(it)
                }
                val colValues = mutableListOf<Bing>()
                for (j in 0..4) {
                    colValues.add(
                        Bing(intsBoard[j][index])
                    )
                }
                cols.add(
                    colValues
                )
            }
            Board(
                cols = cols, rows = rows
            )
        }

    fun part1(
        inputNumbers: List<String>,
        inputBoards: List<String>
    ): Int {
        val numbers = inputNumbers.first().split(',').filter { it.isNotBlank() }.map { value -> value.trim().toInt() }

        val boards = fillBoards(inputBoards)

        for (number in numbers) {
            var winBoard: Board? = null
            boards.forEach { board ->
                board.markIfFound(number)
                if (board.checkIfWin()) {
                    winBoard = board.copy()
                    return@forEach
                }
            }
            if (winBoard != null) {
                winBoard?.prettyPrint()
                println(winBoard?.getAllUnmarked()?.map { it.value })
                val sumOfUnmarked = winBoard?.getAllUnmarked()?.sumOf { it.value } ?: 0
                return (sumOfUnmarked * number)
            }
        }
        return 0
    }

    fun part2(
        inputNumbers: List<String>,
        inputBoards: List<String>
    ): Int {
        val numbers = inputNumbers.first().split(',').filter { it.isNotBlank() }.map { value -> value.trim().toInt() }

        val boards = fillBoards(inputBoards)

        for (number in numbers) {
            var lastWinBoard: Board? = null
            var foundTheLastNumber = false
            boards.forEach { board ->
                board.markIfFound(number)
                if (boards.all { it.checkIfWin() }) {
                    foundTheLastNumber = true
                }
            }
            if (foundTheLastNumber) {
                boards.forEach { board ->
                    board.unmarkIfFound(number)
                }
                lastWinBoard = boards.last { !it.checkIfWin() }
                lastWinBoard.markIfFound(number)
            }
            if (lastWinBoard != null) {
                lastWinBoard.prettyPrint()
                println(lastWinBoard.getAllUnmarked().map { it.value })
                val sumOfUnmarked = lastWinBoard.getAllUnmarked().sumOf { it.value }
                return (sumOfUnmarked * number)
            }
        }
        return 0
    }

    val numbers =
        readInput("Day04")

    val boards = readInput("Day04_2")
//    println(part1(numbers, boards))
//    println(part2(numbers, boards))

    println(part1(numbers, boards))
    println(part2(numbers, boards))
}

data class Board(
    private val rows: List<List<Bing>>,
    private val cols: List<List<Bing>>,
) {

    fun markIfFound(valueToMark: Int) {
        for (row in rows) {
            row.find { it.value == valueToMark }?.let {
                it.isMarked = true
            }
        }
        for (col in cols) {
            col.find { it.value == valueToMark }?.let {
                it.isMarked = true
            }
        }
    }

    fun unmarkIfFound(valueToMark: Int) {
        for (row in rows) {
            row.find { it.value == valueToMark }?.let {
                it.isMarked = false
            }
        }
        for (col in cols) {
            col.find { it.value == valueToMark }?.let {
                it.isMarked = false
            }
        }
    }

    fun checkIfWin(): Boolean {
        val atLeastOneRow = rows.any { row ->
            row.all {
                it.isMarked
            }
        }
        if (atLeastOneRow) {
            return true
        }
        val atLeastOneColumn = cols.any { col ->
            col.all {
                it.isMarked
            }
        }
        return atLeastOneColumn
    }

    fun prettyPrint() {
        for (row in rows) {
            println(row.joinToString(separator = " ") {
                "${if (it.value < 10) " ${it.value}" else it.value}" + "(${if (it.isMarked) "+" else "-"})"
            })
        }
    }

    fun getAllUnmarked() = cols.flatten().filter { !it.isMarked }
}

data class Bing(
    val value: Int, var isMarked: Boolean = false
)
