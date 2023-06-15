package dev.venomcode.serverapi.mixin.handlers;

import dev.venomcode.serverapi.api.gui.SAPI_SLOT_CLICK;
import dev.venomcode.serverapi.api.gui.ServerScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class MixinScreenHandler {
    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    void injectOnSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if(player.getEntityWorld().isClient)
            return;

        if(player.currentScreenHandler instanceof ServerScreenHandler serverScreenHandler) {

            if(serverScreenHandler.getSlot(slotIndex).inventory == player.getInventory())
                return;

            ClickType clickType = button == 0 ? ClickType.LEFT : ClickType.RIGHT;

            SAPI_SLOT_CLICK clickResult = serverScreenHandler.handleClickedSlot(slotIndex, clickType, actionType, (ServerPlayerEntity) player);
            if(clickResult == SAPI_SLOT_CLICK.DENY_CLICK)
            {
                ci.cancel();
            }
            // ELSE: Pass it through as normal
        }
    }
}
