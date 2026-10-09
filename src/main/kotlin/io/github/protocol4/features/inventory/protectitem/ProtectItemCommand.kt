package io.github.protocol4.features.inventory.protectitem

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component

object ProtectItemCommand {
	fun build(): LiteralArgumentBuilder<FabricClientCommandSource> =
		LiteralArgumentBuilder.literal<FabricClientCommandSource>("protectitem").executes { context ->
			val stack = Minecraft.getInstance().player?.mainHandItem
			val uuid = stack?.let { ProtectedItems.uuidOf(it) }
			if (stack == null || uuid == null) {
				val error = Component.literal("Hold an item with a SkyBlock UUID.").withStyle(ChatFormatting.RED)
				context.source.sendFeedback(error)
				return@executes 0
			}
			val protectedNow = ProtectedItems.toggle(uuid)
			val prefix = if (protectedNow) "Protected " else "Unprotected "
			val color = if (protectedNow) ChatFormatting.GREEN else ChatFormatting.YELLOW
			context.source.sendFeedback(Component.literal(prefix).withStyle(color).append(stack.hoverName))
			1
		}
}
