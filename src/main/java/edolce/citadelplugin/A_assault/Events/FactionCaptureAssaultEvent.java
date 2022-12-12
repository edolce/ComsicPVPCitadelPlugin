package edolce.citadelplugin.A_assault.Events;

import com.massivecraft.factions.Faction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;







public class FactionCaptureAssaultEvent extends Event {

    private final Faction winnerFaction;
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }




    public FactionCaptureAssaultEvent(Faction kingFaction) {
        this.winnerFaction = kingFaction;
    }

    public Faction getWinnerFaction() {
        return winnerFaction;
    }
}
