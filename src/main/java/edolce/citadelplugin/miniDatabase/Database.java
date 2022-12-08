package edolce.citadelplugin.miniDatabase;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import edolce.citadelplugin.CitadelPlugin;
import edolce.citadelplugin.assault.CitadelStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    public static Location getAssaultSpawn() {
        int xCoord = CitadelPlugin.getInstance().getConfig().getInt("AssaultCoords.x");
        int yCoord = CitadelPlugin.getInstance().getConfig().getInt("AssaultCoords.y");
        int zCoord = CitadelPlugin.getInstance().getConfig().getInt("AssaultCoords.z");
        String worldName = CitadelPlugin.getInstance().getConfig().getString("AssaultCoords.world");

        return new Location(Bukkit.getWorld(worldName),xCoord,yCoord,zCoord);
    }

    public static ProtectedRegion getRegion() {
        String worldName = CitadelPlugin.getInstance().getConfig().getString("AssaultWorldGuardRegion.world");
        String regionName = CitadelPlugin.getInstance().getConfig().getString("AssaultWorldGuardRegion.regionName");


        World parkourWorld = Objects.requireNonNull(Bukkit.getWorld(worldName));
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(parkourWorld));

        return regions.getRegion(regionName);
    }
}
