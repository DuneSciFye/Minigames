package me.dunescifye.minigames.minigames.Tower;

import me.dunescifye.minigames.Minigames;
import me.dunescifye.minigames.minigames.MinigamePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLOutput;

import static me.dunescifye.minigames.Minigames.minigamePlayers;

public class Listeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();
        MinigamePlayer minigamePlayer = minigamePlayers.get(p);
        if (minigamePlayer == null) return;

        if (minigamePlayer instanceof TowerPlayer towerPlayer) {
            new BukkitRunnable() {
                @Override
                public void run() {

                    if (towerPlayer.getStamina() >= towerPlayer.getMaxStamina()) {
                        this.cancel();
                        return;
                    }

                    if (p.isSprinting()) {
                        towerPlayer.reduceStamina(1);
                    } else {
                        towerPlayer.recoverStamina(1);
                    }

                }
            }.runTaskTimer(Minigames.getPlugin(), 0L, 1L);
        }
    }

    @EventHandler
    public void onPlayerFoodChange(FoodLevelChangeEvent e) {
        System.out.println("ee");
    }
}
