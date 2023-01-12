package club.someoneice.lottery.data

data class LotteryData(
    val name: String,
    val code: String,
    var ItemList: HashMap<Int, ItemList>,
    var player: ArrayList<String>
)