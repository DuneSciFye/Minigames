package me.dunescifye.minigames.minigames.Tower;

import me.dunescifye.minigames.Minigames;
import me.dunescifye.minigames.Utils.Utils;
import me.dunescifye.minigames.minigames.MinigamePlayer;
import me.dunescifye.minigames.minigames.Tower.utils.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static me.dunescifye.minigames.Minigames.minigamePlayers;
import static me.dunescifye.minigames.Utils.Utils.smartRound;
import static me.dunescifye.minigames.minigames.Tower.utils.DecayAttribute.*;
import static me.dunescifye.minigames.minigames.Tower.utils.GrowthAttribute.getGrowthAttributes;
import static me.dunescifye.minigames.minigames.Tower.utils.VanillaAttribute.*;
import static me.dunescifye.minigames.minigames.Tower.utils.Synergy.*;
import static me.dunescifye.minigames.minigames.Tower.Tower.*;
import static me.dunescifye.minigames.minigames.Tower.utils.Weight.attributeKey;
import static me.dunescifye.minigames.minigames.Tower.utils.Weight.weightKey;


@SuppressWarnings("UnstableApiUsage")
public class TowerPlayer extends MinigamePlayer {

    public final Player player;

    private double maxStamina = 20;
    private double stamina = 20;
    private double weight = 0;

    private boolean recoveringStamina = false;

    public TowerPlayer(Player player) {
        this.player = player;

        // Keep player's health visual at 10 hearts
        player.setHealthScaled(true);
        player.setHealthScale(20);

        player.setFoodLevel(20);
        player.setSaturation(0);

        minigamePlayers.put(player, this);
    }

    public void reduceStamina(final double amount) {
        stamina = Math.max(0, stamina - amount);
        calculateVisualStamina();

        if (recoveringStamina) return;

        recoveringStamina = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    this.cancel();
                    recoveringStamina = false;
                }

                else if (player.isSprinting()) {
                    reduceStamina(1);
                } else {
                    recoverStamina(0.5);
                    if (stamina >= maxStamina) {
                        this.cancel();
                        recoveringStamina = false;
                    }
                }

            }
        }.runTaskTimer(Minigames.getPlugin(), 20L, 20L);
    }

    public void recoverStamina(final double amount) {
        stamina = Math.min(maxStamina, stamina + amount);
        calculateVisualStamina();
    }

    public double getStamina() {
        return stamina;
    }

    public double getMaxStamina() {
        return maxStamina;
    }

    private void calculateVisualStamina() {
        player.setFoodLevel((int) (20 * stamina / maxStamina));
    }

    public void calculateVisualXP(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
            Double experience = pdc.get(experienceKey, PersistentDataType.DOUBLE);
            Double synergy = pdc.get(synergyIncomingKey, PersistentDataType.DOUBLE);

            // Multiply experience by Synergy
            if (synergy != null) experience *= synergy;

            if (experience != null) {
                int level = 0;
                float xpNeeded = 5;

                while (experience >= xpNeeded) {
                    level++;
                    experience -= xpNeeded;
                    xpNeeded += 5;
                }

                player.setLevel(level);
                player.setExp((float) (experience / xpNeeded));
                return;
            }
        }

        player.setExperienceLevelAndProgress(0);
    }

    public void calculateItemLevel(@NonNull final ItemStack item, final int slot) {
        Double level = 0.0;
        int xpNeeded = 5;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        Double experience = pdc.get(experienceKey, PersistentDataType.DOUBLE);

        // Multiply experience by Synergy
        Double synergyIncoming = pdc.get(synergyIncomingKey, PersistentDataType.DOUBLE);
        if (synergyIncoming != null) experience *= synergyIncoming;

        while (experience >= xpNeeded) {
            level++;
            experience -= xpNeeded;
            xpNeeded += 5;
        }

        // Don't run changes if level hasn't changed
        Double initialLevel = pdc.get(levelKey, PersistentDataType.DOUBLE);
        if (level.equals(initialLevel)) return;

        String playerName = player.getName();
        // Run commands after setting meta
        ArrayList<String> commandsToRun = new ArrayList<>(List.of("ei console-modification set variable " + playerName + " " + slot + " level " + level));

        // Updating item's vanilla attributes such as damage, attack knockback, attack speed
        for (VanillaAttribute attributeKey : getVanillaAttributes()) {
            Double base = pdc.get(attributeKey.baseKey, PersistentDataType.DOUBLE);
            Double growth = pdc.get(attributeKey.growthKey, PersistentDataType.DOUBLE);
            Double max = pdc.get(attributeKey.maxKey, PersistentDataType.DOUBLE);
            if (base == null || growth == null || max == null) continue;

            double newValue = max - (max - base) * (Math.pow(growth, level));

            // Keep the Operation and Slot Group of Attributes
            Collection<AttributeModifier> attributeModifiers = meta.getAttributeModifiers(attributeKey.attribute);
            if (attributeModifiers != null) {
                for (AttributeModifier modifier : attributeModifiers) {
                    if (modifier.getKey().equals(attributeKey.defaultKey)) {
                        AttributeModifier attributeModifier = new AttributeModifier(attributeKey.defaultKey, newValue, modifier.getOperation(), modifier.getSlotGroup());
                        meta.removeAttributeModifier(attributeKey.attribute, attributeModifier);
                        meta.addAttributeModifier(attributeKey.attribute, attributeModifier);
                        break;
                    }
                }
            }

            commandsToRun.add("ei console-modification set variable " + playerName + " " + slot + " " + attributeKey.name + " " + smartRound(newValue));
        }

        // Decay formulas for non-vanilla attributes such as cooldown
        for (DecayAttribute decayAttribute : getDecayAttributes()) {
            Double base = pdc.get(decayAttribute.baseKey, PersistentDataType.DOUBLE);
            Double decay = pdc.get(decayAttribute.decayKey, PersistentDataType.DOUBLE);
            Double min = pdc.get(decayAttribute.minKey, PersistentDataType.DOUBLE);
            if (base == null || decay == null || min == null) continue;

            double newValue = min + (base - min) * (Math.pow(decay, level));
            commandsToRun.add("ei console-modification set variable " + playerName + " " + slot + " " + decayAttribute.name + " " + smartRound(newValue));
        }

        // Growth formulas for non-vanilla growth attributes such as range
        for (GrowthAttribute growthAttribute : getGrowthAttributes()) {
            Double base = pdc.get(growthAttribute.baseKey, PersistentDataType.DOUBLE);
            Double growth = pdc.get(growthAttribute.growthKey, PersistentDataType.DOUBLE);
            Double max = pdc.get(growthAttribute.maxKey, PersistentDataType.DOUBLE);
            if (base == null || growth == null || max == null) continue;

            double newValue = max - (max - base) * (Math.pow(growth, level));
            commandsToRun.add("ei console-modification set variable " + playerName + " " + slot + " " + growthAttribute.name + " " + smartRound(newValue));
        }

        item.setItemMeta(meta);
        Utils.runConsoleCommands(commandsToRun);
    }

    public void calculateInventorySynergy() {
        PlayerInventory inventory = player.getInventory();

        ArrayList<Synergy> synergies = getSynergies();

        weight = 0;

        // Add each item into respective synergies
        for (int i = 0; i <= 40; i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null || !item.hasItemMeta()) continue;
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();

            // Calculate Item Weight
            Double itemWeight = pdc.get(Weight.weightKey, PersistentDataType.DOUBLE);
            if (itemWeight != null) {
                weight += itemWeight;
            }

            // Reset Synergy to 1.0
            Double synergyIncoming = pdc.get(synergyIncomingKey, PersistentDataType.DOUBLE);
            if (synergyIncoming == null) continue;
            else pdc.set(synergyIncomingKey, PersistentDataType.DOUBLE, 1.0);

            item.setItemMeta(meta);

            // Synergies
            for (Synergy synergy : synergies) {
                Double itemSynergy = pdc.get(synergy.synergyType, PersistentDataType.DOUBLE);
                if (itemSynergy == null) continue;

                synergy.items.add(item);
                synergy.slots.add(i);
                synergy.totalSynergy *= itemSynergy;
            }
        }

        for (Synergy synergy : synergies) {
            for (int i = 0; i < synergy.items.size(); i++) {
                ItemStack item = synergy.items.get(i);
                int slot = synergy.slots.get(i);

                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer pdc = meta.getPersistentDataContainer();

                Double itemSynergy = pdc.get(synergy.synergyType, PersistentDataType.DOUBLE);
                Double synergyIncoming = pdc.get(synergyIncomingKey, PersistentDataType.DOUBLE);

                synergyIncoming *= synergy.totalSynergy / itemSynergy;

                pdc.set(synergyIncomingKey, PersistentDataType.DOUBLE, synergyIncoming);
                item.setItemMeta(meta);

                calculateItemLevel(item, slot);
                System.out.println("set synergy of item in slot " + slot + " to " + synergyIncoming);
            }
        }
        calculateVisualXP(inventory.getItemInMainHand());
        calculateWeight();
    }

    public void calculateWeight() {
        AttributeInstance attributeInstance = player.getAttribute(Attribute.MOVEMENT_SPEED);

        attributeInstance.removeModifier(attributeKey);

        AttributeModifier attributeModifier = new AttributeModifier(weightKey, weight * -0.001, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ANY);
        attributeInstance.addModifier(attributeModifier);
    }

}
