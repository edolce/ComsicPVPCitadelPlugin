package edolce.citadelplugin.assault.Commands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class AssaultTimerListener implements Listener {
    private final Player player;
    private final BukkitTask task;
    public AssaultTimerListener(Player player, BukkitTask task){
        this.player=player;
        this.task = task;
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event){
        if(event.getPlayer()!=player) return;
        task.cancel();
    }
}
