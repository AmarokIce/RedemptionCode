package club.someoneice.lottery.json

import club.someoneice.lottery.LotteryMain
import club.someoneice.lottery.data.DataSet
import club.someoneice.lottery.data.inner.LotteryData
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.reflect.Type

object JsonReader {
    private val gson = Gson()
    private val getType: Type = object : TypeToken<List<LotteryData>>() {}.type
    private val file = File(System.getProperty("user.dir") + "\\lottery\\lottery.json")
    fun reader() {
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
            for (i in 0 .. list.size) {
                if (i == list.size) return
                val getList: LotteryData = list[i]
                DataSet.gift[getList.name] = getList
            }

        } catch (_: Exception) {

        }
    }
}