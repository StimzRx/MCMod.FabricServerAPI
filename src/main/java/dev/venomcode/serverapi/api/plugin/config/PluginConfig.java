package dev.venomcode.serverapi.api.plugin.config;

import dev.venomcode.serverapi.ServerAPIMod;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;

public class PluginConfig {
    public PluginConfig(String fileName) {

        _configLoader = HoconConfigurationLoader.builder()
                .path(Path.of(fileName + ".conf"))
                .build();
    }

    public void load()
    {
        try {
            _rootNode = _configLoader.load();
        } catch (IOException e) {
            System.err.println("An error occurred while loading this configuration: " + e.getMessage());
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            }
        }
    }
    public void save()
    {
        try {
            _configLoader.save(_rootNode);
        } catch (ConfigurateException e) {

        }
    }

    private final HoconConfigurationLoader _configLoader;
    CommentedConfigurationNode _rootNode;
}
