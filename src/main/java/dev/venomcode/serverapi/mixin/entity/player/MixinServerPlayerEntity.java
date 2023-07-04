package dev.venomcode.serverapi.mixin.entity.player;

import com.mojang.authlib.GameProfile;
import dev.venomcode.serverapi.api.ServerUtils;
import dev.venomcode.serverapi.ifaces.player.IPlayerTeleporter;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
abstract class MixinServerPlayerEntity extends PlayerEntity implements IPlayerTeleporter {

    public MixinServerPlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Shadow public abstract void sendMessage(Text message);

    @Inject( method = "tick", at = @At("HEAD"))
    void injectTick(CallbackInfo ci) {
        tickTimer++;

        if(tickTimer % 20 == 0) {
            if(tickTimer > 2000)
                tickTimer = 0;


            if(teleportWindupSeconds > 0) {
                teleportWindupSeconds--;

                if(getDistance(teleportLastMovePos, getPos()) >= 3f)
                {
                    cancelTeleport();
                    sendMessage(ServerUtils.getText("Teleportation canceled due to movement!", Formatting.RED, Formatting.ITALIC), true);
                    return;
                }

                if(teleportWindupSeconds <= 0) {

                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity)(Object)this;
                    serverPlayer.teleport(teleportTargetWorld, teleportTargetPosition.x, teleportTargetPosition.y, teleportTargetPosition.z, 0f, 0f);

                    sendMessage(ServerUtils.getText("Teleported!", Formatting.GREEN, Formatting.ITALIC), true);
                }
                else {
                    sendMessage(ServerUtils.getText("Teleporting in " + teleportWindupSeconds + "...", Formatting.AQUA, Formatting.ITALIC), true);
                }

            }

        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    void injectDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir)
    {
        if(teleportWindupSeconds > 0)
        {
            cancelTeleport();
            sendMessage(ServerUtils.getText("Teleportation canceled due to being hurt!", Formatting.RED, Formatting.ITALIC), true);
        }
    }

    private float getDistance(Vec3d a, Vec3d b)
    {
        Vec3d diff = new Vec3d( a.x - b.x, a.y - b.y, a.z - b.z);

        return MathHelper.sqrt((float) (Math.pow(diff.x, 2f) + Math.pow(diff.y, 2f) + Math.pow(diff.z, 2f)));
    }

    @Override
    public void setTeleportTarget(Vec3d pos, int windupSeconds, ServerWorld world) {
        teleportTargetPosition = pos;
        teleportWindupSeconds = windupSeconds;
        teleportTargetWorld = world;
        teleportLastMovePos = getPos();
    }

    @Override
    public void cancelTeleport() {
        teleportTargetPosition = Vec3d.ZERO;
        teleportWindupSeconds = 0;
        teleportTargetWorld = null;
    }

    Vec3d teleportLastMovePos = Vec3d.ZERO;
    Vec3d teleportTargetPosition = Vec3d.ZERO;
    ServerWorld teleportTargetWorld = null;
    int teleportWindupSeconds = 0;

    int tickTimer = 0;
}
