package club.someoneice.lottery

import club.someoneice.lottery.data.ItemList
import club.someoneice.lottery.data.LotteryData
import java.util.*

object DataSet {
    val itemList: HashMap<Int, ItemList> = HashMap<Int, ItemList>()

    var gift: HashMap<String, LotteryData> = HashMap<String, LotteryData>()

    var codeList: ArrayList<String> = ArrayList<String>()
}