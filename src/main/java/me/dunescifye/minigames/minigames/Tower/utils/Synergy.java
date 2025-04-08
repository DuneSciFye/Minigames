package me.dunescifye.minigames.minigames.Tower.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Synergy {

    public static final NamespacedKey synergyIncomingKey = new NamespacedKey("score", "score-synergy-incoming");

    public static final NamespacedKey synergySpearKey = new NamespacedKey("score", "score-synergy-spear");
    public static final NamespacedKey synergySwordKey = new NamespacedKey("score", "score-synergy-sword");
    public static final NamespacedKey synergyAssassinKey = new NamespacedKey("score", "score-synergy-assassin");
    public static final NamespacedKey synergyTankKey = new NamespacedKey("score", "score-synergy-tank");

    public final NamespacedKey synergyType;
    public double totalSynergy = 1.0;
    public final ArrayList<ItemStack> items = new ArrayList<>();
    public final ArrayList<Integer> slots = new ArrayList<>();

    Synergy(NamespacedKey synergyType) {
        this.synergyType = synergyType;
    }

    public static ArrayList<Synergy> getSynergies() {
        ArrayList<Synergy> synergies = new ArrayList<>();

        synergies.add(new Synergy(synergySpearKey));
        synergies.add(new Synergy(synergySwordKey));
        synergies.add(new Synergy(synergyAssassinKey));
        synergies.add(new Synergy(synergyTankKey));

        return synergies;
    }

}
