package me.dunescifye.minigames.minigames.Deathswap;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Deathswap {

    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> spectators = new ArrayList<>();

    public Deathswap(Player p) {
        players.add(p);
    }

    public void start() {

    }

}
