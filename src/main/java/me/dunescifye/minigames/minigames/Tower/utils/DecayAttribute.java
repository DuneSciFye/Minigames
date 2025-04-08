package me.dunescifye.minigames.minigames.Tower.utils;

import org.bukkit.NamespacedKey;

import java.util.ArrayList;

public class DecayAttribute {

    public final NamespacedKey baseKey;
    public final NamespacedKey decayKey;
    public final NamespacedKey minKey;
    public final String name;

    DecayAttribute(String name) {
        this.baseKey = new NamespacedKey("score", "score-" + name + "-base");
        this.decayKey = new NamespacedKey("score", "score-" + name + "-mult");
        this.minKey = new NamespacedKey("score", "score-" + name + "-min");
        this.name = name;
    }

    public static ArrayList<DecayAttribute> getDecayAttributes() {
        ArrayList<DecayAttribute> attributeKeys = new ArrayList<>();

        attributeKeys.add(new DecayAttribute("decay1"));
        attributeKeys.add(new DecayAttribute("decay2"));
        attributeKeys.add(new DecayAttribute("decay3"));
        attributeKeys.add(new DecayAttribute("decay4"));
        attributeKeys.add(new DecayAttribute("decay5"));

        return attributeKeys;
    }
}
