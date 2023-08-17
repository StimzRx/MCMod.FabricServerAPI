package dev.venomcode.serverapi.mixin.entity;

import dev.venomcode.serverapi.api.event.SAPIPlayerEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
abstract class MixinEntity {
    @Inject(method = "setSneaking", at = @At("HEAD"), cancellable = true)
    void hookSetSneaking(boolean sneaking, CallbackInfo ci) {
        if ((LivingEntity)(Object)this instanceof ServerPlayerEntity) {
            if(sneaking) {
                if(!SAPIPlayerEvents.SNEAK.invoker().allowSneak((ServerPlayerEntity)(Object)this)) {
                    ci.cancel();
                }
            }
        }
    }
}