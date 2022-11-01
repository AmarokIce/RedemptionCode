package club.someoneice.lottery.helper

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries
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

    fun setSystemClipboardText(str: String?) {
        val clip: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val text: Transferable = StringSelection(str)
        clip.setContents(text, null)
    }

    fun getItemFromString(ItemStr: String): Item? {
        val itemKey = ResourceLocation(ItemStr)

        return if (ForgeRegistries.ITEMS.containsKey(itemKey))
            ForgeRegistries.ITEMS.getValue(itemKey)!!
        else if (ForgeRegistries.BLOCKS.containsKey(itemKey))
            ForgeRegistries.BLOCKS.getValue(itemKey)!!.asItem()
        else null
    }
}