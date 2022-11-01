package club.someoneice.lottery.command

import club.someoneice.lottery.data.DataSet
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentTranslation

class Pool: CommandBase() {
    override fun getName(): String {
        return "pool"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "/pool"
    }

    override fun execute(mc: MinecraftServer, sender: ICommandSender, list: Array<String>) {
        sender.sendMessage(TextComponentTranslation(DataSet.itemList.toString()) as ITextComponent)
    }
}