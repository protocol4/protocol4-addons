package io.github.protocol4.config

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.Config
import io.github.notenoughupdates.moulconfig.annotations.Category
import io.github.notenoughupdates.moulconfig.common.text.StructuredText
import io.github.protocol4.Protocol4Addons
import io.github.protocol4.config.features.AboutConfig
import io.github.protocol4.config.features.inventory.InventoryConfig
import net.fabricmc.loader.api.FabricLoader

class Protocol4Config : Config() {
	override fun getTitle(): StructuredText {
		val version = FabricLoader.getInstance().getModContainer(Protocol4Addons.MOD_ID)
			.map { it.metadata.version.friendlyString }
			.orElse("")
		return StructuredText.of("Protocol4 Addons $version by §cprotocol4§r, config by §5Moulberry §rand §5nea89")
	}

	override fun saveNow() = ConfigManager.save()

	@Expose
	@Category(name = "About", desc = "Information about Protocol4 Addons and credits.")
	var about = AboutConfig()

	@Expose
	@Category(name = "Inventory", desc = "Features for your inventory and items.")
	var inventory = InventoryConfig()
}
