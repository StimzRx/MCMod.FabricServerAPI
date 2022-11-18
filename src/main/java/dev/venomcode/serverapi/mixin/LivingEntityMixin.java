package dev.venomcode.serverapi.mixin;

import dev.venomcode.serverapi.api.events.PlayerEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
    @Inject(method = "jump", at=@At("HEAD"), cancellable = true)
    void hookJump(CallbackInfo ci)
    {
        if ((LivingEntity)(Object)this instanceof ServerPlayerEntity) {
            if(PlayerEvents.JUMP.invoker().allowJump((ServerPlayerEntity)(Object)this))
            {
                ci.cancel();
            }
        }
    }
}
