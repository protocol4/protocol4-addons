package io.github.protocol4.features.inventory.protectitem

import io.github.protocol4.config.ConfigManager
import io.github.protocol4.utils.KeyboardManager
import net.minecraft.client.Minecraft

object ProtectItemKeybind {
	private val config get() = ConfigManager.config.inventory.protectItem

	fun register() {
		KeyboardManager.onWorldKey({ config.keybind }) {
			val player = Minecraft.getInstance().player
			if (config.enabled && player != null) {
				ProtectedItems.toggleWithFeedback(player.mainHandItem, player::sendSystemMessage)
			}
		}
		KeyboardManager.onSlotKey({ config.keybind }) { slot ->
			val player = Minecraft.getInstance().player
			val stack = slot.item
			if (!config.enabled || player == null || stack.isEmpty) {
				false
			} else {
				ProtectedItems.toggleWithFeedback(stack, player::sendSystemMessage)
				true
			}
		}
	}
}
