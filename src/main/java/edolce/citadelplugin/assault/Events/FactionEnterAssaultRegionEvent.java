package edolce.citadelplugin.assault.Events;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.sk89q.worldedit.math.BlockVector2;
import edolce.citadelplugin.miniDatabase.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Vector;

public class FactionEnterAssaultRegionEvent extends Event implements Listener {
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

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent event){
        BlockVector2 pos = BlockVector2.at(event.getTo().getBlockX(),event.getTo().getBlockZ());
        if(Database.getRegion().contains(pos)){
            Bukkit.getPluginManager().callEvent(this);
        }
    }
}
