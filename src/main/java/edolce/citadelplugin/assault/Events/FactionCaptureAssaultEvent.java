package edolce.citadelplugin.assault.Events;

import com.massivecraft.factions.Faction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionCaptureAssaultEvent extends Event {

    private final Faction winnerFaction;

    public FactionCaptureAssaultEvent(Faction kingFaction) {
        this.winnerFaction = kingFaction;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public Faction getWinnerFaction() {
        return winnerFaction;
    }
}
