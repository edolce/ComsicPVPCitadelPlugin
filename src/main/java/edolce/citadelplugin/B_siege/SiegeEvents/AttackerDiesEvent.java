package edolce.citadelplugin.B_siege.SiegeEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AttackerDiesEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    public AttackerDiesEvent(Player entity) {
        this.player=entity;
    }

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
