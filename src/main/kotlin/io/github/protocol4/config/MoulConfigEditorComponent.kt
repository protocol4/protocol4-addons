package io.github.protocol4.config

import io.github.notenoughupdates.moulconfig.gui.CloseEventListener
import io.github.notenoughupdates.moulconfig.gui.CloseEventListener.CloseAction
import io.github.notenoughupdates.moulconfig.gui.GuiComponent
import io.github.notenoughupdates.moulconfig.gui.GuiImmediateContext
import io.github.notenoughupdates.moulconfig.gui.KeyboardEvent
import io.github.notenoughupdates.moulconfig.gui.MoulConfigEditor
import io.github.notenoughupdates.moulconfig.gui.MouseEvent

internal class MoulConfigEditorComponent(private val editor: MoulConfigEditor<*>) : GuiComponent(), CloseEventListener {
	override fun getWidth(): Int = mc.scaledWidth

	override fun getHeight(): Int = mc.scaledHeight

	override fun render(context: GuiImmediateContext) {
		editor.render()
	}

	override fun mouseEvent(mouseEvent: MouseEvent, context: GuiImmediateContext): Boolean =
		editor.mouseInput(context.mouseX, context.mouseY, mouseEvent)

	override fun keyboardEvent(event: KeyboardEvent, context: GuiImmediateContext): Boolean =
		editor.keyboardInput(event)

	override fun onBeforeClose(): CloseAction = listeners().fold(CloseAction.NO_OBJECTIONS_TO_CLOSE) { action, listener ->
		action.or(listener.onBeforeClose())
	}

	override fun onAfterClose() {
		listeners().forEach { it.onAfterClose() }
	}

	private fun listeners(): List<CloseEventListener> =
		listOf<CloseEventListener>(editor) + editor.allOptions.mapNotNull { it.editor as? CloseEventListener }
}
