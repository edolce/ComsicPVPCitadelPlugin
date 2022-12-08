package edolce.citadelplugin.assault;

import com.massivecraft.factions.Faction;
import edolce.citadelplugin.CitadelPlugin;
import edolce.citadelplugin.assault.Events.FactionCaptureAssaultEvent;
import edolce.citadelplugin.assault.Events.FactionEnterAssaultRegionEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;

public class AssaultSystem implements Listener {
    private final int captureTimeMinutes = 15;
    private final int deathBanTimeMinutes = 5;
    private final CitadelAssault citadelAssault;
    @Nullable
    private Faction kingFaction = null;
    private long currentCaptureTimeSeconds = 0;

    private BukkitRunnable captureTimer= new BukkitRunnable() {
        @Override
        public void run() {
            if(currentCaptureTimeSeconds<=captureTimeMinutes*60){
                currentCaptureTimeSeconds++;
                System.out.printf("[%s] - %s is capturing...",currentCaptureTimeSeconds,kingFaction.getId());
            }else{
                //Cattura effettuata
                Bukkit.getPluginManager().callEvent(new FactionCaptureAssaultEvent(kingFaction));
                this.cancel();
            }
        }
    };


    public AssaultSystem(CitadelAssault citadelAssault) {
        this.citadelAssault = citadelAssault;
    }




    @EventHandler
    public void onFactionPlayerEnterRegion(FactionEnterAssaultRegionEvent event){
        if(event.getFaction()==null) return;
        if(kingFaction==event.getFaction()){
            return;
        }
        System.out.printf("[%s] - %s lost the capturing...",currentCaptureTimeSeconds,kingFaction.getId());
        //Every Second
        captureTimer.cancel();
        captureTimer.runTaskTimer(CitadelPlugin.getInstance(),0,20);
        System.out.printf("[%s] - %s started the capturing...",currentCaptureTimeSeconds,kingFaction.getId());
    }


    @EventHandler
    public void onFactionCaptureRegion(FactionCaptureAssaultEvent event){
        event.getWinnerFaction();
        //Add the winner-faction and players to the mini database
    }
}
