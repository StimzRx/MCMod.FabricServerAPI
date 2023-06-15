package dev.venomcode.serverapi.ifaces.player;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.Vec3d;

public interface IPlayerTeleporter {
    public void setTeleportTarget(Vec3d pos, int windupSeconds, ServerWorld world);
    public void cancelTeleport();
}
