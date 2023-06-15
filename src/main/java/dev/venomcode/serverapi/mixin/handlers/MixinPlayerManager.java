package dev.venomcode.serverapi.mixin.handlers;

import dev.venomcode.serverapi.data.SAPIData;
import net.minecraft.network.ClientConnection;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
abstract class MixinPlayerManager {
    @Shadow @Final private MinecraftServer server;

    @Inject(method = "onPlayerConnect", at = @At("RETURN"))
    void injectOnPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci)
    {
        SAPIData data = SAPIData.getState(server);
        if(data.isSpawnSet())
        {
            BlockPos blkPos = new BlockPos((int)Math.round(data.getSpawnPos().x), (int)Math.round(data.getSpawnPos().y), (int)Math.round(data.getSpawnPos().z));
            player.setSpawnPoint(RegistryKey.of(RegistryKeys.WORLD, data.getSpawnDimension()), blkPos, 0, true, false);
        }
    }
}
