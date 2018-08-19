import java.io.File

const val INPUT_FOLDER = "."
const val OUTPUT_FOLDER = "converted"

const val EXT_MP3 = "mp3"
const val EXT_SMP = "smp"

const val USEAGE = "please provide only an existing folder or file name"

const val KEY = 'f'.toInt()

fun main(args: Array<String>) {
    println("StoryMania file converter")
    when {
        args.isEmpty() && File(INPUT_FOLDER).exists() -> println("converting files in folder '$INPUT_FOLDER'")
        args.size > 1 -> println(USEAGE)
        args.size == 1 -> {
            val file = File(args[0])
            if (!file.exists()) {
                System.err.println("File or folder '${args[0]}' does not exist, $USEAGE")
                System.exit(1)
            }
            transcode(file)
        }
    }
}

private fun transcode(file: File){
    if (file.isDirectory){
        file.listFiles().map {transcode(it) }
    }
    if (file.isFile){
        if (file.extension.toLowerCase() == EXT_SMP){
            transcode(file, EXT_MP3)
        } else if (file.extension.toLowerCase() == EXT_MP3 ){
            transcode(file, EXT_SMP)
        } else {
            System.err.println("Skipped file with wrong extension '${file.canonicalFile}'")
        }
    }
}

private fun transcode(file: File, outExtension: String) {
    val input = file
            .inputStream()
            .buffered()
    val output = File(OUTPUT_FOLDER, file.nameWithoutExtension+"."+outExtension)
    output.parentFile.mkdirs()
    output.createNewFile()
    output.outputStream()
            .buffered()
            .use {
                var next = input.read()
                while (next != -1) {
                    it.write(next.xor(KEY))
                    next = input.read()
                }
            }
}

