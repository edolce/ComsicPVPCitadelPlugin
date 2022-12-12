package edolce.citadelplugin.B_siege.SiegeEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DefenderDiesEvent extends Event {

    private final Player player;

    public DefenderDiesEvent(Player entity) {
        this.player=entity;
    }


    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }
}
