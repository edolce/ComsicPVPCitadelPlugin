package edolce.citadelplugin.assault;

import edolce.citadelplugin.CitadelType;
import edolce.citadelplugin.miniDatabase.Database;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//SINGLETON CLASS
public class CitadelAssault {

    private static CitadelAssault singletonInstance=null;

    private CitadelStatus status;
    private CitadelType citadelDate;

    private Location assaultSpawn;




    public static CitadelAssault getInstance(){
        if(singletonInstance==null){
            singletonInstance = new CitadelAssault();
            singletonInstance.setAssaultSpawn(Database.getAssaultSpawn());
        }
        return singletonInstance;
    }

    private void setAssaultSpawn(Location assaultSpawn) {
        this.assaultSpawn=assaultSpawn;
    }


    public CitadelStatus getStatus() {

        //0-Thursday //1-Friday
        int currentWeekDayNumber = ((int)System.currentTimeMillis()/(60*60*24*1000))%7;
        int currentDaySecond = (((int)(System.currentTimeMillis()/(1000))+3600)%(60*60*24));

        CitadelType prev = CitadelType.CITADEL_THIRD_ASSAULT;
        CitadelType next = CitadelType.CITADEL_FOURTH_ASSAULT;

        for (CitadelType value : CitadelType.values()) {
            if(value.getAssaultNumber()==null) return null;
            //Control if day is ahed or beyind
            if(value.greaterThan(currentWeekDayNumber,currentDaySecond)){
                if(!value.greaterThan(next.getWeekDayNumber(),next.getTime())){
                    next=value;
                }
            }else {
                if(value.greaterThan(prev.getWeekDayNumber(),prev.getTime())){
                    prev=value;
                }
            }
        }


        //check if prev is captured, if yes STATUS IS LOCKED, if not STATUS IS OPEN
        return Database.getAssaultStatus(prev.getAssaultNumber());
    }

    public CitadelType getCitadelType() {
        //0-Thursday //1-Friday
        int currentWeekDayNumber = ((int)System.currentTimeMillis()/(60*60*24*1000))%7;
        int currentDaySecond = (((int)(System.currentTimeMillis()/(1000))+3600)%(60*60*24));

        CitadelType prev = CitadelType.CITADEL_THIRD_ASSAULT;
        CitadelType next = CitadelType.CITADEL_FOURTH_ASSAULT;

        for (CitadelType value : CitadelType.values()) {
            if(value.getAssaultNumber()==null) return null;
            //Control if day is ahed or beyind
            if(value.greaterThan(currentWeekDayNumber,currentDaySecond)){
                if(!value.greaterThan(next.getWeekDayNumber(),next.getTime())){
                    next=value;
                }
            }else {
                if(value.greaterThan(prev.getWeekDayNumber(),prev.getTime())){
                    prev=value;
                }
            }
        }

        //check if prev is captured, if yes Display next, if not Display prev
        if(Database.getAssaultStatus(prev.getAssaultNumber())==CitadelStatus.LOCKED) return next;
        else return prev;
    }



    public ItemStack getItem(){
        ItemStack item = new ItemStack(Material.BEACON,1);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(this.getStatus().toString()));
        meta.setDisplayName(this.getCitadelType().toString());
        item.setItemMeta(meta);

        return item;
    }
}
