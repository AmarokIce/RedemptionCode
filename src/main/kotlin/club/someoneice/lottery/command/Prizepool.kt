package club.someoneice.lottery.command

import club.someoneice.lottery.data.DataSet
import club.someoneice.lottery.data.inner.ItemList
import club.someoneice.lottery.data.inner.LotteryData
import club.someoneice.lottery.helper.Util
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ChatComponentTranslation
import java.util.*

class Prizepool: CommandBase() {
    override fun getCommandName(): String {
        return "prizepool"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/prizepool"
    }

    override fun processCommand(sender: ICommandSender, list: Array<String>) {
        val player: EntityPlayerMP = getPlayer(sender, sender.commandSenderName)
        val code: String = if (list.size >= 2) list[1]
        else Util.getRandomCode(7)
        DataSet.gift.put(code, LotteryData(list[0], code, HashMap<Int, ItemList>(), ArrayList<String>()))
        DataSet.gift[list[0]]!!.ItemList = DataSet.itemList.clone() as HashMap<Int, ItemList>
        DataSet.codeList.add(code)

        player.addChatMessage(ChatComponentTranslation("你创建了礼包： ${list[0]}, 兑换码: $code"))

        DataSet.itemList.clear()
        Util.setSystemClipboardText(code)
    }
}