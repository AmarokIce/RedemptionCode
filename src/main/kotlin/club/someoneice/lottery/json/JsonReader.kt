package club.someoneice.lottery.json

import club.someoneice.lottery.LotteryMain
import club.someoneice.lottery.LotteryMain.Companion.log
import club.someoneice.lottery.data.DataSet
import club.someoneice.lottery.data.inner.LotteryData
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*

object JsonReader {
    val gson = Gson()
    val getType = object : TypeToken<List<LotteryData>>() {}.type
    val file = File(System.getProperty("user.dir") + "\\lottery\\lottery.json")
    fun reader() {
        val text = StringBuffer()
        val buffreader = BufferedReader(FileReader(file))

        try {
            while (true) {
                val str = buffreader.readLine()
                if (str == null) break else text.append(str.toString())
            }
            buffreader.close()
            val output: String = text.toString()
            LotteryMain.log.info(output)
            val list: List<LotteryData> = gson.fromJson(output, getType)
            for (i in 0 .. list.size) {
                if (i == list.size) return
                val getList: LotteryData = list[i]
                DataSet.gift.put(getList.name, getList)
            }

        } catch (_: Exception) {

        }
    }
}