package pl1.darkermatter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;



public class EndReset implements CommandExecutor {

    private JavaPlugin plugin;

    public EndReset(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("endreset.command") && command.getName().equalsIgnoreCase("endreset")) {
            // ... (previous endreset command code) ...
        } else if (sender.hasPermission("clearelytras.command") && command.getName().equalsIgnoreCase("clearelytras")) {
            for (World world : Bukkit.getWorlds()) {
                resetElytrasInEnderChests(world);
                resetElytrasInChests(world);
            }
            sender.sendMessage(ChatColor.GREEN + "Elytras in Ender Chests and regular chests in all dimensions have been cleared.");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }

        if (sender.hasPermission("endreset.command") && command.getName().equalsIgnoreCase("endreset")) {
            World endWorld = Bukkit.getWorld("world_the_end");

            if (endWorld == null) {
                sender.sendMessage(ChatColor.RED + "Error: Could not find the End dimension.");
                return true;
            }

            resetElytrasInWorld(endWorld);
            restoreElytrasInItemFrames(endWorld);
            sender.sendMessage(ChatColor.GREEN + "Elytras in the End dimension have been reset.");
        }
        return true;
    }

    private void resetElytrasInWorld(World world) {
        for (Chunk chunk : world.getLoadedChunks()) {
            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof Item) {
                    ItemStack item = ((Item) entity).getItemStack();
                    if (item.getType() == Material.ELYTRA) {
                        entity.remove();
                    }
                }
            }
        }
    }

    private void resetElytrasInEnderChests(World world) {
        for (Player player : world.getPlayers()) {
            Inventory enderChest = player.getEnderChest();
            for (int i = 0; i < enderChest.getSize(); i++) {
                ItemStack item = enderChest.getItem(i);
                if (item != null && item.getType() == Material.ELYTRA) {
                    enderChest.setItem(i, null);
                }
            }
        }
    }
    private void resetElytrasInChests(World world) {
        for (Chunk chunk : world.getLoadedChunks()) {
            for (BlockState state : chunk.getTileEntities()) {
                if (state instanceof Container) {
                    Container container = (Container) state;
                    Inventory inventory = container.getInventory();
                    for (int i = 0; i < inventory.getSize(); i++) {
                        ItemStack item = inventory.getItem(i);
                        if (item != null && item.getType() == Material.ELYTRA) {
                            inventory.setItem(i, null);
                        }
                    }
                }
            }
        }
    }
    private void restoreElytrasInItemFrames(World world) {
        world.getEntities().stream()
                .filter(entity -> entity instanceof ItemFrame)
                .map(entity -> (ItemFrame) entity)
                .filter(itemFrame -> itemFrame.getItem().getType() == Material.AIR)
                .forEach(itemFrame -> itemFrame.setItem(new ItemStack(Material.ELYTRA), false));
    }

}
