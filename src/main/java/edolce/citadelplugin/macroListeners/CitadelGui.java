package edolce.citadelplugin.macroListeners;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitTask;

public class CitadelGui implements Listener {
    private final Player player;
    private final BukkitTask task;
    public CitadelGui(Player player, BukkitTask task){
        this.player=player;
        this.task = task;
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event){
        if(event.getPlayer()!=player) return;
        task.cancel();
    }

    @EventHandler
    public void onPlayerInteractEvent(InventoryClickEvent event){
        if(event.getWhoClicked()!=player) return;
        event.setCancelled(true);
        if(event.getCurrentItem().getType()== Material.BEACON){
            //CitadelAssault.getInstance().clickedAction((Player) event.getWhoClicked());
        }
    }
}
