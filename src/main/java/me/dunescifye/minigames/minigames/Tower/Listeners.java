package me.dunescifye.minigames.minigames.Tower;

import com.ssomar.score.api.executableitems.events.AddItemInPlayerInventoryEvent;
import me.dunescifye.minigames.Minigames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static me.dunescifye.minigames.Minigames.minigamePlayers;
import static me.dunescifye.minigames.minigames.Tower.utils.Synergy.synergyIncomingKey;
import static me.dunescifye.minigames.minigames.Tower.Tower.*;

public class Listeners implements Listener {

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

    // Do item synergies, can't ignore cancelled event because EI cancels the event
    @EventHandler
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


    @EventHandler
    public void onGetEI(AddItemInPlayerInventoryEvent e) {
        System.out.println("a");
    }
}
