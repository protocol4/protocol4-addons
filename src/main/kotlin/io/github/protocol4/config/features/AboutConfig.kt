package io.github.protocol4.config.features

import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.Accordion
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorButton
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorInfoText
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption
import net.minecraft.util.Util

private fun open(url: String) = Util.getPlatform().openUri(url)

class AboutConfig {
	@ConfigOption(
		name = "protocol4 Addons",
		desc = "§7 Quality of life features for Hypixel SkyBlock. Type §e/protocol4§7 or §e/p4§7 to open this menu."
	)
	@ConfigEditorInfoText(infoTitle = "About")
	val info: String? = null

	@ConfigOption(name = "Credits", desc = "People and projects this mod learned from")
	@Accordion
	@Expose
	val credits = Credits()

	@ConfigOption(name = "Used Software", desc = "Information about used software and licenses")
	@Accordion
	@Expose
	val software = Software()

	class Credits {
		@ConfigOption(
			name = "SkyHanni",
			desc = "§7Inspiration for the config layout, MoulConfig setup and keybind handling. By hannibal2 and contributors. LGPL-2.1"
		)
		@ConfigEditorButton(buttonText = "Source")
		val skyHanni = Runnable { open("https://github.com/hannibal002/SkyHanni") }

		@ConfigOption(
			name = "SkyOcean",
			desc = "§7 the multiversion system has stuff learnt from this mod. By Meowdding and contributors .SkyOcean License v1"
		)
		@ConfigEditorButton(buttonText = "Source")
		val skyocean = Runnable { open("https://github.com/Meowdding/SkyOcean") }
	}

	class Software {
		@ConfigOption(name = "MoulConfig", desc = "MoulConfig is available under the LGPL 3.0 License or later version")
		@ConfigEditorButton(buttonText = "Source")
		val moulConfig = Runnable { open("https://github.com/NotEnoughUpdates/MoulConfig") }

		@ConfigOption(name = "Fabric Loader", desc = "Fabric Loader is available under the Apache-2.0 license")
		@ConfigEditorButton(buttonText = "Source")
		val fabricLoader = Runnable { open("https://github.com/FabricMC/fabric-loader") }

		@ConfigOption(name = "Fabric API", desc = "Fabric API is available under the Apache-2.0 license")
		@ConfigEditorButton(buttonText = "Source")
		val fabricApi = Runnable { open("https://github.com/FabricMC/fabric-api") }

		@ConfigOption(name = "Fabric Language Kotlin", desc = "Fabric Language Kotlin is available under the Apache-2.0 license")
		@ConfigEditorButton(buttonText = "Source")
		val fabricLanguageKotlin = Runnable { open("https://github.com/FabricMC/fabric-language-kotlin") }

		@ConfigOption(name = "Kotlin", desc = "Kotlin is available under the Apache-2.0 license")
		@ConfigEditorButton(buttonText = "Source")
		val kotlin = Runnable { open("https://github.com/JetBrains/kotlin") }

		@ConfigOption(name = "Mixin", desc = "Mixin is available under the MIT License")
		@ConfigEditorButton(buttonText = "Source")
		val mixin = Runnable { open("https://github.com/FabricMC/Mixin") }

		@ConfigOption(name = "Fabric Loom", desc = "Fabric Loom is available under the MIT License")
		@ConfigEditorButton(buttonText = "Source")
		val fabricLoom = Runnable { open("https://github.com/FabricMC/fabric-loom") }

		@ConfigOption(name = "Stonecutter", desc = "Stonecutter is available under the MPL-2.0 license")
		@ConfigEditorButton(buttonText = "Source")
		val stonecutter = Runnable { open("https://github.com/stonecutter-versioning/stonecutter") }

		@ConfigOption(name = "Shadow", desc = "Shadow is available under the Apache-2.0 license")
		@ConfigEditorButton(buttonText = "Source")
		val shadow = Runnable { open("https://github.com/GradleUp/shadow") }
	}
}