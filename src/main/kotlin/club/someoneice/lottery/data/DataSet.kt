package club.someoneice.lottery.data

import club.someoneice.lottery.data.inner.ItemList
import club.someoneice.lottery.data.inner.LotteryData

object DataSet {
    val itemList = HashMap<Int, ItemList>()

    var gift = HashMap<String, LotteryData>()
}
