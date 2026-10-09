package io.github.protocol4

import io.github.protocol4.commands.Commands
import io.github.protocol4.config.ConfigManager
import net.fabricmc.api.ClientModInitializer
import io.github.protocol4.features.inventory.protectitem.ProtectedItems

import net.minecraft.resources.Identifier
import org.slf4j.LoggerFactory

object Protocol4Addons : ClientModInitializer {
	const val MOD_ID = "protocol4-addons"
	val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitializeClient() {
		ProtectedItems.load()
		ConfigManager.load()
		Commands.register()
	}

	fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, path)
}
