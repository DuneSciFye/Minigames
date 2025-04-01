package me.dunescifye.minigames.minigames.Tower;

import me.dunescifye.minigames.minigames.MinigamePlayer;
import org.bukkit.entity.Player;

import static me.dunescifye.minigames.Minigames.minigamePlayers;

public class TowerPlayer extends MinigamePlayer {

    public final Player player;
    private double maxStamina = 20;
    private double stamina = 20;

    public TowerPlayer(Player player) {
        this.player = player;
        minigamePlayers.put(player, this);
    }

    public void reduceStamina(final double amount) {
        stamina -= amount;
        recalculateStamina();
    }

    public void recoverStamina(final double amount) {
        stamina += amount;
        recalculateStamina();
    }

    public double getStamina() {
        return stamina;
    }

    public double getMaxStamina() {
        return maxStamina;
    }

    private void recalculateStamina() {
        player.setFoodLevel((int) (20 * stamina / maxStamina));
    }

}
