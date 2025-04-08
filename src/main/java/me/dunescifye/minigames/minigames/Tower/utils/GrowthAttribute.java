package me.dunescifye.minigames.minigames.Tower.utils;

import org.bukkit.NamespacedKey;

import java.util.ArrayList;

public class GrowthAttribute {

    public final NamespacedKey baseKey;
    public final NamespacedKey growthKey;
    public final NamespacedKey maxKey;
    public final String name;

    GrowthAttribute(String name) {
        this.baseKey = new NamespacedKey("score", "score-" + name + "-base");
        this.growthKey = new NamespacedKey("score", "score-" + name + "-mult");
        this.maxKey = new NamespacedKey("score", "score-" + name + "-max");
        this.name = name;
    }

    public static ArrayList<GrowthAttribute> getGrowthAttributes() {
        ArrayList<GrowthAttribute> attributeKeys = new ArrayList<>();

        attributeKeys.add(new GrowthAttribute("growth1"));
        attributeKeys.add(new GrowthAttribute("growth2"));
        attributeKeys.add(new GrowthAttribute("growth3"));
        attributeKeys.add(new GrowthAttribute("growth4"));
        attributeKeys.add(new GrowthAttribute("growth5"));

        return attributeKeys;
    }
}
