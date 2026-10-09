package io.github.protocol4.features.inventory.protectitem

import io.github.protocol4.Protocol4Addons
import io.github.protocol4.config.ConfigManager
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.Slot

object StarOverlay {
	private val sprite = Protocol4Addons.id("protected_item")

	@JvmStatic
	fun draw(graphics: GuiGraphicsExtractor, slot: Slot) {
		val config = ConfigManager.config.inventory.protectItem
		val star = config.star
		if (!config.enabled || !star.enabled || star.opacity == 0) return
		if (!star.showInContainers && slot.container !is Inventory) return
		if (!ProtectedItems.isProtected(slot.item)) return
		val color = (star.opacity * 255 / 100 shl 24) or 0xFFFFFF
		graphics.blitSprite(
			RenderPipelines.GUI_TEXTURED, sprite,
			slot.x + star.offsetX, slot.y + star.offsetY,
			star.size, star.size, color
		)
	}
}
