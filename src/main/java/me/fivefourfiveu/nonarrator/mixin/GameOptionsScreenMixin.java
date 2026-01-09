package me.fivefourfiveu.nonarrator.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.ChatOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.text.Text;

@Mixin(GameOptionsScreen.class)
public class GameOptionsScreenMixin {
	@Unique private Tooltip tooltip = Tooltip.of(Text.of("Narrator disabled by the \"NoNarrator\" mod."));
	@Shadow private OptionListWidget body;
	
	@Inject(method = "init", at = @At("TAIL"))
	private void init(CallbackInfo ci) {
		// Check that this is the accessibility screen or the chat options screen.
		if(!((Object)this instanceof AccessibilityOptionsScreen) && !((Object)this instanceof ChatOptionsScreen))
			return;
		
		// Add a tooltip to the narrator toggle widget.
		MinecraftClient mc = MinecraftClient.getInstance();
		ClickableWidget narratorWidget = body.getWidgetFor(mc.options.getNarrator());
		if(narratorWidget != null) narratorWidget.setTooltip(tooltip);
		
		// Add a tooltip to the narrator hotkey widget and disable it.
		ClickableWidget hotkeyWidget = body.getWidgetFor(mc.options.getNarratorHotkey());
		if(hotkeyWidget != null) {
			hotkeyWidget.setTooltip(tooltip);
			hotkeyWidget.active = false;
		}
	}
}