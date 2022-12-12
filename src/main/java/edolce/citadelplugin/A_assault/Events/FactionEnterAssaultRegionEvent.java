package edolce.citadelplugin.A_assault.Events;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import edolce.citadelplugin.miniDatabase.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;





/*
* Triggered By MACRO LISTENER: Player Enter Region
 */
public class FactionEnterAssaultRegionEvent extends Event {
    public final Player player;
    private static final HandlerList handlers = new HandlerList();
    public FactionEnterAssaultRegionEvent(Player player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Faction getFaction() {
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        return fplayer.getFaction();
    }


}
