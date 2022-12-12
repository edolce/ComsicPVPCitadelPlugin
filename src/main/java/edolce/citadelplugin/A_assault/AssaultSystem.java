package edolce.citadelplugin.A_assault;

import com.massivecraft.factions.Faction;
import edolce.citadelplugin.CitadelPluginTest;
import edolce.citadelplugin.CitadelStatus;
import edolce.citadelplugin.Citadel;
import edolce.citadelplugin.A_assault.Events.FactionCaptureAssaultEvent;
import edolce.citadelplugin.A_assault.Events.FactionEnterAssaultRegionEvent;
import edolce.citadelplugin.miniDatabase.Database;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;

public class AssaultSystem implements Listener {
    private final double captureTimeMinutes = 0.10;
    private final int deathBanTimeMinutes = 5;

    @Nullable
    private Faction kingFaction = null;

    private long currentCaptureTimeSeconds = 0;

    private BukkitRunnable captureTimer= new BukkitRunnable() {
        @Override
        public void run() {
            if(currentCaptureTimeSeconds<=captureTimeMinutes*60){
                currentCaptureTimeSeconds++;
                System.out.println(String.format("[%s] - %s is capturing...",currentCaptureTimeSeconds,kingFaction.getTag()));
            }else{
                //Cattura effettuata
                Bukkit.getPluginManager().callEvent(new FactionCaptureAssaultEvent(kingFaction));
                this.cancel();
            }
        }
    };


    public AssaultSystem() {

    }




    @EventHandler
    public void onFactionPlayerEnterRegion(FactionEnterAssaultRegionEvent event){
        //Controlla se l'assalto é attivo
        if(Citadel.getCurrent()==null) return;
        if(Citadel.getCurrent().getStatus()== CitadelStatus.LOCKED) return;


        //Se il player non e di nessuna fazione non fare nulla
        if(event.getFaction()==null) return;
        //Se il player e della stessa fazione che sta conqueistando non fare nulla
        if(kingFaction==event.getFaction()){
            return;
        }
        //se il player e di un altra fazione inizia il processo di conquista
        if(kingFaction!=null){
            System.out.println(String.format("[%s] - %s lost the capturing...",currentCaptureTimeSeconds,kingFaction.getTag()));
            captureTimer.cancel();
        }
        kingFaction=event.getFaction();
        captureTimer.runTaskTimer(CitadelPluginTest.getInstance(),0,20);
        System.out.println(String.format("[%s] - %s is capturing...",currentCaptureTimeSeconds,kingFaction.getTag()));
    }


    @EventHandler
    public void onFactionCaptureRegion(FactionCaptureAssaultEvent event){
        System.out.println(String.format("[%s] - %s Ha Catturato l'assalto...",currentCaptureTimeSeconds,kingFaction.getTag()));
        Faction winnerFaction = event.getWinnerFaction();

        Database.setWinners(winnerFaction, Citadel.getCurrent());
        //Add the winner-faction and players to the mini database
    }
}
