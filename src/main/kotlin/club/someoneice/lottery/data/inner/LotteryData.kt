package club.someoneice.lottery.data.inner

data class LotteryData(
    val name: String,
    val code: String,
    var ItemList: HashMap<Int, ItemList>,
    val player: ArrayList<String>
)