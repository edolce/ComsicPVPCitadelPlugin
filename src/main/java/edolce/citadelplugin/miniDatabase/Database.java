package edolce.citadelplugin.miniDatabase;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import edolce.citadelplugin.CitadelPluginTest;
import edolce.citadelplugin.CitadelStatus;
import edolce.citadelplugin.Citadel;
import edolce.citadelplugin.B_siege.SiegeEvents.FactionDefenderHasChangedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class Database {
    public static HashMap<Faction,List<Player>> getAssaultFactionWinnerMap(){
        CitadelPluginTest.getInstance().reloadConfig();
        HashMap<Faction,List<Player>> feedBack = new HashMap<>();

        for(int i=1;i<5;i++){
            String factionId = CitadelPluginTest.getInstance().getConfig().getString("Assault"+i+".FactionId");
            List<String> playersString = CitadelPluginTest.getInstance().getConfig().getStringList("Assault"+i+".Players");

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
        CitadelPluginTest.getInstance().reloadConfig();
        String factionId = CitadelPluginTest.getInstance().getConfig().getString("Siege.Defenders.FactionId");
        return Factions.getInstance().getByTag(factionId);
    }

    public static CitadelStatus getAssaultStatus(Integer assaultNumber) {
        CitadelPluginTest.getInstance().reloadConfig();
        String status = CitadelPluginTest.getInstance().getConfig().getString("Assault"+assaultNumber+".Status");
        return CitadelStatus.valueOf(status);
    }

    public static Location getAssaultSpawn() {
        CitadelPluginTest.getInstance().reloadConfig();
        int xCoord = CitadelPluginTest.getInstance().getConfig().getInt("AssaultCoords.x");
        int yCoord = CitadelPluginTest.getInstance().getConfig().getInt("AssaultCoords.y");
        int zCoord = CitadelPluginTest.getInstance().getConfig().getInt("AssaultCoords.z");
        String worldName = CitadelPluginTest.getInstance().getConfig().getString("AssaultCoords.world");

        return new Location(Bukkit.getWorld(worldName),xCoord,yCoord,zCoord);
    }

    public static ProtectedRegion getAssaultRegion() {

        CitadelPluginTest.getInstance().reloadConfig();

        String worldName = CitadelPluginTest.getInstance().getConfig().getString("AssaultWorldGuardRegion.world");
        String regionName = CitadelPluginTest.getInstance().getConfig().getString("AssaultWorldGuardRegion.regionName");


        World parkourWorld = Objects.requireNonNull(Bukkit.getWorld(worldName));
        RegionManager container = WorldGuardPlugin.inst().getRegionManager(parkourWorld);

        return container.getRegion(regionName);
    }

    //set Winners
    public static void setWinners(Faction winnerFaction, Citadel current) {
        CitadelPluginTest.getInstance().reloadConfig();

        List<String> players = new ArrayList<>();

        for (FPlayer player: winnerFaction.getFPlayers()) {
            players.add(player.getPlayer().getName());
        }

        CitadelPluginTest.getInstance().getConfig().set("Assault"+current.getAssaultNumber()+".FactionId",winnerFaction.getTag());
        CitadelPluginTest.getInstance().getConfig().set("Assault"+current.getAssaultNumber()+".Players",players);

        CitadelPluginTest.getInstance().saveConfig();
    }

    public static boolean isAssaultCaptured(Citadel citadel) {
        CitadelPluginTest.getInstance().reloadConfig();
        String factionCaptured;
        if(citadel.getAssaultNumber()==null){
            factionCaptured = CitadelPluginTest.getInstance().getConfig().getString("Siege.FactionWinner");
        }else {
            factionCaptured = CitadelPluginTest.getInstance().getConfig().getString("Assault"+ citadel.getAssaultNumber()+".FactionId");
        }
        return !factionCaptured.equals("");
    }

    public static void setSiegeWinner(Faction faction) {
        //Check if old DEF faction is != now DEF faction
        if(getDefendersFaction()!=faction) {
            Bukkit.getPluginManager().callEvent(new FactionDefenderHasChangedEvent(faction));
        }
        CitadelPluginTest.getInstance().getConfig().set("Siege.Defender",faction.getTag());
        CitadelPluginTest.getInstance().saveConfig();
    }

    public static ProtectedRegion getSiegeRegion() {

        CitadelPluginTest.getInstance().reloadConfig();

        String worldName = CitadelPluginTest.getInstance().getConfig().getString("SiegeWorldGuardRegion.world");
        String regionName = CitadelPluginTest.getInstance().getConfig().getString("SiegeWorldGuardRegion.regionName");


        World parkourWorld = Objects.requireNonNull(Bukkit.getWorld(worldName));
        RegionManager container = WorldGuardPlugin.inst().getRegionManager(parkourWorld);

        return container.getRegion(regionName);
    }

    public static boolean isSiegeForceLocked() {
        CitadelPluginTest.getInstance().reloadConfig();
        if(CitadelPluginTest.getInstance().getConfig().getBoolean("Siege.ForceLock")){
            long timeStart = CitadelPluginTest.getInstance().getConfig().getLong("Siege.ForceLock.ForceLockTimeStart");
            long duration = CitadelPluginTest.getInstance().getConfig().getLong("Siege.ForceLock.ForceLockDuration");
            if(timeStart+duration>System.currentTimeMillis()){
                return true;
            }
        }
        return false;
    }

    public static void resetAttackers() {
        CitadelPluginTest.getInstance().reloadConfig();
        for (int i = 1; i < 5; i++) {
            CitadelPluginTest.getInstance().getConfig().set("Assault"+i+".FactionId","");
            CitadelPluginTest.getInstance().getConfig().set("Assault"+i+".Players",null);
        }
    }

    public static void setForceLock(long currentTimeMillis, int duration) {

        CitadelPluginTest.getInstance().reloadConfig();


        CitadelPluginTest.getInstance().getConfig().set("Siege.ForceLock",true);
        CitadelPluginTest.getInstance().getConfig().set("Siege.ForceLock.ForceLockTimeStart",currentTimeMillis);
        CitadelPluginTest.getInstance().getConfig().set("Siege.ForceLock.ForceLockDuration",duration);
    }
}
