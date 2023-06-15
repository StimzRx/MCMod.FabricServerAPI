package dev.venomcode.serverapi.mixin.handlers;

import dev.venomcode.serverapi.ServerAPIMod;
import dev.venomcode.serverapi.api.ServerUtils;
import dev.venomcode.serverapi.config.ServerAPIConfig;
import dev.venomcode.serverapi.gui.menus.MainMenuScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPlayerAction", at = @At("HEAD"), cancellable = true)
    void injectOnPlayerAction(PlayerActionC2SPacket packet, CallbackInfo ci) {
        if(packet.getAction() == PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND && player.isSneaking())
        {
            if(!ServerAPIMod.getConfig().getHotkeyEnabled())
                return;

            ci.cancel();

            player.openHandledScreen(new NamedScreenHandlerFactory() {
                @Nullable
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new MainMenuScreen(syncId, playerInventory);
                }

                @Override
                public Text getDisplayName() {
                    return ServerUtils.getText("Main Menu", Formatting.YELLOW);
                }
            });
        }
    }
}
