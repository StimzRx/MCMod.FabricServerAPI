package dev.venomcode.serverapi;

import dev.venomcode.serverapi.api.events.PlayerEvents;
import net.fabricmc.api.ModInitializer;

public class ServerAPIMod implements ModInitializer {
    @Override
    public void onInitialize() {
        PlayerEvents.BREAK_BLOCK.register( (player, world, block) ->
        {
            return false;
        });
    }
}
