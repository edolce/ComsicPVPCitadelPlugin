package edolce.citadelplugin.B_siege;

import edolce.citadelplugin.miniDatabase.Database;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class regionBanTime implements Listener {

    private final Player player;
    private final String regionType;
    private final long banTime;
    private final long duration;

    public regionBanTime(Player player, String regionType, long banTime,long duration) {
        this.player = player;
        this.regionType = regionType;
        this.banTime = banTime;
        this.duration=duration;
    }


    @EventHandler
    public void onPlayerWalkInARegion(PlayerMoveEvent event){
        if(Objects.equals(regionType, "Siege")){
            if(Database.getSiegeRegion().contains(event.getTo().getBlockX(),event.getTo().getBlockY(),event.getTo().getBlockZ())){
                if(duration+banTime>System.currentTimeMillis()){
                    HandlerList.unregisterAll(this);
                }else{
                    player.teleport(event.getFrom());
                }
            }
        }else {
            if(Database.getAssaultRegion().contains(event.getTo().getBlockX(),event.getTo().getBlockY(),event.getTo().getBlockZ())){
                if(duration+banTime>System.currentTimeMillis()){
                    HandlerList.unregisterAll(this);
                }else{
                    player.teleport(event.getFrom());
                }
            }
        }
    }
}
