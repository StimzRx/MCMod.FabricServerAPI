package dev.venomcode.serverapi.mixin.entity;

import dev.venomcode.serverapi.api.event.SAPIPlayerEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
abstract class MixinLivingEntity {
    @Inject(method = "jump", at=@At("HEAD"), cancellable = true)
    void hookJump(CallbackInfo ci)
    {
        if ((LivingEntity)(Object)this instanceof ServerPlayerEntity) {
            if(!SAPIPlayerEvents.JUMP.invoker().allowJump((ServerPlayerEntity)(Object)this))
            {
                ci.cancel();
            }
        }
    }
}
