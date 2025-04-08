package me.dunescifye.minigames.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static void runConsoleCommands(List<String> commands) {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        Server server = Bukkit.getServer();
        for (String command : commands)
            if (!Objects.equals(command, "")) server.dispatchCommand(sender, command);
    }

    public static String smartRound(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        int scale = bd.stripTrailingZeros().scale();

        if (scale <= 2) {
            return bd.stripTrailingZeros().toPlainString();
        } else {
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return bd.toPlainString();
        }
    }

}
