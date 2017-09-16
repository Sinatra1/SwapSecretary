import java.io.File

fun main(args: Array<String>) {
    var lines = File("input.txt").readLines()

    var n = lines[0].toInt() - 1

    var A = lines[1].split(" ").map { it.toInt() }.toTypedArray()

    var rows = sort(A, n)

    writeToFile(A, rows)

}

fun sort(AOrigin: Array<Int>, n: Int): ArrayList<Array<Int>> {
    var A = AOrigin
    var B: List<Int> = emptyList()
    var min: Int? = 0
    var minIndex = 0

    var rows: ArrayList<Array<Int>> = ArrayList<Array<Int>>()
    var indexes: Array<Int> = Array<Int>(2, {0})
    var usedIndexes: HashMap<Int, Int> = HashMap<Int, Int>()

    for (j in 1 .. n) {
        B = A.sliceArray(IntRange(j, A.size-1)).toList()
        min = B.min()

        if (A[j-1] > min!!) {
            minIndex = getMinIndex(A, j, min, usedIndexes)

            A = swap(A, j-1, minIndex)

            usedIndexes[min!!] = minIndex

            indexes[0] = j

            indexes[1] = minIndex + 1

            rows.add(indexes.clone())
        }
    }

    return rows
}

fun writeToFile(A: Array<Int>, rows: ArrayList<Array<Int>>) {

    val file = File("output.txt")

    file.createNewFile()

    file.writeText("")
    rows.forEach {
        file.appendText("Swap elements at indices " + it[0] + " and " + it[1] + ".\n")
    }

    file.appendText("No more swaps needed.\n")

    file.appendText(A.joinToString(" "))
}

fun swap(A: Array<Int>, indexA: Int, indexB: Int): Array<Int> {
    var tmp = A[indexA]
    A[indexA] = A[indexB]
    A[indexB] = tmp

    return A
}

fun getMinIndex(B: Array<Int>, j: Int, min: Int?, usedIndexes: HashMap<Int, Int>? = null): Int {
    var minIndex = 0

    B.forEachIndexed { index, s ->
        if (j <= index &&
                (!usedIndexes!!.containsKey(min) || usedIndexes[min!!] != index) &&
                s == min) {
            minIndex = index
            return minIndex
        }
    }

    return minIndex
}