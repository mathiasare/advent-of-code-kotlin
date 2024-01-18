package util

import java.io.File
import java.nio.charset.StandardCharsets

fun readFileLines(fileName: String, test: Boolean = false): List<String> {
    var dir = "src/main/resources/input/"
    if (test) {
        dir = "src/main/resources/test/"
    }
    return File("$dir$fileName.txt").readLines(charset = StandardCharsets.UTF_8)
}