package edolce.citadelplugin.assault.Commands;

import edolce.citadelplugin.CitadelPlugin;
import edolce.citadelplugin.assault.CitadelAssault;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class AssaultEnter implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        //Open Gui Assault
        player.openInventory(getAssaultGui(player));

        return true;
    }


    //Init Gui
    private Inventory getAssaultGui(Player player){
        Inventory gui = Bukkit.createInventory(null,9);
        ItemStack assaultItem = CitadelAssault.getInstance().getItem();

        gui.setItem(4,assaultItem);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                gui.setItem(4,CitadelAssault.getInstance().getItem());
            }
        };

        BukkitTask task = runnable.runTaskTimer(CitadelPlugin.getInstance(),20L,20L);

        Bukkit.getPluginManager().registerEvents(new AssaultTimerListener(player,task),CitadelPlugin.getInstance());
        return gui;
    }
}
