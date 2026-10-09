package io.github.protocol4.config.features.inventory

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class StarOverlayConfig {
	@Expose
	@ConfigOption(name = "Show Star", desc = "Draws a star on protected items.")
	@ConfigEditorBoolean
	var enabled = true

	@Expose
	@ConfigOption(name = "Opacity", desc = "How see through the star is, in percent.")
	@ConfigEditorSlider(minValue = 0f, maxValue = 100f, minStep = 1f)
	var opacity = 100

	@Expose
	@ConfigOption(name = "Size", desc = "Size of the star in pixels.")
	@ConfigEditorSlider(minValue = 4f, maxValue = 32f, minStep = 1f)
	var size = 16

	@Expose
	@ConfigOption(name = "Offset X", desc = "Moves the star to the left or right.")
	@ConfigEditorSlider(minValue = -16f, maxValue = 16f, minStep = 1f)
	var offsetX = 0

	@Expose
	@ConfigOption(name = "Offset Y", desc = "Moves the star up or down.")
	@ConfigEditorSlider(minValue = -16f, maxValue = 16f, minStep = 1f)
	var offsetY = 0

	@Expose
	@ConfigOption(name = "Show On Hotbar", desc = "Also draws the star on protected items in your hotbar while no menu is open.")
	@ConfigEditorBoolean
	var showOnHotbar = true
}
