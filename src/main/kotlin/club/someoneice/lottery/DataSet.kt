package club.someoneice.lottery

import club.someoneice.lottery.data.ItemList
import club.someoneice.lottery.data.LotteryData

object DataSet {
    val itemList = HashMap<Int, ItemList>()

    var gift = HashMap<String, LotteryData>()
    var codeList = ArrayList<String>()
}
