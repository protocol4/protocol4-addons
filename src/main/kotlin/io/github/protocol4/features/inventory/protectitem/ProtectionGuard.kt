package io.github.protocol4.features.inventory.protectitem

import io.github.protocol4.config.ConfigManager
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.ItemStack

object ProtectionGuard {
	private var lastWarning = 0L

	private val config get() = ConfigManager.config.inventory.protectItem

	@JvmStatic
	fun blockHotbarDrop(stack: ItemStack) = check(stack)

	@JvmStatic
	fun blockInventoryThrow(stack: ItemStack) = check(stack)

	@JvmStatic
	fun blockCursorDrop(stack: ItemStack) = check(stack)

	private fun check(stack: ItemStack): Boolean {
		if (!config.enabled || !ProtectedItems.isProtected(stack)) return false
		warn(stack)
		return true
	}

	private fun warn(stack: ItemStack) {
		val now = System.currentTimeMillis()
		if (now - lastWarning < config.warningDelay) return
		lastWarning = now
		val minecraft = Minecraft.getInstance()
		if (config.warningMessage) {
			val message = Component.literal("Couldnt drop ").append(stack.hoverName).append(" because it is protected.")
			minecraft.player?.sendSystemMessage(message.withStyle(config.warningColor.formatting))
		}
		if (config.warningSound) {
			val sound = SoundEvent.createVariableRangeEvent(Identifier.parse(config.soundType.id))
			minecraft.soundManager.play(SimpleSoundInstance.forUI(sound, config.soundPitch, config.soundVolume))
		}
	}
}
