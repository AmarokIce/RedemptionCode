package club.someoneice.lottery.command

import club.someoneice.lottery.DataSet
import club.someoneice.lottery.Fixed
import club.someoneice.lottery.JsonHelper
import club.someoneice.lottery.Util
import club.someoneice.lottery.data.ItemList
import club.someoneice.lottery.data.LotteryData
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
                    .executes {
                        if (StringArgumentType.getString(it, "name") != null) {
                            val name = StringArgumentType.getString(it, "name")
                            val code: String = if (StringArgumentType.getString(it, "code") == "null") {
                                Util.getRandomCode(7)
                            } else {
                                StringArgumentType.getString(it, "code")
                            }

                            DataSet.gift[code] = LotteryData(name, code, DataSet.itemList, ArrayList())
                            DataSet.codeList.add(code)
                            Fixed.FixCommandHashMapInPut(name)

                            it.source.sendSuccess(Component.translatable("Success create $name ! Code: $code"), false)

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
            Commands.literal("prize").executes {
                val player: ServerPlayer = it.source.playerOrException as ServerPlayer
                val getItem: ItemStack = player.mainHandItem as ItemStack

                val itemName: String = ForgeRegistries.ITEMS.getKey(getItem.item)!!.toString()
                for (i in 0 .. 27) {
                    if (i == 27) {
                        it.source.sendFailure(Component.translatable("Too much item in pool!"))
                    }

                    if (!DataSet.itemList.contains(i)) {
                        DataSet.itemList[i] = ItemList(itemName, getItem.count)
                        it.source.sendSuccess(Component.translatable("Success add ${itemName}!"), false)
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
                    .executes {
                        val player: ServerPlayer = it.source.playerOrException as ServerPlayer
                        val code = StringArgumentType.getString(it, "code")
                        for (i in DataSet.codeList) {
                            if (code == i) {
                                if (DataSet.gift[i]!!.player.contains(player.scoreboardName)) {
                                    it.source.sendFailure(Component.translatable
("You get redemption this code!"))
                                    return@executes 0
                                } else {
                                    it.source.sendSuccess(Component.translatable
("You get redemption ${DataSet.gift[i]!!.name} !"), false)
                                    DataSet.gift[i]!!.player.add(player.scoreboardName)
                                    for (item in 0 .. DataSet.gift[i]!!.ItemList.size) {
                                        if (item == DataSet.gift[i]!!.ItemList.size) return@executes 0
                                        val ItemName: String = DataSet.gift[i]!!.ItemList[item]!!.itemName
                                        val ItemNumber: Int = DataSet.gift[i]!!.ItemList[item]!!.itemNumber

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
                .executes {
                    it.source.sendSuccess(Component.translatable(DataSet.itemList.toString()), false)

                    0
                }

        )
    }

    fun RC(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("rc").then(argument("command", StringArgumentType.string())).executes {
                if (StringArgumentType.getString(it, "code") == "load") {
                    JsonHelper.reader(true)
                } else if (StringArgumentType.getString(it, "code") == "write") {
                    JsonHelper.writer()
                }

                0
            }
        )
    }
}