package edolce.citadelplugin.assault.Events;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionEnterAssaultRegionEvent extends Event {
    public final Player player;

    public FactionEnterAssaultRegionEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public Faction getFaction() {
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        return fplayer.getFaction();
    }
}
