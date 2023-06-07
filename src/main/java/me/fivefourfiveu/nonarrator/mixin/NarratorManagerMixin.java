package me.fivefourfiveu.nonarrator.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.option.NarratorMode;
import net.minecraft.client.util.NarratorManager;

@Mixin(NarratorManager.class)
public class NarratorManagerMixin {
	@Inject(method = "getNarratorMode", at = @At("HEAD"), cancellable = true)
	private void getNarrator(CallbackInfoReturnable<NarratorMode> ci) {
		ci.setReturnValue(NarratorMode.OFF);
	}
	
	@Inject(method = "onModeChange", at = @At("HEAD"), cancellable = true)
	private void onModeChange(CallbackInfo ci) {
		ci.cancel();
	}
}
