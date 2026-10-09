package io.github.protocol4.config

import com.google.gson.GsonBuilder
import io.github.notenoughupdates.moulconfig.gui.GuiContext
import io.github.notenoughupdates.moulconfig.gui.MoulConfigEditor
import io.github.notenoughupdates.moulconfig.platform.MoulConfigScreenComponent
import io.github.notenoughupdates.moulconfig.processor.BuiltinMoulConfigGuis
import io.github.notenoughupdates.moulconfig.processor.ConfigProcessorDriver
import io.github.notenoughupdates.moulconfig.processor.MoulConfigProcessor
import io.github.protocol4.Protocol4Addons
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.nio.file.Files

object ConfigManager {
	private val gson = GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create()
	private val file = FabricLoader.getInstance().configDir.resolve("protocol4-addons/config.json")
	private var screenToOpen: Screen? = null

	var config = Protocol4Config()
		private set

	fun load() {
		if (Files.exists(file)) {
			try {
				config = gson.fromJson(Files.readString(file), Protocol4Config::class.java) ?: config
			} catch (e: Exception) {
				Protocol4Addons.LOGGER.error("Could not load config", e)
			}
		}
		save()
		ClientTickEvents.END_CLIENT_TICK.register { client ->
			val screen = screenToOpen ?: return@register
			screenToOpen = null
			client.gui.setScreen(screen)
		}
	}

	fun save() {
		try {
			Files.createDirectories(file.parent)
			Files.writeString(file, gson.toJson(config))
		} catch (e: Exception) {
			Protocol4Addons.LOGGER.error("Could not save config", e)
		}
	}

	fun openGui() {
		val processor = MoulConfigProcessor(config)
		BuiltinMoulConfigGuis.addProcessors(processor)
		val driver = ConfigProcessorDriver(processor)
		driver.warnForPrivateFields = false
		driver.processConfig(config)
		val editor = MoulConfigEditor(processor)
		screenToOpen = MoulConfigScreenComponent(Component.empty(), GuiContext(MoulConfigEditorComponent(editor)), null)
	}
}
