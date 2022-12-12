package edolce.citadelplugin;

import edolce.citadelplugin.A_assault.AssaultSystem;
import edolce.citadelplugin.macroCommands.AssaultEnter;
import edolce.citadelplugin.A_assault.Events.FactionEnterAssaultRegionEvent;
import edolce.citadelplugin.macroListeners.PlayerEnterRegion;
import edolce.citadelplugin.miniDatabase.Database;
import edolce.citadelplugin.B_siege.Core;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class CitadelPluginTest extends JavaPlugin {

    private static CitadelPluginTest instance;
    private Core core = new Core();

    @Override
    public void onEnable() {
        instance=this;

        //Bukkit runnable to detect end/start of the SIEGE
        new BukkitRunnable() {
            private CitadelStatus oldStatus=CitadelStatus.LOCKED;
            @Override
            public void run() {
                CitadelStatus newStatus= Citadel.CITADEL_SIEGE.getStatus();
                if(oldStatus!=newStatus){
                    oldStatus=newStatus;
                    if(newStatus.equals(CitadelStatus.OPEN)){
                        CitadelPluginTest.getInstance().getServer().broadcastMessage("Siege is now OPEN");
                    }else {
                        CitadelPluginTest.getInstance().getServer().broadcastMessage("Siege is now CLOSED");
                        Database.resetAttackers();
                    }
                }
            }
        }.runTaskTimer(this,20,20);

        //LOAD CONFIG
        this.saveDefaultConfig();

        //LISTENERS
        getServer().getPluginManager().registerEvents(new AssaultSystem(),this);
        getServer().getPluginManager().registerEvents(new PlayerEnterRegion(),this);


        //COMMANDS
        getCommand("assault").setExecutor(new AssaultEnter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CitadelPluginTest getInstance(){
        return instance;
    }

    public Core getCore() {
        return core;
    }
}
