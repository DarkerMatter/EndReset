package pl1.darkermatter;

import org.bukkit.plugin.java.JavaPlugin;

public class EndResetPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Ensure the command names here match the ones in your plugin.yml
        getLogger().info("EndReset plugin has been loaded.");
        getLogger().info("If you have any issues please join my discord server at https://darker.systems/discord");
        this.getCommand("endreset").setExecutor(new EndReset(this));
        this.getCommand("resetelytras").setExecutor(new EndReset(this));
    }
    @Override
    public void onDisable() {
        getLogger().info("EndReset plugin has been unloaded.");
    }
}
