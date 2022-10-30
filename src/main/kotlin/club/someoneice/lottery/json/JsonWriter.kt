package club.someoneice.lottery.json

import club.someoneice.lottery.data.DataSet
import com.google.gson.GsonBuilder
import java.io.BufferedWriter
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

object JsonWriter {
    fun writer() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        // val jsonString = gson.toJson(Data.OreData)
        val path: String = System.getProperty("user.dir") + "\\lottery"
        val file = File(path)
        val pathFile = Paths.get("$path\\lottery.json")

        // val pathFile = FileWriter("$path\\output.json")

        if (!file.isDirectory && !file.exists()) {
            file.mkdir()
        }

        try {
            val iterator: Iterator<String> = DataSet.gift.keys.iterator()
            val writer: BufferedWriter = Files.newBufferedWriter(pathFile)
            // writer.write(jsonString)

            writer.write("[")

            while (iterator.hasNext()) {
                val it = iterator.next()
                writer.write(gson.toJson(DataSet.gift[it]))
                if (iterator.hasNext()) {
                    writer.write(",")
                }
                writer.newLine()
            }
            writer.write("]")
            writer.close()  // must be close.

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}