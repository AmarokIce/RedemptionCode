package club.someoneice.lottery.command

import club.someoneice.lottery.DataSet
import club.someoneice.lottery.Util
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

        for (i in DataSet.codeList) {
            if (list[0] == i) {
                if (DataSet.gift[i]!!.player.contains(player.displayName)) {
                    player.addChatMessage(ChatComponentTranslation("你已经兑换过 ${DataSet.gift[i]!!.name} 礼包了！") as IChatComponent)
                    return
                }

                player.addChatMessage(ChatComponentTranslation("你兑换了 ${DataSet.gift[i]!!.name} 礼包！") as IChatComponent)
                DataSet.gift[i]!!.player.add(player.displayName)
                for (item in 0 .. DataSet.gift[i]!!.ItemList.size) {
                    if (item == DataSet.gift[i]!!.ItemList.size) return

                    player.inventory.addItemStackToInventory(ItemStack(
                        Util.getItemByText(
                            DataSet.gift[i]!!.ItemList[item]!!.itemName.toString()),
                            DataSet.gift[i]!!.ItemList[item]!!.itemNumber,
                            DataSet.gift[i]!!.ItemList[item]!!.itemMeta)
                    )
                }
            }
        }

        player.addChatMessage(ChatComponentTranslation("无法发现礼包！") as IChatComponent)

    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }
}