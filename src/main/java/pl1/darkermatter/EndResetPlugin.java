package pl1.darkermatter;

import org.bukkit.plugin.java.JavaPlugin;

public class EndResetPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("EndReset plugin has been loaded.");
        getCommand("endreset").setExecutor(new EndReset(this));
        getCommand("clearelytras").setExecutor(new EndReset(this));
        getCommand("saveelytraframes").setExecutor(new EndReset(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("EndReset plugin has been unloaded.");
    }
}
