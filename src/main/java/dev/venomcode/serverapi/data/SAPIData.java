package dev.venomcode.serverapi.data;

import dev.venomcode.serverapi.ServerAPIMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class SAPIData extends PersistentState {
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound spawnPointCompound = new NbtCompound();
        spawnPointCompound.putDouble("x", spawnPos.x);
        spawnPointCompound.putDouble("y", spawnPos.y);
        spawnPointCompound.putDouble("z", spawnPos.z);
        spawnPointCompound.putString("dimension", spawnDimension.toString());
        nbt.put("spawn_point", spawnPointCompound);

        return nbt;
    }

    public static SAPIData createFromNbt(NbtCompound tag)
    {
        SAPIData state = new SAPIData();

        NbtCompound spawnPointCompound = tag.getCompound("spawn_point");
        double sX = spawnPointCompound.getDouble("x");
        double sY = spawnPointCompound.getDouble("y");
        double sZ = spawnPointCompound.getDouble("z");
        state.spawnPos = new Vec3d(sX, sY, sZ);

        String dimensionIdentStr = spawnPointCompound.getString("dimension");
        state.spawnDimension = new Identifier(dimensionIdentStr);

        return state;
    }


    public Vec3d getSpawnPos()
    {
        return spawnPos;
    }
    public Identifier getSpawnDimension()
    {
        return spawnDimension;
    }
    public boolean isSpawnSet() { return spawnDimension != null; }
    public void setSpawn(Vec3d pos, Identifier dimension)
    {
        spawnPos = pos;
        spawnDimension = dimension;
        markDirty();
    }

    private Vec3d spawnPos;
    private Identifier spawnDimension = null;

    public static SAPIData getState(MinecraftServer server)
    {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        return persistentStateManager.getOrCreate(
                SAPIData::createFromNbt,
                SAPIData::new,
                ServerAPIMod.MODID);
    }
}
