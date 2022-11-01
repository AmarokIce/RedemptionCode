package club.someoneice.lottery.command

import club.someoneice.lottery.data.DataSet
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentTranslation

class Cash: CommandBase() {
    override fun getName(): String {
        return "cash"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "/cash"
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, list: Array<String>) {
        val player: EntityPlayerMP = getCommandSenderAsPlayer(sender)
        val iterator: Iterator<String> = DataSet.gift.keys.iterator()
        while (iterator.hasNext()) {
            val it = iterator.next()
            if (list[0] == DataSet.gift[it]!!.code) {
                if (DataSet.gift[it]!!.player.contains(player.displayNameString.toString())) {
                    player.sendMessage(TextComponentTranslation("你已经兑换过 ${DataSet.gift[it]!!.name} 礼包了！") as ITextComponent)
                    return
                }
                player.sendMessage(TextComponentTranslation("你兑换了 ${DataSet.gift[it]!!.name} 礼包！") as ITextComponent)
                DataSet.gift[it]!!.player.add(player.displayNameString.toString())
                for (item in 0 .. DataSet.gift[it]!!.ItemList.size){
                    if (item == DataSet.gift[it]!!.ItemList.size) return

                    player.inventory.addItemStackToInventory(ItemStack(
                        getItemByText(sender, DataSet.gift[it]!!.ItemList[item]!!.itemName),
                        DataSet.gift[it]!!.ItemList[item]!!.itemNumber,
                        DataSet.gift[it]!!.ItemList[item]!!.itemMeta))

                }
            }
        }

        player.sendMessage(TextComponentTranslation("无法发现礼包！") as ITextComponent)

    }

    override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean {
        return true
    }
}