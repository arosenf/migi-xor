import java.io.File

const val INPUT_FOLDER = "input"
const val OUTPUT_FOLDER = "output"

const val EXT_MP3 = ".mp3"
const val EXT_SMP = ".smp"

const val KEY = 'f'.toInt()

fun main(args: Array<String>) {
    when {
        args.isEmpty() -> generateSequence(1, { it + 1 })
                .map { "M" + it.toString().padStart(2, '0') }
                .takeWhile { toFile(INPUT_FOLDER, it, EXT_SMP).exists() }
                .forEach { transcode(it, EXT_SMP, EXT_MP3) }
        args[0] == "dec" -> decode(args[1])
        args[0] == "enc" -> encode(args[1])
    }
}

private fun decode(filename: String) {
    transcode(filename, EXT_SMP, EXT_MP3)
}

private fun encode(filename: String) {
    transcode(filename, EXT_MP3, EXT_SMP)
}

private fun transcode(filename: String, inExtension: String, outExtension: String) {
    val input = (toFile(INPUT_FOLDER, filename, inExtension)
            .takeIf { it.exists() } ?: return)
            .inputStream()
            .buffered()

    toFile(OUTPUT_FOLDER, filename, outExtension)
            .outputStream()
            .buffered()
            .use {
                var next = input.read()
                while (next != -1) {
                    it.write(next.xor(KEY))
                    next = input.read()
                }
            }
}

private fun toFile(folder: String, name: String, extenstion: String): File {
    return File(folder, name + extenstion)
}
