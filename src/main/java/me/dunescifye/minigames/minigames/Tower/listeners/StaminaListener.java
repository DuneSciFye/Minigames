package me.dunescifye.minigames.minigames.Tower.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.dunescifye.minigames.Minigames;
import me.dunescifye.minigames.minigames.MinigamePlayer;
import me.dunescifye.minigames.minigames.Tower.TowerPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.dunescifye.minigames.Minigames.minigamePlayers;

public class StaminaListener implements Listener {

    // Reduce stamina when Jumping
    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e) {
        Player p = e.getPlayer();
        MinigamePlayer minigamePlayer = minigamePlayers.get(p);
        if (minigamePlayer == null) return;

        if (minigamePlayer instanceof TowerPlayer towerPlayer) {
            towerPlayer.reduceStamina(0.5);
        }
    }

    // Stamina bar for sprinting
    @EventHandler
    public void onPlayerSprint(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();
        MinigamePlayer minigamePlayer = minigamePlayers.get(p);
        if (minigamePlayer == null) return;

        if (minigamePlayer instanceof TowerPlayer towerPlayer) {
            towerPlayer.reduceStamina(0); // To trigger the Runnable that checks if player is running
        }
    }

    // Disable vanilla food changes
    @EventHandler
    public void onPlayerFoodChangeNaturally(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player p)
            if (minigamePlayers.get(p) instanceof TowerPlayer)
                e.setCancelled(true);
    }

}
