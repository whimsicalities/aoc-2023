fun main() {
    fun getFullNumberAndFinalIndex(line: String, numberStartIndex: Int): Pair<Int, Int> {
        var fullNumber = ""
        var index = numberStartIndex
        while (index < line.length && line[index].isDigit()) {
            val character = line[index]
            fullNumber = "$fullNumber$character"
            index++
        }
        return Pair(fullNumber.toInt(), index-1)
    }

    fun isSymbol(character: Char): Boolean {
        return (character != '.') && (!character.isDigit())
    }

    fun isWithinBounds(input: List<String>, lineNumber: Int, indexWithinLine: Int): Boolean {
        return (lineNumber >= 0
            && lineNumber < input.size
            && indexWithinLine >= 0
            && indexWithinLine < input[lineNumber].length)
    }

    fun isWithinBoundsAndIsSymbol(input: List<String>, lineNumber: Int, indexWithinLine: Int): Boolean {
        return isWithinBounds(input, lineNumber, indexWithinLine) && isSymbol(input[lineNumber][indexWithinLine])
    }

    fun part1(input: List<String>): Int {
        var partNumbersSum = 0
        for (lineNumber in input.indices) {
            val line = input[lineNumber]
            var currentCharacterIndex = 0
            while (currentCharacterIndex<line.length) {
                val character = line[currentCharacterIndex]
                if (character.isDigit()) {
                    // retrieve end of number indices & whole number
                    val (fullNumber, finalIndex) = getFullNumberAndFinalIndex(line, currentCharacterIndex)
                    // check all directions for symbols and add it to sum if appropriate
                    // check left and right
                    if (isWithinBoundsAndIsSymbol(input, lineNumber, currentCharacterIndex-1)
                        || isWithinBoundsAndIsSymbol(input, lineNumber, finalIndex+1)) {
                        partNumbersSum += fullNumber
                    } else {
                        // check above and below
                        for (i in currentCharacterIndex-1..finalIndex+1) {
                            if (isWithinBoundsAndIsSymbol(input, lineNumber-1, i)
                                || isWithinBoundsAndIsSymbol(input, lineNumber+1, i)) {
                                partNumbersSum += fullNumber
                            }
                        }
                    }
                    // increase currentIndex to end indices of number + 1
                    currentCharacterIndex = finalIndex + 1
                } else {
                    currentCharacterIndex++
                }
            }
        }
        return partNumbersSum
    }

    fun findStartIndexOfNumber(line: String, somewhereInNumberIndex: Int): Int {
        var currentIndex = somewhereInNumberIndex
        do {
            currentIndex--
        } while (currentIndex>=0 && line[currentIndex].isDigit())
        return currentIndex+1
    }

    fun part2(input: List<String>): Int {
        var partNumbersSum = 0
        for (lineNumber in input.indices) {
            val line = input[lineNumber]
            for (currentCharacterIndex in line.indices) {
                val character = line[currentCharacterIndex]
                if (character == '*') {
                    // check either side
                    val relativeIndicesToCheckSides = arrayOf(Pair(0, -1), Pair(0, 1))
                    val adjacentNumbersFound = arrayListOf<Int>()
                    for (relativeIndicesPair in relativeIndicesToCheckSides) {
                        val shiftedLineNumber = lineNumber + relativeIndicesPair.first
                        val shiftedCharacterIndex = currentCharacterIndex + relativeIndicesPair.second
                        if (isWithinBounds(input,
                                shiftedLineNumber,
                                shiftedCharacterIndex)) {
                            val adjacentCharacter = input[shiftedLineNumber][shiftedCharacterIndex]
                            if (adjacentCharacter.isDigit()){
                                // Figure out which digit
                                val startIndex = findStartIndexOfNumber(input[shiftedLineNumber], shiftedCharacterIndex)
                                val (fullNumber, _) = getFullNumberAndFinalIndex(input[shiftedLineNumber], startIndex)
                                // Add it to the adjacent numbers found list
                                adjacentNumbersFound.add(fullNumber)
                            }
                        }
                    }
                    // check above and below
                    for (lineShift in -1..1 step 2) {
                        var index = -1
                        // Move along lines
                        while (index<=1) {
                            val shiftedLineNumber = lineNumber + lineShift
                            val shiftedCharacterIndex = currentCharacterIndex + index
                            if (isWithinBounds(input,
                                    shiftedLineNumber,
                                    shiftedCharacterIndex)) {
                                val adjacentCharacter = input[shiftedLineNumber][shiftedCharacterIndex]
                                if (adjacentCharacter.isDigit()){
                                    // Figure out which digit
                                    val startIndex = findStartIndexOfNumber(input[shiftedLineNumber], shiftedCharacterIndex)
                                    val (fullNumber, finalIndex) = getFullNumberAndFinalIndex(input[shiftedLineNumber], startIndex)
                                    // Add it to the adjacent numbers found list
                                    adjacentNumbersFound.add(fullNumber)
                                    index += (finalIndex - shiftedCharacterIndex) + 1 // Move along index so that the same number doesn't get counted again
                                    continue
                                }
                            }
                            index++
                        }
                    }

                    if (adjacentNumbersFound.size == 2){
                        partNumbersSum += adjacentNumbersFound[0] * adjacentNumbersFound[1]
                    }
                }
            }
        }
        return partNumbersSum
    }

    // debugging a problem I had with a specific input configuration ...
    val problemInput = readInput("Day03_problem_input")
    check(part2(problemInput) == 100)

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
