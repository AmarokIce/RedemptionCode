package club.someoneice.lottery

import club.someoneice.lottery.data.LotteryData
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.lang.reflect.Type
import java.nio.file.Files
import java.nio.file.Paths

object JsonHelper {
// TODO
    fun reader(isHot: Boolean) {
        val gson = Gson()
        val getType: Type = object : TypeToken<List<LotteryData>>() {}.type
        val file = File(System.getProperty("user.dir") + "\\lottery\\lottery.json")

        if (!file.isDirectory && !file.exists())
            return

        val text = StringBuffer()
        val buffreader = BufferedReader(FileReader(file))

        try {
            while (true) {
                val str = buffreader.readLine()
                if (str == null) break else text.append(str.toString())
            }
            buffreader.close()
            val output: String = text.toString()
            LotteryMain.LOGGER.info(output)
            val list: List<LotteryData> = gson.fromJson(output, getType)
            if (!isHot) for (i in list) DataSet.gift[i.code] = i
            else {
                for (i in list) {
                    if (DataSet.gift.containsKey(i.code)) i.player = DataSet.gift[i.code]!!.player
                    DataSet.gift[i.code] = i
                }
            }

        } catch (_: Exception) {

        }
    }

    fun writer() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val path: String = System.getProperty("user.dir") + "\\lottery"
        val file = File(path)
        val pathFile = Paths.get("$path\\lottery.json")

        if (!file.isDirectory && !file.exists())
            file.mkdir()

        try {
            val iterator: Iterator<String> = DataSet.gift.keys.iterator()
            val writer: BufferedWriter = Files.newBufferedWriter(pathFile)

            writer.write("[")
            writer.newLine()
            while (iterator.hasNext()) {
                val it = iterator.next()
                writer.write(gson.toJson(DataSet.gift[it]))
                if (iterator.hasNext())
                    writer.write(",")
                writer.newLine()
            }
            writer.write("]")
            writer.close()  // must be close.
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}