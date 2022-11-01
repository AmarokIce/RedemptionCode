package club.someoneice.lottery.command

import club.someoneice.lottery.data.DataSet
import club.someoneice.lottery.data.inner.ItemList
import club.someoneice.lottery.data.inner.LotteryData
import club.someoneice.lottery.helper.Fixed
import club.someoneice.lottery.helper.Util
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.argument
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.registries.ForgeRegistries

@EventBusSubscriber
class CommandHelper {

    fun PRIZEPOOL(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("prizepool")
                .then(argument("name", StringArgumentType.string())
                .then(argument("code", StringArgumentType.string())
                    .executes { lottery ->
                        if (StringArgumentType.getString(lottery, "name") != null) {
                            val name = StringArgumentType.getString(lottery, "name")
                            val code: String = if (StringArgumentType.getString(lottery, "code") == "null")
                                Util.getRandomCode(7)
                            else
                                StringArgumentType.getString(lottery, "code")

                            DataSet.gift[name] = LotteryData(name, code, DataSet.itemList, ArrayList())

                            Fixed.FixCommandHashMapInPut(name)

                            lottery.source.sendSuccess(Component.translatable("Success create $name ! Code: $code"), false)

                            Util.setSystemClipboardText(code)
                            DataSet.itemList.clear()
                        }

                        0
                    }
                )
            )
        )
    }

    fun PRIZE(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("prize").executes { lottery ->
                val player: ServerPlayer = lottery.source.playerOrException as ServerPlayer
                val getItem: ItemStack = player.mainHandItem as ItemStack

                val itemName: String = ForgeRegistries.ITEMS.getKey(getItem.item)!!.toString()
                for (i in 0 .. 27) {
                    if (i == 27) {
                        lottery.source.sendFailure(Component.translatable("Too much item in pool!"))
                    }

                    if (!DataSet.itemList.contains(i)) {
                        DataSet.itemList[i] = ItemList(itemName, getItem.count)
                        lottery.source.sendSuccess(Component.translatable("Success add ${itemName}!"), false)
                        break
                    }
                }

                0
            }
        )
    }

    fun CASH(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("cash")
                .then(argument("code", StringArgumentType.string())
                    .executes { lottery ->
                        val player: ServerPlayer = lottery.source.playerOrException as ServerPlayer
                        val iterator: Iterator<String> = DataSet.gift.keys.iterator()
                        val code = StringArgumentType.getString(lottery, "code")
                        while (iterator.hasNext()) {
                            val it = iterator.next()
                            if (code == DataSet.gift[it]!!.code) {
                                if (DataSet.gift[it]!!.player.contains(player.scoreboardName)) {
                                    lottery.source.sendFailure(Component.translatable("You get redemption this code!"))
                                    return@executes 0
                                } else {
                                    lottery.source.sendSuccess(Component.translatable("You get redemption ${DataSet.gift[it]!!.name} !"), false)
                                    DataSet.gift[it]!!.player.add(player.scoreboardName)
                                    for (item in 0 .. DataSet.gift[it]!!.ItemList.size) {
                                        if (item == DataSet.gift[it]!!.ItemList.size) return@executes 0
                                        val ItemName: String = DataSet.gift[it]!!.ItemList[item]!!.itemName
                                        val ItemNumber: Int = DataSet.gift[it]!!.ItemList[item]!!.itemNumber

                                        val GetItem: Item? = Util.getItemFromString(ItemName)
                                        if (GetItem != null) {
                                            player.inventory.add(ItemStack(GetItem, ItemNumber))
                                        }
                                    }
                                }
                            }
                        }

                        0
                    }
                )
        )
    }

    fun POOL(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("pool")
                .executes { lottery ->

                    lottery.source.sendSuccess(Component.translatable(DataSet.itemList.toString()), false)

                    0
                }

        )
    }
}