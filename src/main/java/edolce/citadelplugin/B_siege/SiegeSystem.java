package edolce.citadelplugin.B_siege;

import edolce.citadelplugin.CitadelPluginTest;
import edolce.citadelplugin.CitadelStatus;
import edolce.citadelplugin.Citadel;
import edolce.citadelplugin.miniDatabase.Database;
import edolce.citadelplugin.B_siege.SiegeEvents.AttackerDiesEvent;
import edolce.citadelplugin.B_siege.SiegeEvents.DefenderDiesEvent;
import edolce.citadelplugin.B_siege.SiegeEvents.FactionBreaksBeaconEvent;
import edolce.citadelplugin.B_siege.SiegeEvents.FactionDefenderHasChangedEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SiegeSystem implements Listener {

    private final int attackersDeathBanTimeMinutes = 10;
    private final int defendersDeathBanTimeMinutes = 5;



    //Controlla se il player sta cercando di entrare nella region del siege e l'evento non é iniziato
    @EventHandler
    public void onRegionEnter(PlayerMoveEvent event){
        if(Database.getSiegeRegion().contains(event.getTo().getBlockX(),event.getTo().getBlockY(),event.getTo().getBlockZ())){
            if(Citadel.getCurrent().getAssaultNumber()==null & Citadel.getCurrent().getStatus()== CitadelStatus.OPEN){
                return;
            }else {
                event.getPlayer().sendMessage("The Siege Is not Started Yet");
                event.getPlayer().teleport(event.getFrom());
            }
        }
    }

    @EventHandler
    public void onAttackersDie(AttackerDiesEvent event){
        //Non Permettergli di entrare per 10 minuti
        Bukkit.getPluginManager().registerEvents(new regionBanTime(event.getPlayer(),"Siege",System.currentTimeMillis(),attackersDeathBanTimeMinutes*(60*1000)), CitadelPluginTest.getInstance());
    }
    @EventHandler
    public void onDefendersDie(DefenderDiesEvent event){
        //Non Permettergli di entrare per 5 minuti
        Bukkit.getPluginManager().registerEvents(new regionBanTime(event.getPlayer(),"Siege",System.currentTimeMillis(),defendersDeathBanTimeMinutes*(60*1000)), CitadelPluginTest.getInstance());
    }

    @EventHandler
    public void factionBreaksBeaconEvent(FactionBreaksBeaconEvent event){
        Database.setSiegeWinner(event.getFaction());
        Database.setForceLock(System.currentTimeMillis(),(60*60*24*1000));
    }

    @EventHandler
    public void onDefFactionChange(FactionDefenderHasChangedEvent event){

    }
}
