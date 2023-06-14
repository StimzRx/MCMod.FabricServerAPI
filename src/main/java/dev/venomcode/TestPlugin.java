package dev.venomcode;

import dev.venomcode.serverapi.ServerAPIMod;
import dev.venomcode.serverapi.api.plugin.FabricPlugin;

public class TestPlugin extends FabricPlugin {
    @Override
    public void onEnable() {
        //ServerAPIMod.LOGGER.info(ansi().fg(Ansi.Color.YELLOW).a("Tester ENABLED!").reset().toString());
    }

    @Override
    public void onDisable() {
        //ServerAPIMod.LOGGER.info(ansi().fg(Ansi.Color.YELLOW).a("Tester DISABLED!").reset().toString());
    }
}
