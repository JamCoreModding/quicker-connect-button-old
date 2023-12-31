package io.github.jamalam360.quickerconnectbutton.mixin;

import io.github.jamalam360.quickerconnectbutton.QuickerConnectButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Component $$0) {
		super($$0);
	}

	@Inject(
			method = "createNormalMenuOptions",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 1, shift = At.Shift.BEFORE),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void quickerconnectbutton$injectCustomButton(int a, int b, CallbackInfo info, Component multiplayerDisabledReason, boolean multiplayerDisabled, Tooltip multiplayerDisabledTooltip) {
		if (QuickerConnectButton.enabled() && !QuickerConnectButton.config.replaceMultiplayerButton()) {
			Component text = QuickerConnectButton.getButtonText();
			int width = Mth.clamp(this.minecraft.font.width(text), 50, this.width - (this.width / 2 + 108));

			this.addRenderableWidget(Button.builder(text, button -> {
				ServerData data = QuickerConnectButton.createServerData();
				ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(data.ip), data, false);
			}).bounds(this.width / 2 + 104, a + b, width, 20).tooltip(multiplayerDisabledTooltip).build()).active = multiplayerDisabled;
		}
	}

	@ModifyArgs(
			method = "createNormalMenuOptions",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;builder(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)Lnet/minecraft/client/gui/components/Button$Builder;", ordinal = 1)
	)
	private void quickerconnectbutton$modifyMultiplayerButton(Args args) {
		if (QuickerConnectButton.config.replaceMultiplayerButton()) {
			args.set(0, QuickerConnectButton.getButtonText());
			args.<Button.OnPress>set(1, (button) -> {
				ServerData data = QuickerConnectButton.createServerData();
				ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(data.ip), data, false);
			});
		}
	}
}
