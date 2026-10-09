package io.github.protocol4.features.inventory.protectitem

import com.google.gson.Gson
import io.github.protocol4.Protocol4Addons
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.ChatFormatting
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import java.nio.file.Files

object ProtectedItems {
	private val gson = Gson()
	private val file = FabricLoader.getInstance().configDir.resolve("protocol4-addons/protected-items.json")
	private val uuids = mutableSetOf<String>()

	fun load() {
		if (!Files.exists(file)) return
		try {
			uuids.addAll(gson.fromJson(Files.readString(file), Array<String>::class.java))
		} catch (e: Exception) {
			Protocol4Addons.LOGGER.error("Could not load protected items", e)
		}
	}

	private fun save() {
		try {
			Files.createDirectories(file.parent)
			Files.writeString(file, gson.toJson(uuids))
		} catch (e: Exception) {
			Protocol4Addons.LOGGER.error("Could not save protected items", e)
		}
	}

	fun uuidOf(stack: ItemStack): String? {
		val tag = stack.get(DataComponents.CUSTOM_DATA)?.copyTag() ?: return null
		return tag.getString("uuid").orElse(null)?.takeIf { it.isNotBlank() }
	}

	fun isProtected(stack: ItemStack): Boolean {
		val uuid = uuidOf(stack) ?: return false
		return uuid in uuids
	}

	fun toggle(uuid: String): Boolean {
		val added = uuids.add(uuid)
		if (!added) uuids.remove(uuid)
		save()
		return added
	}

	fun toggleWithFeedback(stack: ItemStack, send: (Component) -> Unit): Boolean {
		val uuid = uuidOf(stack)
		if (uuid == null) {
			send(Component.literal("That item has no SkyBlock UUID.").withStyle(ChatFormatting.RED))
			return false
		}
		val protectedNow = toggle(uuid)
		val prefix = if (protectedNow) "Protected " else "Unprotected "
		val color = if (protectedNow) ChatFormatting.GREEN else ChatFormatting.YELLOW
		send(Component.literal(prefix).withStyle(color).append(stack.hoverName))
		return true
	}
}
