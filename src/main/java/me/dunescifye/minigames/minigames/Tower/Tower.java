package me.dunescifye.minigames.minigames.Tower;

import me.dunescifye.minigames.Minigames;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static me.dunescifye.minigames.Minigames.minigamePlayers;

public class Tower {

    private final ArrayList<Player> players = new ArrayList<>();
    private final World world;

    public Tower(Player host) {
        players.addFirst(host);
        world = host.getWorld(); // Temporary
        new TowerPlayer(host);
    }

}
