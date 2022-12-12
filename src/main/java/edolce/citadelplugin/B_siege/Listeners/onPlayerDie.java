package edolce.citadelplugin.B_siege.Listeners;

import com.massivecraft.factions.FPlayers;
import edolce.citadelplugin.miniDatabase.Database;
import edolce.citadelplugin.B_siege.SiegeEvents.AttackerDiesEvent;
import edolce.citadelplugin.B_siege.SiegeEvents.DefenderDiesEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onPlayerDie implements Listener {

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        //Controlla se muore nella regione della siege

        //Controlla se e un attacker o un defender
        String factionTag = FPlayers.getInstance().getByPlayer(event.getEntity()).getFaction().getTag();

        if(factionTag == Database.getDefendersFaction().getTag()){
            //e defender
            Bukkit.getPluginManager().callEvent(new DefenderDiesEvent(event.getEntity()));
        }else {
            Bukkit.getPluginManager().callEvent(new AttackerDiesEvent(event.getEntity()));
        }
    }


}
