package edolce.citadelplugin.siege;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import edolce.citadelplugin.assault.CitadelStatus;
import edolce.citadelplugin.CitadelType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class CitadelSiege {
    private List<Faction> factions= new ArrayList<>();
    private List<List<FPlayer>> players= new ArrayList<>();
    private final CitadelType citadelType = CitadelType.CITADEL_SIEGE;
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
