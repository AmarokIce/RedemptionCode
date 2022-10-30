package club.someoneice.lottery

import club.someoneice.lottery.command.Cash
import club.someoneice.lottery.command.Pool
import club.someoneice.lottery.command.Prize
import club.someoneice.lottery.command.Prizepool
import club.someoneice.lottery.json.JsonReader
import club.someoneice.lottery.json.JsonSandman
import club.someoneice.lottery.json.JsonWriter
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import cpw.mods.fml.common.event.FMLServerStoppingEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(modid = LotteryMain.modid, version = LotteryMain.version, dependencies = "required-after:legacymckotlin")
class LotteryMain {
    companion object {
        const val modid: String = "lottery"
        const val version: String = "1.0.1"
        val log: Logger = LogManager.getLogger(modid)
    }

    @Mod.Instance
    var instance: LotteryMain? = null

    @EventHandler
    fun init(event: FMLInitializationEvent) {

    }

    @EventHandler
    fun perInit(event: FMLPreInitializationEvent) {

    }

    @EventHandler
    fun serverStarting(event: FMLServerStartingEvent) {
        JsonReader.reader()

        event.registerServerCommand(Prize())
        event.registerServerCommand(Prizepool())
        event.registerServerCommand(Cash())
        event.registerServerCommand(Pool())
    }

    @EventHandler
    fun serverStarting(event: FMLServerStoppingEvent) {
        JsonWriter.writer()
    }
}