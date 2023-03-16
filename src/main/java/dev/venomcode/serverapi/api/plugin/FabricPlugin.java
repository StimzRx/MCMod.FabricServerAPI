package dev.venomcode.serverapi.api.plugin;

public class FabricPlugin {

    public void onEnable() {
        _pluginEnabled = true;
    }

    public void onDisable() {
        _pluginEnabled = false;
    }

    public String getName()
    {
        return _pluginName;
    }
    public boolean getEnabled()
    {
        return _pluginEnabled;
    }

    private String _pluginName;
    private boolean _pluginEnabled = false;
}
