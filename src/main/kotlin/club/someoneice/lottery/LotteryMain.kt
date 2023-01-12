package club.someoneice.lottery

import club.someoneice.lottery.command.CommandRegistry
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.server.ServerAboutToStartEvent
import net.minecraftforge.event.server.ServerStoppingEvent
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.FORGE_BUS

@Mod(LotteryMain.ID)
object LotteryMain {
    const val ID: String = "lottery"

    val LOGGER: Logger = LogManager.getLogger("lottery")

    init {
        FORGE_BUS.addListener(::onServerAboutToStart)
        FORGE_BUS.addListener(::onServerStopping)
    }

    private fun onServerAboutToStart(event: ServerAboutToStartEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
        JsonHelper.reader()

        MinecraftForge.EVENT_BUS.register(CommandRegistry)
    }

    private fun onServerStopping(event: ServerStoppingEvent) {
        LOGGER.log(Level.INFO, "Server stopping...")
        JsonHelper.writer()

    }
}