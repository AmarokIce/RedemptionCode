package club.someoneice.lottery.command

import club.someoneice.lottery.data.DataSet
import club.someoneice.lottery.data.inner.ItemList
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentTranslation
import net.minecraftforge.fml.common.registry.ForgeRegistries

class Prize: CommandBase() {
    override fun getName(): String {
        return "prize"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "/prize"
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, list: Array<String>) {
        val player: EntityPlayerMP = sender.commandSenderEntity as EntityPlayerMP
        val item: ItemStack? = player.inventory.getCurrentItem()
        if (item != null) {
            var i = 0
            while(true) {
                if (!DataSet.itemList.containsKey(i)) {
                    DataSet.itemList[i] = ItemList(ForgeRegistries.ITEMS.getKey(item.item).toString(), item.count, item.itemDamage)
                    break
                } else if (i > 27){
                    player.sendMessage(TextComponentTranslation("你添加了太多物品！"))
                    break
                } else i ++
            }
            player.sendMessage(TextComponentTranslation("你添加了物品： ${item.displayName}") as ITextComponent)
        }
    }
}