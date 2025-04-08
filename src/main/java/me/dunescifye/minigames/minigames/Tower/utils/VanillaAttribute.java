package me.dunescifye.minigames.minigames.Tower.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;

import java.util.ArrayList;

public class VanillaAttribute {

    public static final NamespacedKey attackDamageBaseKey = new NamespacedKey("score", "score-attack-damage-base");
    public static final NamespacedKey attackDamageMultKey = new NamespacedKey("score", "score-attack-damage-mult");
    public static final NamespacedKey attackDamageDefaultKey = new NamespacedKey("minecraft", "595adcc3-fe87-461d-a93d-5a02c3bdec20");

    public static final NamespacedKey attackKnockbackBaseKey = new NamespacedKey("score", "score-attack-knockback-base");
    public static final NamespacedKey attackKnockbackMultKey = new NamespacedKey("score", "score-attack-knockback-mult");
    public static final NamespacedKey attackKnockbackDefaultKey = new NamespacedKey("minecraft", "d754df03-1ec5-4267-a5d6-d8dc0d0f3259");

    public static final NamespacedKey attackSpeedBaseKey = new NamespacedKey("score", "score-attack-speed-base");
    public static final NamespacedKey attackSpeedMultKey = new NamespacedKey("score", "score-attack-speed-mult");
    public static final NamespacedKey attackSpeedDefaultKey = new NamespacedKey("minecraft", "742f1740-c870-43ab-b6de-33dd9553fb6e");


    public final NamespacedKey baseKey;
    public final NamespacedKey multKey;
    public final NamespacedKey defaultKey;
    public final Attribute attribute;
    public final String name;
    public final double defaultValue;

    VanillaAttribute(NamespacedKey baseKey, NamespacedKey multKey, NamespacedKey defaultKey, Attribute attribute, String name, double defaultValue) {
        this.baseKey = baseKey;
        this.multKey = multKey;
        this.defaultKey = defaultKey;
        this.attribute = attribute;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public static ArrayList<VanillaAttribute> getVanillaAttributes() {
        ArrayList<VanillaAttribute> attributeKeys = new ArrayList<>();

        attributeKeys.add(new VanillaAttribute(attackDamageBaseKey, attackDamageMultKey, attackDamageDefaultKey, Attribute.ATTACK_DAMAGE, "attack_damage", 1.0));
        attributeKeys.add(new VanillaAttribute(attackKnockbackBaseKey, attackKnockbackMultKey, attackKnockbackDefaultKey, Attribute.ATTACK_KNOCKBACK, "attack_knockback", 0.0));
        attributeKeys.add(new VanillaAttribute(attackSpeedBaseKey, attackSpeedMultKey, attackSpeedDefaultKey, Attribute.ATTACK_SPEED, "attack_speed", 4.0));

        return attributeKeys;
    }

}
