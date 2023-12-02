import kotlin.math.max

fun main() {
    fun processIntoHandfuls(game: String): List<String> {
        return game.split("; ", ": ")
    }

    fun findMaximumForHandfuls(handfuls: List<String>): Triple<Int, Int, Int>{
        var maxGreen = 0
        var maxRed = 0
        var maxBlue = 0
        for (i in 1..<handfuls.size) {
            val handful = handfuls[i]
            val numberedColours = handful.split(", ", " ")
            for (j in numberedColours.indices step 2) {
                val number = numberedColours[j].toInt()
                val colour = numberedColours[j + 1]
                when (colour) {
                    "green" -> maxGreen = max(maxGreen, number)
                    "red" -> maxRed = max(maxRed, number)
                    "blue" -> maxBlue = max(maxBlue, number)
                }
            }
        }
        return Triple(maxGreen, maxRed, maxBlue)
    }

    fun part1(input: List<String>): Int {
        var possibleGamesIdSum = 0
        for (line in input) {
            val handfuls = processIntoHandfuls(line)
            val (maxGreen, maxRed, maxBlue) = findMaximumForHandfuls(handfuls)
            if (maxGreen<=13 && maxRed<=12 && maxBlue<=14){
                possibleGamesIdSum += handfuls[0].split(" ")[1].toInt()
            }
        }
        return possibleGamesIdSum
    }

    fun part2(input: List<String>): Int {
        var minimumSetsOfCubesPowerSum = 0
        for (line in input) {
            val handfuls = processIntoHandfuls(line)
            val (maxGreen, maxRed, maxBlue) = findMaximumForHandfuls(handfuls)
            minimumSetsOfCubesPowerSum += maxGreen * maxRed * maxBlue
        }
        return minimumSetsOfCubesPowerSum
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
