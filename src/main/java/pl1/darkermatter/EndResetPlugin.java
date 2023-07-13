package pl1.darkermatter;

import org.bukkit.plugin.java.JavaPlugin;

public class EndResetPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Ensure the command names here match the ones in your plugin.yml
        getLogger().info("EndReset plugin has been loaded.");
        getLogger().info("If you have any issues please join my discord server at https://darker.systems/discord");
        getLogger().info("Or check out the github repo at https://github.com/DarkerMatter/EndReset");
        this.getCommand("resetelytras").setExecutor(new EndReset(this));
    }
    @Override
    public void onDisable() {
        getLogger().info("EndReset plugin has been unloaded.");
    }
}
