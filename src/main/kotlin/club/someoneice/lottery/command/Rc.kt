package club.someoneice.lottery.command

import club.someoneice.lottery.json.JsonReader
import club.someoneice.lottery.json.JsonWriter
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentTranslation

class Rc: CommandBase() {
    override fun getCommandName(): String {
        return "rc"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/rc [load / write]"
    }

    override fun processCommand(sender: ICommandSender, list: Array<String>) {
        if (list[0] == "load") {
            JsonReader.reader(true)
            sender.addChatMessage(ChatComponentTranslation("Done!"))
        } else if (list[0] == "write") {
            JsonWriter.writer()
            sender.addChatMessage(ChatComponentTranslation("Done!"))
        } else sender.addChatMessage(ChatComponentTranslation("/rc load / write"))
    }
}