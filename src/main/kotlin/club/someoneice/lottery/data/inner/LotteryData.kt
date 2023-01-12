package club.someoneice.lottery.data.inner

import java.util.*

data class LotteryData(
    val name: String,
    val code: String,
    var ItemList: HashMap<Int, ItemList>,
    var player: ArrayList<String>
)