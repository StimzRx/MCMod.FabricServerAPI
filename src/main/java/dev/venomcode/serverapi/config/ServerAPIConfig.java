package dev.venomcode.serverapi.config;

import dev.venomcode.serverapi.ServerAPIMod;
import dev.venomcode.serverapi.api.ServerAPI;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.io.IOException;
import java.nio.file.Path;

@ConfigSerializable
public class ServerAPIConfig {

    @Setting(value = "enable hotkey menu")
    @Comment("Enables the sneak+F hotkey to bring up the menu")
    private boolean enableHotkey = true;

    public boolean getHotkeyEnabled()
    {
        return enableHotkey;
    }
}
