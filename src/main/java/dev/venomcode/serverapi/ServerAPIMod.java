package dev.venomcode.serverapi;

import dev.venomcode.serverapi.api.ServerAPI;
import dev.venomcode.serverapi.commands.server.ServerCommand;
import dev.venomcode.serverapi.config.ServerAPIConfig;
import dev.venomcode.serverapi.data.SAPIData;
import dev.venomcode.serverapi.items.SoulShardItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;

import static org.fusesource.jansi.Ansi.ansi;

public class ServerAPIMod implements ModInitializer {
    public static final String MODID = "serverapi";
    public static Logger LOGGER;

    @Override
    public void onInitialize() {
        // Install JANSI into this program for colorizing console outputs
        AnsiConsole.systemInstall();
        LOGGER = LoggerFactory.getLogger("ServerAPI");

        CommandRegistrationCallback.EVENT.register( (dispatcher, registryAccess, registrationEnvironment) -> {
            ServerCommand.register(dispatcher);
        });

        getConfig();
        saveConfig();

        // Example of colored console output using JANSI with the LOGGER:
        LOGGER.info( ServerAPI.Logger.Debug("Loaded ServerAPI!") );



        ServerPlayerEvents.AFTER_RESPAWN.register( ((oldPlayer, newPlayer, alive) ->
        {
            boolean isBedBlock = false;
            boolean isAnchorBlock = false;
            if(oldPlayer.getSpawnPointPosition() != null)
            {
                BlockState spawnBlockState = oldPlayer.getServerWorld().getBlockState(oldPlayer.getSpawnPointPosition());
                Block spawnBlock = spawnBlockState.getBlock();

                isBedBlock = (spawnBlock instanceof BedBlock && BedBlock.isBedWorking(oldPlayer.getServerWorld()));
                isAnchorBlock = (spawnBlock instanceof RespawnAnchorBlock && RespawnAnchorBlock.isNether(newPlayer.getWorld()));
            }

            if(!isBedBlock && !isAnchorBlock)
            {
                SAPIData data = SAPIData.getState(newPlayer.server);
                if(data.isSpawnSet())
                {
                    double sX = data.getSpawnPos().x;
                    double sY = data.getSpawnPos().y;
                    double sZ = data.getSpawnPos().z;
                    ServerWorld sWorld = newPlayer.server.getWorld(RegistryKey.of(RegistryKeys.WORLD, data.getSpawnDimension()));

                    BlockPos blkPos = new BlockPos((int)Math.round(data.getSpawnPos().x), (int)Math.round(data.getSpawnPos().y), (int)Math.round(data.getSpawnPos().z));
                    newPlayer.setSpawnPoint(RegistryKey.of(RegistryKeys.WORLD, data.getSpawnDimension()), blkPos, 0, true, false);

                    newPlayer.teleport(sWorld, sX, sY, sZ, 0f, 0f);
                }
            }
        }));

        Registry.register(Registries.ITEM, new Identifier("serverapi:soul_shard"), new SoulShardItem(new FabricItemSettings().maxCount(1)));
    }

    public static ServerAPIConfig getConfig() {
        if(_configCached != null)
            return _configCached;

        try {
            CommentedConfigurationNode node = loader.load();

            _configCached = node.get(ServerAPIConfig.class);
        }
        catch (ConfigurateException ex) {
            LOGGER.info(ServerAPI.Logger.Error("[ERROR]Failed to load config."));
        }

        return _configCached;
    }

    public static void saveConfig() {
        CommentedConfigurationNode node = CommentedConfigurationNode.root();
        try {
            node.set(ServerAPIConfig.class, _configCached);
            loader.save(node);
        }
        catch (ConfigurateException ex)
        {
            LOGGER.info(ServerAPI.Logger.Error("[ERROR]Failed to save config."));
        }
    }

    private static final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
            .path(Path.of(ServerAPI.CONFIG_PATH + "serverapi.conf"))
            .build();
    private static ServerAPIConfig _configCached = null;
}
