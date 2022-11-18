package dev.venomcode.serverapi.mixin;

import dev.venomcode.serverapi.api.events.PlayerEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
abstract class EntityMixin {
    @Inject(method = "setSneaking", at = @At("HEAD"), cancellable = true)
    void hookSetSneaking(boolean sneaking, CallbackInfo ci) {
        if ((LivingEntity)(Object)this instanceof ServerPlayerEntity) {
            if(PlayerEvents.SNEAK.invoker().allowSneak((ServerPlayerEntity)(Object)this))
            {
                ci.cancel();
            }
        }
    }
}
