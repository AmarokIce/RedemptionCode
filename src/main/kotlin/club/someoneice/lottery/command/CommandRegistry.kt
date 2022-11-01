package club.someoneice.lottery.command

import club.someoneice.lottery.LotteryMain
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.server.ServerStartedEvent
import net.minecraftforge.event.server.ServerStoppedEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(modid = LotteryMain.ID, bus = EventBusSubscriber.Bus.FORGE)
object CommandRegistry {
    private var init = false
    private fun register(event: CommandDispatcher<CommandSourceStack>) {
        val cmd = CommandHelper()
        cmd.PRIZE(event)
        cmd.PRIZEPOOL(event)
        cmd.CASH(event)
        cmd.POOL(event)
    }

    @SubscribeEvent
    fun register(event: ServerStartedEvent) {
        if (!init) {
            register(event.server.commands.dispatcher)
            init = true
        }
    }

    @SubscribeEvent
    fun register(event: RegisterCommandsEvent) {
        if (init) register(event.dispatcher)
    }

    @SubscribeEvent
    fun onServerStopped(event: ServerStoppedEvent?) {
        init = false
    }
}