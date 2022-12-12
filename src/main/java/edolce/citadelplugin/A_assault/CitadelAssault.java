//package edolce.citadelplugin.assault;
//
//import edolce.citadelplugin.CitadelPlugin;
//import edolce.citadelplugin.CitadelType;
//import edolce.citadelplugin.miniDatabase.Database;
//import org.bukkit.ChatColor;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//
////SINGLETON CLASS
//public class CitadelAssault {
//
//    private static CitadelAssault singletonInstance=null;
//
//    private Location assaultSpawn;
//
//    private final List<String> defaultLore = Arrays.asList(
//            "",
//            "&d&lStatus: <status.type>",
//            "&d&l<status.key> &f<time>",
//            "&7<assault.start-time>",
//            "",
//            "&d&lLimits:",
//            "&d&l* &fRisk Inventory",
//            "&d&l* &f5m Deathban",
//            "",
//            "&7Capture this assault to earn your faction a",
//            "&7place in the Citadel Siege on &f<siege.date>",
//            "",
//            "&7Click to teleport to assault."
//    );
//
//
//    //BASE METHODS
//    public static CitadelAssault getInstance(){
//        if(singletonInstance==null){
//            singletonInstance = new CitadelAssault();
//            singletonInstance.setAssaultSpawn(Database.getAssaultSpawn());
//        }
//        return singletonInstance;
//    }
//    private void setAssaultSpawn(Location assaultSpawn) {
//        this.assaultSpawn=assaultSpawn;
//    }
//
//    /*
//    * De
//     */
//    public CitadelStatus getStatus() {
//        if(CitadelType.getCurrent()==getCitadelType()){
//            return CitadelStatus.OPEN;
//        }else {
//            return CitadelStatus.LOCKED;
//        }
//    }
//
//    /*
//    * Get in which CitadelAssault the server is currently on
//     */
//    public CitadelType getCitadelType() {
//        CitadelType citadelType = CitadelType.getCurrent();
//        if(Database.isAssaultCaptured(citadelType)){
//            return citadelType.getNext();
//        }else {
//            return citadelType;
//        }
//    }
//
//
//    /*
//    * Get how much time's left until next assault
//     */
//    public String getStringTime(){
//      int currentWeekDayNumber = ((int)System.currentTimeMillis()/(60*60*24*1000))%7;
//      int currentDaySecond = (((int)(System.currentTimeMillis()/(1000))+3600)%(60*60*24));
////        int currentWeekDayNumber = (int) ((CitadelPlugin.getInstance().getDebugTime() /(60*60*24*1000))%7);
////        int currentDaySecond = (int) (((CitadelPlugin.getInstance().getDebugTime()/(1000))+3600)%(60*60*24));
//        int destWeekDay = CitadelType.getCurrent().getNext().getWeekDayNumber();
//        int destSecondsDay = CitadelType.getCurrent().getNext().getTime();
//
//        System.out.printf("currentWeekDay: %s\n",currentWeekDayNumber);
//        System.out.printf("currentDaySecond: %s\n",currentDaySecond);
//
//        System.out.printf("destWeekDay: %s\n",destWeekDay);
//        System.out.printf("destDaySecond: %s\n",destSecondsDay);
//
//        System.out.printf("citadelType: %s\n",getCitadelType());
//
//        // Shift the week
//        if (currentWeekDayNumber > destWeekDay) {
//            destWeekDay+=7;
//        } else if(currentWeekDayNumber == destWeekDay){
//            if(currentDaySecond > destSecondsDay){
//                destWeekDay+=7;
//            }
//        }
//
//        int currentTotalSeconds = currentWeekDayNumber*(24*60*60)+ currentDaySecond;
//        int destinationTotalSeconds = destWeekDay*(24*60*60)+ destSecondsDay;
//
//        //calculate difference in days
//        int weekDayDifference = destWeekDay-currentWeekDayNumber;
//        int secondsDifference;
//        //calculate difference in hours
//        if(currentDaySecond > destSecondsDay){
//            secondsDifference = (60*60*24)-currentDaySecond + destSecondsDay;
//        }else{
//            secondsDifference = destSecondsDay - currentDaySecond;
//        }
//
////        int totalSecondDiff = (weekDayDifference *(60*60*24)) + secondsDifference;
//        int totalSecondDiff = destinationTotalSeconds-currentTotalSeconds;
//
//        int days = totalSecondDiff/(60*60*24);
//        int hours = (totalSecondDiff%(60*60*24))/(60*60);
//        int minutes = ((totalSecondDiff%(60*60*24))%(60*60))/60;
//        int seconds = ((totalSecondDiff%(60*60*24))%(60*60))%60;
//
//        return String.format("%sd %sh %sm %ss",days,hours,minutes,seconds);
//    }
//
//
//    public ItemStack getItem(){
//        ItemStack item = new ItemStack(Material.BEACON,1);
//        ItemMeta meta = item.getItemMeta();
//        meta.setLore(getEnhancedLore());
//        meta.setDisplayName(getCitadelType().getTitle());
//        item.setItemMeta(meta);
//
//        return item;
//    }
//
//    private List<String> getEnhancedLore(){
//        List<String> enhancedLore=new ArrayList<>();
//        for (String s:defaultLore){
//            if(s.contains("<status.type>")) s=s.replace("<status.type>",getStatus().getType());
//            if(s.contains("<status.key>"))s=s.replace("<status.key>",getStatus().getKey());
//            if(s.contains("<assault.start-time>"))s=s.replace("<assault.start-time>",getCitadelType().getStartTime());
//            if(s.contains("<time>"))s=s.replace("<time>",getStringTime());
//            if(s.contains("<siege.date>"))s=s.replace("<status.type>",CitadelType.CITADEL_SIEGE.toString());
//
//            enhancedLore.add(ChatColor.translateAlternateColorCodes('&',s));
//        }
//
//        return enhancedLore;
//    }
//
//    public void clickedAction(Player player) {
//        if(getStatus()==CitadelStatus.OPEN){
//            player.teleport(assaultSpawn);
//        }
//    }
//}
