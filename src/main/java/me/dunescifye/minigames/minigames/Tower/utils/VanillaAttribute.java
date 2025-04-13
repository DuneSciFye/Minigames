package me.dunescifye.minigames.minigames.Tower.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;

import java.util.ArrayList;

public class VanillaAttribute {

    public final NamespacedKey baseKey;
    public final NamespacedKey growthKey;
    public final NamespacedKey decayKey;
    public final NamespacedKey defaultKey = new NamespacedKey("minecraft", "ad920d87-b922-4b4e-800e-a70dc2caa583");
    public final NamespacedKey maxKey;
    public final Attribute attribute;
    public final String name;

    VanillaAttribute(Attribute attribute, String name) {
        this.baseKey = new NamespacedKey("score", "score-" + name + "-base");
        this.growthKey = new NamespacedKey("score", "score-" + name + "-growth");
        this.decayKey = new NamespacedKey("score", "score-" + name + "-decay");
        this.maxKey = new NamespacedKey("score", "score-" + name + "-max");
        this.attribute = attribute;
        this.name = name;
    }

    public static ArrayList<VanillaAttribute> getVanillaAttributes() {
        ArrayList<VanillaAttribute> attributeKeys = new ArrayList<>();

        attributeKeys.add(new VanillaAttribute(Attribute.ATTACK_DAMAGE, "attack_damage"));
        attributeKeys.add(new VanillaAttribute(Attribute.ATTACK_KNOCKBACK, "attack_knockback"));
        attributeKeys.add(new VanillaAttribute(Attribute.ATTACK_SPEED, "attack_speed"));
        attributeKeys.add(new VanillaAttribute(Attribute.ARMOR, "armor"));
        attributeKeys.add(new VanillaAttribute(Attribute.ARMOR_TOUGHNESS, "armor_toughness"));
        attributeKeys.add(new VanillaAttribute(Attribute.SCALE, "scale"));
        attributeKeys.add(new VanillaAttribute(Attribute.BLOCK_BREAK_SPEED, "block_break_speed"));
        attributeKeys.add(new VanillaAttribute(Attribute.BLOCK_INTERACTION_RANGE, "block_interaction_range"));
        attributeKeys.add(new VanillaAttribute(Attribute.BURNING_TIME, "burning_time"));
        attributeKeys.add(new VanillaAttribute(Attribute.ENTITY_INTERACTION_RANGE, "entity_interaction_range"));
        attributeKeys.add(new VanillaAttribute(Attribute.EXPLOSION_KNOCKBACK_RESISTANCE, "explosion_knockback_resistance"));
        attributeKeys.add(new VanillaAttribute(Attribute.FALL_DAMAGE_MULTIPLIER, "fall_damage_multiplier"));
        attributeKeys.add(new VanillaAttribute(Attribute.GRAVITY, "gravity"));
        attributeKeys.add(new VanillaAttribute(Attribute.JUMP_STRENGTH, "jump_strength"));
        attributeKeys.add(new VanillaAttribute(Attribute.KNOCKBACK_RESISTANCE, "knockback_resistance"));
        attributeKeys.add(new VanillaAttribute(Attribute.MAX_ABSORPTION, "max_absorption"));
        attributeKeys.add(new VanillaAttribute(Attribute.MAX_HEALTH, "max_health"));
        attributeKeys.add(new VanillaAttribute(Attribute.MINING_EFFICIENCY, "mining_efficiency"));
        attributeKeys.add(new VanillaAttribute(Attribute.MOVEMENT_EFFICIENCY, "movement_efficiency"));
        attributeKeys.add(new VanillaAttribute(Attribute.MOVEMENT_SPEED, "movement_speed"));
        attributeKeys.add(new VanillaAttribute(Attribute.OXYGEN_BONUS, "oxygen_bonus"));
        attributeKeys.add(new VanillaAttribute(Attribute.SAFE_FALL_DISTANCE, "safe_fall_distance"));
        attributeKeys.add(new VanillaAttribute(Attribute.SNEAKING_SPEED, "sneaking_speed"));
        attributeKeys.add(new VanillaAttribute(Attribute.STEP_HEIGHT, "step_height"));
        attributeKeys.add(new VanillaAttribute(Attribute.SUBMERGED_MINING_SPEED, "submerged_mining_speed"));
        attributeKeys.add(new VanillaAttribute(Attribute.SWEEPING_DAMAGE_RATIO, "sweeping_damage_ratio"));
        attributeKeys.add(new VanillaAttribute(Attribute.WATER_MOVEMENT_EFFICIENCY, "water_movement_efficiency"));

        return attributeKeys;
    }

}
