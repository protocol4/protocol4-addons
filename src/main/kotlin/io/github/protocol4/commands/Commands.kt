package io.github.protocol4.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.github.protocol4.config.ConfigManager
import io.github.protocol4.features.inventory.protectitem.ProtectItemCommand
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource

object Commands {
	fun register() {
		ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
			dispatcher.register(ProtectItemCommand.build())
			for (name in listOf("protocol4", "p4")) {
				dispatcher.register(LiteralArgumentBuilder.literal<FabricClientCommandSource>(name).executes {
					ConfigManager.openGui()
					1
				})
			}
		}
	}
}
