package me.dunescifye.minigames;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.dunescifye.minigames.minigames.MinigamePlayer;
import me.dunescifye.minigames.minigames.Tower.Tower;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Logger;

public final class Minigames extends JavaPlugin {

    public static MVWorldManager worldManager;
    public static HashMap<Player, MinigamePlayer> minigamePlayers = new HashMap<>();
    private static Minigames plugin;

    public static Minigames getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
    }

    @Override
    public void onEnable() {
        plugin = this;
        Logger logger = this.getLogger();
        logger.info("Minigames plugin starting up!");

        //Multiverse
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (core == null) {
            logger.warning("PracticeHubCore could not hook into Multiverse-Core. This is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            worldManager = core.getMVWorldManager();
        }

        CommandAPI.onEnable();
        Tower.setup();

    }

    @Override
    public void onDisable() {
        Logger logger = this.getLogger();
        logger.info("Minigames plugin shutting down!");

        CommandAPI.onDisable();
    }
}
