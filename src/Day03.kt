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

    fun List<String>.findDominantBinary(
        searchIndex: Int,
    ): Int {
        return map {
            it[searchIndex]
        }.joinToString(separator = "")
            .partition { it.digitToInt() == 0 }
            .let {
                val zeroes = it.first.length
                val ones = it.second.length
                when {
                    zeroes == ones -> 1
                    zeroes > ones -> 0
                    else -> 1
                }
            }
    }

    fun filterByDominantBinary(
        input: List<String>,
        filterIndex: Int,
        isForOxygen: Boolean,
    ): List<String> {
        val dominantBinary = input.findDominantBinary(filterIndex)
        return when {
            input.size == 2 -> {
                if (input[0][filterIndex] == input[1][filterIndex]) {
                    input
                } else {
                    input.filter {
                        if (isForOxygen) {
                            it[filterIndex] == '1'
                        } else {
                            it[filterIndex] == '0'
                        }
                    }
                }
            }
            isForOxygen -> {
                input.filter {
                    if (dominantBinary == 1) {
                        it[filterIndex] == '1'
                    } else {
                        it[filterIndex] == '0'
                    }
                }
            }
            else -> {
                input.filter {
                    if (dominantBinary == 1) {
                        it[filterIndex] == '0'
                    } else {
                        it[filterIndex] == '1'
                    }
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        var oxygenList = input
        var step = 0
        while (oxygenList.size != 1) {
            oxygenList = filterByDominantBinary(input = oxygenList, filterIndex = step, isForOxygen = true)
            step++
        }
        step = 0
        val oxygen = oxygenList.joinToString(separator = "")
        val oxygenInt = oxygen.toInt(2)

        var notOxygenList = input
        while (notOxygenList.size != 1) {
            notOxygenList = filterByDominantBinary(input = notOxygenList, filterIndex = step, isForOxygen = false)
            step++
        }
        val notOxygen = notOxygenList.joinToString(separator = "")
        val notOxygenInt = notOxygen.toInt(2)
        return oxygenInt * notOxygenInt
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day03_test")
//    check(part1(testInput) == 8)
//    check(part2(testInput) == 8)

    val input = readInput("Day03")
//    println(part1(input))
    println(part2(input))
}