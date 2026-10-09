package io.github.protocol4.utils

import com.mojang.blaze3d.platform.InputConstants
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.input.KeyEvent
import net.minecraft.world.inventory.Slot

object KeyboardManager {
	private class WorldBind(val key: () -> Int, val action: () -> Unit) {
		var wasDown = false
	}

	private class SlotBind(val key: () -> Int, val action: (Slot) -> Boolean)

	private val worldBinds = mutableListOf<WorldBind>()
	private val slotBinds = mutableListOf<SlotBind>()

	fun register() {
		ClientTickEvents.END_CLIENT_TICK.register { client ->
			val noScreen = client.gui.screen() == null
			for (bind in worldBinds) {
				val down = noScreen && bind.key().isKeyHeld()
				val pressed = down && !bind.wasDown
				bind.wasDown = down
				if (pressed) bind.action()
			}
		}
	}

	fun onWorldKey(key: () -> Int, action: () -> Unit) {
		worldBinds.add(WorldBind(key, action))
	}

	fun onSlotKey(key: () -> Int, action: (Slot) -> Boolean) {
		slotBinds.add(SlotBind(key, action))
	}

	@JvmStatic
	fun handleSlotKey(event: KeyEvent, hovered: Slot?): Boolean {
		if (hovered == null) return false
		if (Minecraft.getInstance().gui.screen()?.focused is EditBox) return false
		for (bind in slotBinds) {
			val code = bind.key()
			if (code > 5 && event.key() == code && bind.action(hovered)) return true
		}
		return false
	}

	fun Int.isKeyHeld(): Boolean {
		if (this <= 5) return false
		return InputConstants.isKeyDown(Minecraft.getInstance().window, this)
	}
}
