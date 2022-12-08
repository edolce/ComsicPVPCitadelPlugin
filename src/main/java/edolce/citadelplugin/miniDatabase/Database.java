package edolce.citadelplugin.miniDatabase;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.FactionsAPI;
import edolce.citadelplugin.CitadelPlugin;
import edolce.citadelplugin.assault.CitadelStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    public static HashMap<Faction,List<Player>> getAssaultFactionWinnerMap(){
        HashMap<Faction,List<Player>> feedBack = new HashMap<>();

        for(int i=1;i<5;i++){
            String factionId = CitadelPlugin.getInstance().getConfig().getString("Assault"+i+".FactionId");
            List<String> playersString = CitadelPlugin.getInstance().getConfig().getStringList("Assault"+i+".Players");

            List<Player> players=new ArrayList<>();

            for (String s : playersString) {
                Player p= Bukkit.getPlayer(s);
                players.add(p);
            }

            feedBack.put(Factions.getInstance().getByTag(factionId),players);
        }

        return feedBack;
    }
    public static Faction getDefendersFaction(){
        String factionId = CitadelPlugin.getInstance().getConfig().getString("Siege.Defenders.FactionId");
        return Factions.getInstance().getByTag(factionId);
    }

    public static CitadelStatus getAssaultStatus(Integer assaultNumber) {
        String status = CitadelPlugin.getInstance().getConfig().getString("Assault"+assaultNumber+".Status");
        return CitadelStatus.valueOf(status);
    }
}
