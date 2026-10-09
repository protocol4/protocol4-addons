package io.github.protocol4.config.features.inventory

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.Accordion
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class InventoryConfig {
	@ConfigOption(name = "Protect Item", desc = "Keep your valuable items from being dropped by accident.")
	@Accordion
	@Expose
	var protectItem = ProtectItemConfig()
}
