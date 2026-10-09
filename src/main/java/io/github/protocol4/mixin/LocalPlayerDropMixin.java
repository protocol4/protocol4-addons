package io.github.protocol4.mixin;

import io.github.protocol4.features.inventory.protectitem.ProtectionGuard;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerDropMixin {

	@Inject(method = "drop", at = @At("HEAD"), cancellable = true)
	private void blockProtectedDrop(boolean all, CallbackInfoReturnable<Boolean> cir) {
		Inventory inventory = ((LocalPlayer) (Object) this).getInventory();
		if (ProtectionGuard.blockHotbarDrop(inventory.getItem(inventory.getSelectedSlot()))) {
			cir.setReturnValue(false);
		}
	}
}
