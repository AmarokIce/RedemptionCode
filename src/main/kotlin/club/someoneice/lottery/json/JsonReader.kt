package club.someoneice.lottery.json

import club.someoneice.lottery.LotteryMain.Companion.log
import club.someoneice.lottery.data.DataSet
import club.someoneice.lottery.data.inner.LotteryData
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object JsonReader {
    private val gson = Gson()
    private val getType = object : TypeToken<List<LotteryData>>() {}.type
    private val file = File(System.getProperty("user.dir") + "\\lottery\\lottery.json")
    fun reader(isHot: Boolean) {
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
}