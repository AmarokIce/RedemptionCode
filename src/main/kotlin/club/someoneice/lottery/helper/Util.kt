package club.someoneice.lottery.helper

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.command.NumberInvalidException
import net.minecraft.item.Item
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.util.EnumChatFormatting
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable
import java.util.*


object Util {
    fun getRandomCode(size: Int): String {
        val some = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMabcdefghijklmnopqrstuvwxyz"
        val random = Random()
        val buffer = StringBuffer()
        for (i in 0..size) {
            buffer.append(some[random.nextInt(some.length)])
            if (i == 2) buffer.append("-")
        }
        return buffer.toString()
    }

    fun getModIdFromBlock(block: Block): String {
        return GameRegistry.findUniqueIdentifierFor(block).modId ?: "null"
    }

    fun getObjectFromBlock(block: Block): String {
        return GameRegistry.findUniqueIdentifierFor(block).name ?: "null"
    }

    fun getRegisterNameFromBlock(block: Block): String {
        val modId: String? = GameRegistry.findUniqueIdentifierFor(block).modId
        val blockRegistryName: String? = GameRegistry.findUniqueIdentifierFor(block).name
        return if (modId != null && blockRegistryName != null)
            "$modId.$blockRegistryName"
        else "null"
    }

    fun getModIdFromItem(item: Item): String {
        return GameRegistry.findUniqueIdentifierFor(item).modId ?: "null"
    }

    fun getObjectFromItem(item: Item): String {
        return GameRegistry.findUniqueIdentifierFor(item).name ?: "null"
    }

    fun getRegisterNameFromItem(item: Item): String {
        val modId: String? = GameRegistry.findUniqueIdentifierFor(item).modId
        val itemRegistryName: String? = GameRegistry.findUniqueIdentifierFor(item).name
        return if (modId != null && itemRegistryName != null)
            "$modId.$itemRegistryName"
        else "null"
    }

    fun setSystemClipboardText(str: String?) {
        val clip: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val text: Transferable = StringSelection(str)
        clip.setContents(text, null)
    }

    fun getItemByText(str: String): Item {
        var item: Item? = Item.itemRegistry.getObject(str) as Item

        if (item == null) {
            try {
                item = Item.getItemById(Integer.parseInt(str))
            } catch (_: NumberFormatException) {
            }
        }

        return item ?: throw NumberInvalidException("commands.give.notFound", str)
    }
}