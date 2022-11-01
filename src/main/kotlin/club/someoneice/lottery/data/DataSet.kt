package club.someoneice.lottery.data

import club.someoneice.lottery.data.inner.ItemList
import club.someoneice.lottery.data.inner.LotteryData
import java.util.*

object DataSet {
    val itemList: HashMap<Int, ItemList> = HashMap<Int, ItemList>()

    var gift: HashMap<String, LotteryData> = HashMap<String, LotteryData>()
}
