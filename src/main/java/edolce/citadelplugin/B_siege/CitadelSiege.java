package edolce.citadelplugin.B_siege;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import edolce.citadelplugin.CitadelStatus;
import edolce.citadelplugin.Citadel;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class CitadelSiege {
    private List<Faction> factions= new ArrayList<>();
    private List<List<FPlayer>> players= new ArrayList<>();
    private final Citadel citadel = Citadel.CITADEL_SIEGE;
    private CitadelStatus status = CitadelStatus.LOCKED;


    public ItemStack getItem(){
        return null;
    }






    public void setStatus(CitadelStatus status) {
        this.status = status;
    }
    public CitadelStatus getStatus() {
        return status;
    }
}
