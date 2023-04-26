package pl1.darkermatter;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.StructureType;

import java.util.List;
import java.util.stream.Collectors;

public class EndReset implements CommandExecutor {

    private final JavaPlugin plugin;
    private BukkitTask resetElytrasTask;

    public EndReset(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("endreset").setExecutor(this);
        plugin.getCommand("resetelytras").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("endreset.command") && command.getName().equalsIgnoreCase("endreset")) {
            World endWorld = Bukkit.getWorld("world_the_end");

            if (endWorld == null) {
                sender.sendMessage(ChatColor.RED + "Error: Could not find the End dimension.");
                return true;
            }

            resetElytrasInWorld(endWorld);
            restoreElytrasInEndCityItemFrames(endWorld);
            sender.sendMessage(ChatColor.GREEN + "Elytras in the End dimension have been reset.");
        } else if (sender.hasPermission("resetelytras.command") && command.getName().equalsIgnoreCase("resetelytras")) {
            resetElytrasInServer();
            sender.sendMessage(ChatColor.GREEN + "All Elytras in the server have been removed.");
        }
        return true;
    }

    private void resetElytrasInWorld(World world) {
        for (Chunk chunk : world.getLoadedChunks()) {
            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof ItemFrame) {
                    ItemFrame itemFrame = (ItemFrame) entity;
                    ItemStack item = itemFrame.getItem();
                    if (item != null && item.getType() == Material.ELYTRA) {
                        itemFrame.setItem(null, false);
                    }
                }
            }
        }
    }

    private void restoreElytrasInEndCityItemFrames(World world) {
        for (Chunk chunk : world.getLoadedChunks()) {
            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof ItemFrame) {
                    ItemFrame itemFrame = (ItemFrame) entity;
                    Location location = itemFrame.getLocation();
                    if (world.locateNearestStructure(location, StructureType.END_CITY, 0, false) != null) {
                        ItemStack item = itemFrame.getItem();
                        if (item == null || item.getType() == Material.AIR) {
                            itemFrame.setItem(new ItemStack(Material.ELYTRA), false);
                        }
                    }
                }
            }
        }
    }

    private void resetElytrasInServer() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (BlockState blockState : chunk.getTileEntities()) {
                    if (blockState instanceof Container) {
                        Container container = (Container) blockState;
                        for (ItemStack item : container.getInventory().getContents()) {
                            if (item != null && item.getType() == Material.ELYTRA) {
                                container.getInventory().remove(item);
                            }
                        }
                    }
                }
            }
        }
    }
}
