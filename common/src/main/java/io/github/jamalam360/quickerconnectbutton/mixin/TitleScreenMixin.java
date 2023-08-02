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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

	protected TitleScreenMixin(Component $$0) {
		super($$0);
	}

	@Inject(
			method = "createNormalMenuOptions",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 1),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	private void init(int a, int b, CallbackInfo info, Component multiplayerDisabledReason, boolean multiplayerDisabled, Tooltip multiplayerDisabledTooltip) {
		if (QuickerConnectButton.config.ip() != null && !QuickerConnectButton.config.ip().equals("")) {
			this.addRenderableWidget(Button.builder(Component.translatable("quickerconnectbutton.menu.connect"), button -> {
				ServerData data = new ServerData(I18n.get("selectServer.defaultName"), QuickerConnectButton.config.ip(), false);
				ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(data.ip), data, false);
			}).bounds(this.width / 2 + 104, a + b, 50, 20).tooltip(multiplayerDisabledTooltip).build()).active = multiplayerDisabled;
		}
	}
}
