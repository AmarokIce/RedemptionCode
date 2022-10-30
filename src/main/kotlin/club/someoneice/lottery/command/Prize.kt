package club.someoneice.lottery.command

import club.someoneice.lottery.data.DataSet
import club.someoneice.lottery.data.inner.ItemList
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentTranslation

class Prize: CommandBase() {
    override fun getCommandName(): String {
        return "prize"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/prize"
    }

    override fun processCommand(sender: ICommandSender, list: Array<String>) {
        val player: EntityPlayerMP = getPlayer(sender, sender.commandSenderName)
        val item: ItemStack? = player.inventory.getCurrentItem()
        if (item != null) {
            var i = 0
            while(true) {
                if (!DataSet.itemList.containsKey(i)) {
                    DataSet.itemList.put(i, ItemList(Item.getIdFromItem(item.item), item.stackSize, item.itemDamage))
                    break
                } else if (i > 27){
                    player.addChatMessage(ChatComponentTranslation("你添加了太多物品！"))
                    break
                } else i ++
            }
            player.addChatMessage(ChatComponentTranslation("你添加了物品： ${item.displayName}"))
        }
    }
}