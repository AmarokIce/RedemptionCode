package club.someoneice.lottery.command

import club.someoneice.lottery.data.DataSet
import club.someoneice.lottery.data.inner.ItemList
import club.someoneice.lottery.helper.Util
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.util.IChatComponent

class Cash: CommandBase() {
    override fun getCommandName(): String {
        return "cash"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/cash"
    }

    override fun processCommand(sender: ICommandSender, list: Array<String>) {
        val player: EntityPlayerMP = getCommandSenderAsPlayer(sender)
        val iterator: Iterator<String> = DataSet.gift.keys.iterator()
        while (iterator.hasNext()) {
            val it = iterator.next()
            if (list[0] == DataSet.gift[it]!!.code) {
                if (DataSet.gift[it]!!.player.contains(player.displayName)) {
                    player.addChatMessage(ChatComponentTranslation("你已经兑换过 ${DataSet.gift[it]!!.name} 礼包了！") as IChatComponent)
                    return
                }
                player.addChatMessage(ChatComponentTranslation("你兑换了 ${DataSet.gift[it]!!.name} 礼包！") as IChatComponent)
                DataSet.gift[it]!!.player.add(player.displayName)
                for (item in 0 .. DataSet.gift[it]!!.ItemList.size){
                    if (item == DataSet.gift[it]!!.ItemList.size) return

                    player.inventory.addItemStackToInventory(ItemStack(
                        getItemByText(sender, DataSet.gift[it]!!.ItemList[item]!!.itemName.toString()),
                        DataSet.gift[it]!!.ItemList[item]!!.itemNumber,
                        DataSet.gift[it]!!.ItemList[item]!!.itemMeta))

                }
            }
        }

        player.addChatMessage(ChatComponentTranslation("无法发现礼包！") as IChatComponent)

    }
}