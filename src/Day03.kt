fun main() {
    fun part1(input: List<String>): Int {
        var dominateBinary = IntArray(size = 12)
        input.forEach { inputString ->
            dominateBinary.forEachIndexed { index, value ->
                when (inputString[index]) {
                    '0' -> {
                        dominateBinary[index] = value - 1
                    }
                    '1' -> {
                        dominateBinary[index] = value + 1
                    }
                    else -> error("Binary should be represented only with 1 or 0!")
                }
            }
        }
        val gamma = dominateBinary.joinToString(separator = "") {
            if (it > 0) {
                "1"
            } else {
                "0"
            }
        }.toInt(2)
        val epsilon = dominateBinary.joinToString(separator = "") {
            if (it > 0) {
                "0"
            } else {
                "1"
            }
        }.toInt(2)
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        return input.size
    }
    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day03_test")
//    check(part1(testInput) == 8)
//    check(part2(testInput) == 8L)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
