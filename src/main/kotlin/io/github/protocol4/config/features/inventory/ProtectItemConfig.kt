package io.github.protocol4.config.features.inventory

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.Accordion
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorDropdown
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorKeybind
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorSlider
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption
import net.minecraft.ChatFormatting
import org.lwjgl.glfw.GLFW

class ProtectItemConfig {
	@Expose
	@ConfigOption(name = "Enabled", desc = "Turns the whole protect item feature on or off.")
	@ConfigEditorBoolean
	var enabled = true

	@Expose
	@ConfigOption(name = "Keybind", desc = "Press while holding an item, or while hovering an item in any inventory, to protect or unprotect it.")
	@ConfigEditorKeybind(defaultKey = GLFW.GLFW_KEY_UNKNOWN)
	var keybind: Int = GLFW.GLFW_KEY_UNKNOWN

	@Expose
	@ConfigOption(name = "Warning Message", desc = "Shows a chat message when a protected item is saved from being dropped.")
	@ConfigEditorBoolean
	var warningMessage = true

	@Expose
	@ConfigOption(name = "Warning Color", desc = "Color of the warning message.")
	@ConfigEditorDropdown
	var warningColor = WarningColor.RED

	@Expose
	@ConfigOption(name = "Warning Delay", desc = "Minimum time in milliseconds between two warning messages.")
	@ConfigEditorSlider(minValue = 0f, maxValue = 5000f, minStep = 100f)
	var warningDelay = 1000

	@Expose
	@ConfigOption(name = "Warning Sound", desc = "Plays a sound when a protected item is saved from being dropped.")
	@ConfigEditorBoolean
	var warningSound = true

	@Expose
	@ConfigOption(name = "Sound Type", desc = "Which sound to play.")
	@ConfigEditorDropdown
	var soundType = WarningSound.ORB

	@Expose
	@ConfigOption(name = "Sound Volume", desc = "Volume of the warning sound.")
	@ConfigEditorSlider(minValue = 0f, maxValue = 1f, minStep = 0.05f)
	var soundVolume = 0.5f

	@Expose
	@ConfigOption(name = "Sound Pitch", desc = "Pitch of the warning sound.")
	@ConfigEditorSlider(minValue = 0.5f, maxValue = 2f, minStep = 0.05f)
	var soundPitch = 1f

	@Expose
	@ConfigOption(name = "Star Overlay", desc = "Settings for the star drawn on protected items.")
	@Accordion
	var star = StarOverlayConfig()

	enum class WarningColor(private val label: String, val formatting: ChatFormatting) {
		RED("Red", ChatFormatting.RED),
		GOLD("Gold", ChatFormatting.GOLD),
		YELLOW("Yellow", ChatFormatting.YELLOW),
		GREEN("Green", ChatFormatting.GREEN),
		AQUA("Aqua", ChatFormatting.AQUA),
		GRAY("Gray", ChatFormatting.GRAY),
		WHITE("White", ChatFormatting.WHITE);

		override fun toString() = label
	}

	enum class WarningSound(private val label: String, val id: String) {
		BEACON_OFF("Power Down", "block.beacon.deactivate"),
		PLING("Pling", "block.note_block.pling"),
		BELL("Bell", "block.note_block.bell"),
		BIT("Bit", "block.note_block.bit"),
		BASS("Note Block Bass", "block.note_block.bass"),
		DIDGERIDOO("Didgeridoo", "block.note_block.didgeridoo"),
		VILLAGER("Villager No", "entity.villager.no"),
		BLOCKED("Shield Block", "item.shield.block"),
		ITEM_BREAK("Item Break", "entity.item.break"),
		ORB("Experience Orb", "entity.experience_orb.pickup"),
		ANVIL("Anvil", "block.anvil.place");

		override fun toString() = label
	}
}