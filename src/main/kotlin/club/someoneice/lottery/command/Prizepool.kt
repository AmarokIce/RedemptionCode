package club.someoneice.lottery.command

import club.someoneice.lottery.data.DataSet
import club.someoneice.lottery.data.inner.ItemList
import club.someoneice.lottery.data.inner.LotteryData
import club.someoneice.lottery.helper.Util
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.TextComponentTranslation

class Prizepool: CommandBase() {
    override fun getName(): String {
        return "prizepool"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "/prizepool"
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, list: Array<String>) {
        val player: EntityPlayerMP = sender.commandSenderEntity as EntityPlayerMP
        val code: String = if (list.size >= 2) list[1]
        else Util.getRandomCode(7)
        DataSet.gift.put(list[0], LotteryData(list[0], code, HashMap<Int, ItemList>(), ArrayList<String>()))
        DataSet.gift[list[0]]!!.ItemList = DataSet.itemList
        for (i in 0 .. DataSet.itemList.size) {
            DataSet.gift[list[0]]!!.ItemList[i] = DataSet.itemList[i]!!
        }

        player.sendMessage(TextComponentTranslation("你创建了礼包： ${list[0]}, 兑换码: $code"))

        DataSet.itemList.clear()
        Util.setSystemClipboardText(code)
    }
}

