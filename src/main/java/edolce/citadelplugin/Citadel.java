package edolce.citadelplugin;

import edolce.citadelplugin.miniDatabase.Database;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Citadel {
    CITADEL_FIRST_ASSAULT(0,1,1, "(Monday @ 3pm CST)","CITADEL_SECOND_ASSAULT",1,53999,"ASSAULT"),
    CITADEL_SECOND_ASSAULT(1,54000,2, "(Tuesday @ 3pm CST)","CITADEL_THIRD_ASSAULT",2,53999,"ASSAULT"),
    CITADEL_THIRD_ASSAULT(2,54000,3, "(Wednesday @ 3pm CST)","CITADEL_FOURTH_ASSAULT",3,53999,"ASSAULT"),
    CITADEL_FOURTH_ASSAULT(3,54000,4, "(Thursday @ 3pm CST)","CITADEL_SIEGE",4,53999,"ASSAULT"),
    CITADEL_SIEGE(5,54000,null, "(Saturday @ 3pm CST)","CITADEL_FIRST_ASSAULT",6,54000,"SIEGE");


    private final int weekDayNumber;
    private final int time;
    private final Integer assaultNumber;
    private final String startTime;
    private final String next;
    private final int weekDayEnd;
    private final int timeEnd;
    private final String type;

    private final List<String> defaultAssaultLore = Arrays.asList(
            "",
            "&d&lStatus: <status.type>",
            "&d&l<status.key> &f<time>",
            "&7<assault.start-time>",
            "",
            "&d&lLimits:",
            "&d&l* &fRisk Inventory",
            "&d&l* &f5m Deathban",
            "",
            "&7Capture this assault to earn your faction a",
            "&7place in the Citadel Siege on &f<siege.date>",
            "",
            "&7Click to teleport to assault."
    );

    Citadel(int weekDayNumber, int time, @Nullable Integer assaultNumber, String startTime, String next, int weekDayEnd, int timeEnd, String type){
        this.weekDayNumber = weekDayNumber;
        this.time = time;
        this.assaultNumber=assaultNumber;
        this.startTime = startTime;
        this.next = next;
        this.weekDayEnd = weekDayEnd;
        this.timeEnd = timeEnd;
        this.type = type;
    }

    public String getTitle(){
        if(this.toString().contains("ASSAULT")){
            return ChatColor.translateAlternateColorCodes('&',String.format("&c&lCitadel Assault #%s",this.assaultNumber));
        }else return ChatColor.translateAlternateColorCodes('&',"&c&lCitadel Siege");
    }

    public Integer getAssaultNumber() {
        return assaultNumber;
    }

    public static Citadel getCurrent(){
        //Controllo se il current time é nel range previsto
        long currentTimeWeekSeconds = ((System.currentTimeMillis()/1000+(4*24*60*60)+ (60 * 60)))%(7*24*60*60);

        for(Citadel type: Citadel.values()){
            long startWeekSeconds = type.weekDayNumber*(24*60*60)+type.time;
            long endWeekSeconds = type.weekDayEnd*(24*60*60)+type.timeEnd;

            if(startWeekSeconds>endWeekSeconds){
                //Viene aggiunta una settimana (permette lo shift adeguato)
                endWeekSeconds+= 7 * (24*60*60);
            }
            if(currentTimeWeekSeconds>=startWeekSeconds & currentTimeWeekSeconds<=endWeekSeconds){
                return type;
            }
        }

        return null;
    }


    //Get the display item
    public ItemStack getItem(){
        ItemStack item = new ItemStack(Material.BEACON,1);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(getEnhancedLore());
        meta.setDisplayName(this.getTitle());
        item.setItemMeta(meta);
        return item;
    }

    //get the modifided Lore
    private List<String> getEnhancedLore(){
        List<String> enhancedLore=new ArrayList<>();
        for (String s:defaultAssaultLore){
            if(s.contains("<status.type>")) s=s.replace("<status.type>",getStatus().getType());
            if(s.contains("<status.key>"))s=s.replace("<status.key>",getStatus().getKey());
            if(s.contains("<assault.start-time>"))s=s.replace("<assault.start-time>",startTime);
            if(s.contains("<time>"))s=s.replace("<time>",getStringTime());
            if(s.contains("<siege.date>"))s=s.replace("<status.type>", Citadel.CITADEL_SIEGE.toString());

            enhancedLore.add(ChatColor.translateAlternateColorCodes('&',s));
        }

        return enhancedLore;
    }

    //get the current status of the citadel assault-siege
    public CitadelStatus getStatus() {
        //Se e siege controlla il forced lock
        if(this==CITADEL_SIEGE){
            boolean locked = Database.isSiegeForceLocked();
            if(locked){
                return CitadelStatus.LOCKED;
            }
        }


        //Controllo se il current time é nel range previsto
        long currentTimeWeekSeconds = ((System.currentTimeMillis()/1000+(4*24*60*60)+ (60 * 60)))%(7*24*60*60);
        long startWeekSeconds = weekDayNumber*(24*60*60)+time;
        long endWeekSeconds = weekDayEnd*(24*60*60)+timeEnd;

        if(startWeekSeconds>endWeekSeconds){
            //Viene aggiunta una settimana (permette lo shift adeguato)
            endWeekSeconds+= 7 * (24*60*60);
        }

        if(currentTimeWeekSeconds>=startWeekSeconds & currentTimeWeekSeconds<endWeekSeconds){
            if(Database.isAssaultCaptured(this)) return CitadelStatus.LOCKED;
            return CitadelStatus.OPEN;
        }else return CitadelStatus.LOCKED;
    }

    //get time remaning
    public String getStringTime(){
        //Controllo se il current time é nel range previsto
        long currentTimeWeekSeconds = ((System.currentTimeMillis()/1000+(4*24*60*60)+ (60 * 60)))%(7*24*60*60);
        long startWeekSeconds = weekDayNumber*(24*60*60)+time;
        long endWeekSeconds = weekDayEnd*(24*60*60)+timeEnd;
        int totalSecondDiff;

        if(getStatus()==CitadelStatus.OPEN){
            if(currentTimeWeekSeconds>endWeekSeconds){
                endWeekSeconds+= 7 * (24*60*60);
            }
            totalSecondDiff= (int) (endWeekSeconds - currentTimeWeekSeconds);
        }else {
            if(currentTimeWeekSeconds>startWeekSeconds){
                startWeekSeconds+= 7 * (24*60*60);
            }
            totalSecondDiff= (int) (startWeekSeconds - currentTimeWeekSeconds);
        }

        int days = totalSecondDiff/(60*60*24);
        int hours = (totalSecondDiff%(60*60*24))/(60*60);
        int minutes = ((totalSecondDiff%(60*60*24))%(60*60))/60;
        int seconds = ((totalSecondDiff%(60*60*24))%(60*60))%60;

        return String.format("%sd %sh %sm %ss",days,hours,minutes,seconds);
    }
}

