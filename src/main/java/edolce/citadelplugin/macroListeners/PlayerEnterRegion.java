package edolce.citadelplugin.macroListeners;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import edolce.citadelplugin.A_assault.Events.FactionEnterAssaultRegionEvent;
import edolce.citadelplugin.miniDatabase.Database;
import jdk.internal.vm.vector.VectorSupport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerEnterRegion implements Listener {

    @EventHandler
    private void onPlayerEnterRegion(PlayerMoveEvent event){
        Location dest = event.getTo();

        ProtectedRegion assaultRegion = Database.getAssaultRegion();
        ProtectedRegion siegeRegion = Database.getSiegeRegion();



        if(regionContainsLocation(assaultRegion,dest)){
            Bukkit.getPluginManager().callEvent(new FactionEnterAssaultRegionEvent(event.getPlayer()));
        }

        if(regionContainsLocation(siegeRegion,dest)){
            Bukkit.getPluginManager().callEvent(new FactionEnterAssaultRegionEvent(event.getPlayer()));
        }
    }

    private boolean regionContainsLocation(ProtectedRegion region, Location location){
        return region.contains(location.getBlockX(),location.getBlockY(),location.getBlockZ());
    }


}
