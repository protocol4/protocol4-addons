package io.github.protocol4.mixin;

import io.github.protocol4.utils.KeyboardManager;
import io.github.protocol4.features.inventory.protectitem.ProtectionGuard;
import io.github.protocol4.features.inventory.protectitem.StarOverlay;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

	@Shadow
	protected Slot hoveredSlot;

	@Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
	private void toggleProtectionKey(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
		if (KeyboardManager.handleSlotKey(event, hoveredSlot)) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "slotClicked", at = @At("HEAD"), cancellable = true)
	private void blockProtectedThrow(Slot slot, int slotId, int button, ContainerInput input, CallbackInfo ci) {
		AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) (Object) this;
		boolean clickedOutside = slotId == -999 && input == ContainerInput.PICKUP;
		if (clickedOutside && ProtectionGuard.blockCursorDrop(screen.getMenu().getCarried())) {
			ci.cancel();
		} else if (input == ContainerInput.THROW && slot != null && ProtectionGuard.blockInventoryThrow(slot.getItem())) {
			ci.cancel();
		}
	}

	@Inject(method = "extractSlot", at = @At("RETURN"))
	private void drawProtectedStar(GuiGraphicsExtractor graphics, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
		StarOverlay.draw(graphics, slot);
	}
}
