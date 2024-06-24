package dev.tranducminh.disablephantom;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DisablePhantom extends JavaPlugin implements Listener {

    private boolean pluginEnabled;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        pluginEnabled = config.getBoolean("enabled", true);

        if (pluginEnabled) {
            getServer().getPluginManager().registerEvents(this, this);
            removeExistingPhantoms();
        } else {
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!pluginEnabled) return;

        if (event.getEntityType() == EntityType.PHANTOM) {
            event.setCancelled(true);
        }
    }

    private void removeExistingPhantoms() {
        if (!pluginEnabled) return;

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() == EntityType.PHANTOM) {
                    entity.remove();
                }
            }
        }
    }
}