package club.someoneice.lottery

import club.someoneice.lottery.LotteryMain.Companion.log
import club.someoneice.lottery.data.LotteryData
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Paths

object JsonHelper {
    private val gson = Gson()

    fun reader(isHot: Boolean) {
        val getType = object : TypeToken<List<LotteryData>>() {}.type
        val file = File(System.getProperty("user.dir") + "\\lottery\\lottery.json")

        if (!file.isDirectory && !file.exists()) return
        val text = StringBuffer()
        val buffreader = BufferedReader(FileReader(file))

        try {
            while (true) {
                val str = buffreader.readLine()
                if (str == null) break else text.append(str.toString())
            }
            buffreader.close()
            val output: String = text.toString()
            log.info(output)
            val list: List<LotteryData> = gson.fromJson(output, getType)
            for (i in list) {
                if (!isHot) DataSet.gift.put(i.code, i)
                else {
                    if (DataSet.gift.containsKey(i.code)) {
                        i.player = DataSet.gift[i.code]!!.player
                    }

                    DataSet.gift.put(i.code, i)
                }
            }

        } catch (_: Exception) {

        }
    }

    fun writer() {
        val path: String = System.getProperty("user.dir") + "\\lottery"
        val file = File(path)
        val pathFile = Paths.get("$path\\lottery.json")

        if (!file.isDirectory && !file.exists()) {
            file.mkdir()
        }

        try {
            val iterator: Iterator<String> = DataSet.gift.keys.iterator()
            val writer: BufferedWriter = Files.newBufferedWriter(pathFile)

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
            writer.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}