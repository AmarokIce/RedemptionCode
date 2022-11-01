package club.someoneice.lottery.helper

import club.someoneice.lottery.data.DataSet

object Fixed {

    // Fix it not input... But I didn't why happen it.
    fun FixCommandHashMapInPut(name: String) {
        for (i in 0 .. DataSet.itemList.size) {
            if (i == DataSet.itemList.size) return

            DataSet.gift[name]!!.ItemList[i] = DataSet.itemList[i]!!
        }
    }
}