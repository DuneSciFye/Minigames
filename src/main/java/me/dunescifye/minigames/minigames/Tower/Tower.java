package me.dunescifye.minigames.minigames.Tower;

import me.dunescifye.minigames.Minigames;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Tower {

    public static final NamespacedKey levelKey = new NamespacedKey("score", "score-level");
    public static final NamespacedKey experienceKey = new NamespacedKey("score", "score-experience");

    private final ArrayList<Player> players = new ArrayList<>();
    private final World world;

    public Tower(Player host) {
        players.addFirst(host);
        world = host.getWorld(); // Temporary
        new TowerPlayer(host);
    }

    public static void setup() {
        new Commands().register();
        Bukkit.getPluginManager().registerEvents(new Listeners(), Minigames.getPlugin());
    }

}
