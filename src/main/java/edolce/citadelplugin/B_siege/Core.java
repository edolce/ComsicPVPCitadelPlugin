package edolce.citadelplugin.B_siege;

import edolce.citadelplugin.B_siege.SiegeEvents.FactionBreaksBeaconEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Core {
    private final int max_life = 100;
    private int remainingLife = max_life;
    private long lastHitTime = 0;

    public Core(){

    }

    public boolean registerHit(Player player){
        if(lastHitTime+10*1000>System.currentTimeMillis()){
            return false;
        }
        if(lastHitTime==0){
            lastHitTime=System.currentTimeMillis();
            remainingLife--;
        }
        if(System.currentTimeMillis()-lastHitTime>60L*60L*1000L){
            remainingLife=max_life;
            remainingLife--;
        }
        if(remainingLife<=0){
            Bukkit.getPluginManager().callEvent(new FactionBreaksBeaconEvent(player));
        }
        return true;
    }
}
