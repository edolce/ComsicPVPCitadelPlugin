package edolce.citadelplugin;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import edolce.citadelplugin.assault.CitadelAssault;
import edolce.citadelplugin.assault.Events.FactionCaptureAssaultEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class CitadelPlugin extends JavaPlugin implements Listener {

    private ProtectedRegion assaultRegion;
    private static CitadelPlugin instance;
    private CitadelAssault currentAssault ;


    @Override
    public void onEnable() {
        instance=this;
        this.saveDefaultConfig();
        initCurrentAssault();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onWorldLoaded(WorldLoadEvent event){
        if(event.getWorld().getName()=="world"){
            //Get the WorldGuard "parkour_region" region
            World parkourWorld = Objects.requireNonNull(Bukkit.getWorld("world"));
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager regions = container.get(BukkitAdapter.adapt(parkourWorld));
            assaultRegion = regions.getRegion("parkour_region");
        }
    }

    private void initCurrentAssault(){
    }





    public ProtectedRegion getAssaultRegion() {
        return assaultRegion;
    }

    public static CitadelPlugin getInstance(){
        return instance;
    }
}
