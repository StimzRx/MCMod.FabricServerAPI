package dev.venomcode.serverapi.api;

import dev.venomcode.serverapi.ServerAPIMod;
import dev.venomcode.serverapi.api.plugin.FabricPlugin;

import java.util.*;

public final class ServerAPI {
    /// Registers a FabricPlugin instance to the ServerAPI
    public static void registerPlugin(FabricPlugin plugin)
    {
        String pluginName = plugin.getName();
        if(plugin.getName() == null)
        {
            pluginName = plugin.getClass().getSimpleName();
        }
        _plugins.put(pluginName, plugin);
        plugin.onEnable();

        ServerAPIMod.LOGGER.info("Registered plugin:" + pluginName);
    }
    /// Gets a plugin from the registry by its registered name
    public static FabricPlugin getPlugin(String pluginName)
    {
        return _plugins.get(pluginName);
    }
    /// Disables and then Enables all plugins
    public static void reloadAllPlugins()
    {
        for( Map.Entry<String, FabricPlugin> entry : _plugins.entrySet() )
        {
            entry.getValue().onDisable();
        }
        for( Map.Entry<String, FabricPlugin> entry : _plugins.entrySet() )
        {
            entry.getValue().onEnable();
        }
    }
    /// Gets a list(array) of names of all registered plugins
    public static List<String> getPluginNames()
    {
        List<String> tmpList = new ArrayList<>();
        for(Map.Entry<String, FabricPlugin> entry : _plugins.entrySet())
        {
            tmpList.add(entry.getKey());
        }
        return tmpList;
    }

    private static final Hashtable<String, FabricPlugin> _plugins = new Hashtable<>();
}
