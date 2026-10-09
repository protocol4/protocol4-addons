package io.github.protocol4.features.inventory.protectitem

import io.github.protocol4.Protocol4Addons
import io.github.protocol4.config.ConfigManager
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

object StarOverlay {
	private val sprite = Protocol4Addons.id("protected_item")

	@JvmStatic
	fun draw(graphics: GuiGraphicsExtractor, slot: Slot) {
		render(graphics, slot.x, slot.y, slot.item)
	}

	@JvmStatic
	fun drawHotbar(graphics: GuiGraphicsExtractor, x: Int, y: Int, stack: ItemStack) {
		val star = ConfigManager.config.inventory.protectItem.star
		if (!star.showOnHotbar) return
		render(graphics, x, y, stack)
	}

	private fun render(graphics: GuiGraphicsExtractor, x: Int, y: Int, stack: ItemStack) {
		val config = ConfigManager.config.inventory.protectItem
		val star = config.star
		if (!config.enabled || !star.enabled || star.opacity == 0) return
		if (!ProtectedItems.isProtected(stack)) return
		val color = (star.opacity * 255 / 100 shl 24) or 0xFFFFFF
		graphics.blitSprite(
			RenderPipelines.GUI_TEXTURED, sprite,
			x + star.offsetX, y + star.offsetY,
			star.size, star.size, color
		)
	}
}
