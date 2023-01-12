package club.someoneice.lottery.command

import club.someoneice.lottery.DataSet
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.util.IChatComponent

class Pool: CommandBase() {
    override fun getCommandName(): String {
        return "pool"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/pool"
    }

    override fun processCommand(sender: ICommandSender, list: Array<String>) {
        sender.addChatMessage(ChatComponentTranslation(DataSet.itemList.toString()) as IChatComponent)
    }
}