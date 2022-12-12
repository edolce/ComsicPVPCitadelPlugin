package edolce.citadelplugin.B_siege.Listeners;

import com.massivecraft.factions.FPlayers;
import edolce.citadelplugin.CitadelPluginTest;
import edolce.citadelplugin.miniDatabase.Database;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class onPlayerBreakBeacon implements Listener {

    @EventHandler
    public void onPlayerBreakBeacon(BlockBreakEvent event){
        //Controlla se e nella region della siege
        //Controlla se e un beacon
        //Controlla se e un attacker
        if(Database.getDefendersFaction()== FPlayers.getInstance().getByPlayer(event.getPlayer()).getFaction()) {
            event.getPlayer().sendMessage("You are a defender, you can't break your own core!");
        }
        if(event.getBlock().getType()== Material.BEACON){
            //Diminuisci la vita del beacon
            CitadelPluginTest.getInstance().getCore().registerHit(event.getPlayer());
        }
    }

}
