package me.dunescifye.minigames.minigames.Tower;

import me.dunescifye.minigames.Minigames;
import me.dunescifye.minigames.minigames.MinigamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import static me.dunescifye.minigames.Minigames.minigamePlayers;
import static me.dunescifye.minigames.minigames.Tower.utils.Synergy.synergyIncomingKey;
import static me.dunescifye.minigames.minigames.Tower.Tower.*;

public class Listeners implements Listener {

    // Stamina bar
    @EventHandler
    public void onPlayerSprint(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();
        MinigamePlayer minigamePlayer = minigamePlayers.get(p);
        if (minigamePlayer == null) return;

        if (minigamePlayer instanceof TowerPlayer towerPlayer) {
            // To only have one loop
            if (towerPlayer.getStamina() < towerPlayer.getMaxStamina())
                return;

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline())
                        this.cancel();

                    else if (p.isSprinting()) {
                        towerPlayer.reduceStamina(1);
                    } else {
                        towerPlayer.recoverStamina(0.5);
                        if (towerPlayer.getStamina() >= towerPlayer.getMaxStamina())
                            this.cancel();
                    }

                }
            }.runTaskTimer(Minigames.getPlugin(), 0L, 20L);
        }
    }

    // Disable vanilla food changes
    @EventHandler
    public void onPlayerFoodChangeNaturally(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player p)
            if (minigamePlayers.get(p) instanceof TowerPlayer)
                e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerGainExperience(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();

        // Ensure player is playing Tower
        if (!(minigamePlayers.get(p) instanceof TowerPlayer towerPlayer)) return;

        // Add XP to item
        ItemStack item = p.getInventory().getItemInMainHand();
        int expGained = e.getAmount();
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            Double experience = pdc.get(experienceKey, PersistentDataType.DOUBLE);
            if (experience != null) {
                pdc.set(experienceKey, PersistentDataType.DOUBLE, experience + expGained);
                item.setItemMeta(meta);

                towerPlayer.calculateVisualXP(item);
                towerPlayer.calculateItemLevel(item, -1);
            }
        }

        // Remove vanilla XP Gained
        p.giveExp(-expGained);
    }

    // Update xp bar to show held item's level
    @EventHandler
    public void onPlayerSelectItem(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        if (!(minigamePlayers.get(p) instanceof TowerPlayer towerPlayer)) return;

        towerPlayer.calculateVisualXP(p.getInventory().getItem(e.getNewSlot()));
    }

    // Do item synergies
    @EventHandler(ignoreCancelled = true)
    public void onItemPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        if (!(minigamePlayers.get(p) instanceof TowerPlayer towerPlayer)) return;

        ItemStack item = e.getItem().getItemStack();
        if (!item.hasItemMeta()) return;

        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        if (!pdc.has(synergyIncomingKey, PersistentDataType.DOUBLE)) return;

        // Run a tick later to register item in inventory
        Bukkit.getScheduler().runTask(Minigames.getPlugin(), towerPlayer::calculateInventorySynergy);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDropItem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (!(minigamePlayers.get(p) instanceof TowerPlayer towerPlayer)) return;

        ItemStack item = e.getItemDrop().getItemStack();
        if (!item.hasItemMeta()) return;

        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        if (pdc.has(experienceKey, PersistentDataType.DOUBLE)) {
            towerPlayer.calculateInventorySynergy();
        }
    }
}
