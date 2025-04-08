package me.dunescifye.minigames.minigames.Tower;

import dev.jorel.commandapi.CommandAPICommand;

public class Commands {

    public void register() {
        new CommandAPICommand("tower")
            .executesPlayer((p, args) -> {
                new Tower(p);
                p.sendMessage("Starting!");
            })
            .register();
    }

}
