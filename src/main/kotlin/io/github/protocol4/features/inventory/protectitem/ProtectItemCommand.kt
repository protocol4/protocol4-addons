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
			if (stack == null) {
				context.source.sendFeedback(Component.literal("Hold an item with a SkyBlock UUID.").withStyle(ChatFormatting.RED))
				return@executes 0
			}
			if (ProtectedItems.toggleWithFeedback(stack, context.source::sendFeedback)) 1 else 0
		}
}
