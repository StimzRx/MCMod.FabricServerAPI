package dev.venomcode.serverapi;

import dev.venomcode.TestPlugin;
import dev.venomcode.serverapi.api.ServerAPI;
import dev.venomcode.serverapi.command.CommandPlugin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class ServerAPIMod implements ModInitializer {
    public static final String MOD_ID = "serverapi";
    public static Logger LOGGER;
    @Override
    public void onInitialize() {
        // Install JANSI into this program for colorizing console outputs
        AnsiConsole.systemInstall();
        LOGGER = LoggerFactory.getLogger("ServerAPI");

        CommandRegistrationCallback.EVENT.register( (dispatcher, registryAccess, registrationEnvironment) -> {
            CommandPlugin.register(dispatcher);
        });

        /* The following block is used to test the plugin functionality.
        // Commenting out for now, though
        TestPlugin plugin = new TestPlugin();
        ServerAPI.registerPlugin(plugin);
         */

        // Example of colored console output using JANSI with the LOGGER:
        //LOGGER.info( ansi().fg(GREEN).a("ServerAPI startup completed!").reset().toString() );
    }
}
