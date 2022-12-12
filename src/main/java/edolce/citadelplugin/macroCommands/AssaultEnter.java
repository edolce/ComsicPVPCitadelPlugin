package edolce.citadelplugin.macroCommands;

import edolce.citadelplugin.macroListeners.CitadelGui;
import edolce.citadelplugin.CitadelPluginTest;
import edolce.citadelplugin.Citadel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
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

        gui.setItem(2, Citadel.CITADEL_FIRST_ASSAULT.getItem());
        gui.setItem(3, Citadel.CITADEL_SECOND_ASSAULT.getItem());
        gui.setItem(4, Citadel.CITADEL_THIRD_ASSAULT.getItem());
        gui.setItem(5, Citadel.CITADEL_FOURTH_ASSAULT.getItem());
        gui.setItem(6, Citadel.CITADEL_SIEGE.getItem());

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                gui.setItem(2, Citadel.CITADEL_FIRST_ASSAULT.getItem());
                gui.setItem(3, Citadel.CITADEL_SECOND_ASSAULT.getItem());
                gui.setItem(4, Citadel.CITADEL_THIRD_ASSAULT.getItem());
                gui.setItem(5, Citadel.CITADEL_FOURTH_ASSAULT.getItem());
                gui.setItem(6, Citadel.CITADEL_SIEGE.getItem());
            }
        };

        BukkitTask task = runnable.runTaskTimer(CitadelPluginTest.getInstance(),20L,20L);

        Bukkit.getPluginManager().registerEvents(new CitadelGui(player,task), CitadelPluginTest.getInstance());
        return gui;
    }
}
