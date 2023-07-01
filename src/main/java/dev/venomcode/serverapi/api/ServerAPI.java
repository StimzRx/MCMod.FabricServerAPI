package dev.venomcode.serverapi.api;

import dev.venomcode.serverapi.ServerAPIMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.fusesource.jansi.Ansi;

import java.util.*;

import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.ansi;

public final class ServerAPI {

    public static ServerPlayerEntity getPlayerByUUID(UUID playerId, MinecraftServer server)
    {
        for (ServerPlayerEntity serverPlr : server.getPlayerManager().getPlayerList()) {
            if(serverPlr.getUuid() == playerId)
                return serverPlr;
        }

        return null;
    }

    public static final String CONFIG_PATH = "./config/";

    public final static class Logger
    {
        public static String Success(String msg)
        {
            return log(msg, Ansi.Color.GREEN);
        }
        public static String Warning(String msg)
        {
            return log(msg, Ansi.Color.YELLOW);
        }
        public static String Error(String msg)
        {
            return log(msg, Ansi.Color.RED);
        }
        public static String Debug(String msg)
        {
            return log(msg, Ansi.Color.CYAN);
        }

        static String log(String msg, Ansi.Color color)
        {
            return ansi().fg(color).a(msg).reset().toString();
        }
    }
}
