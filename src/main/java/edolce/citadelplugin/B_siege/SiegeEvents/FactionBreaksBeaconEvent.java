package edolce.citadelplugin.B_siege.SiegeEvents;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionBreaksBeaconEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }


    Faction faction;

    public FactionBreaksBeaconEvent(Player player) {
        faction = FPlayers.getInstance().getByPlayer(player).getFaction();
    }

    public Faction getFaction() {
        return faction;
    }
}
