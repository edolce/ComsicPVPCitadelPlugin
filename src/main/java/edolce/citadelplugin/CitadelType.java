package edolce.citadelplugin;

import javax.annotation.Nullable;

public enum CitadelType {
    CITADEL_FIRST_ASSAULT(4,54000,1),
    CITADEL_SECOND_ASSAULT(5,54000,2),
    CITADEL_THIRD_ASSAULT(6,54000,3),
    CITADEL_FOURTH_ASSAULT(1,54000,4),
    CITADEL_SIEGE(2,54000,null);


    private final int weekDayNumber;
    private final int time;
    private final Integer assaultNumber;
    /*
    * weekDayNumber: (0-6) 0 => Giovedi
    * time: (0-86399)   0 => 0:00 CT
     */
    CitadelType(int weekDayNumber,int time,@Nullable Integer assaultNumber){
        this.weekDayNumber = weekDayNumber;
        this.time = time;
        this.assaultNumber=assaultNumber;
    }

    public int getTime() {
        return time;
    }

    public int getWeekDayNumber() {
        return weekDayNumber;
    }

    public Integer getAssaultNumber() {
        return assaultNumber;
    }

    public boolean greaterThan(int currentWeekDayNumber, int currentDaySecond) {

        if(weekDayNumber==currentWeekDayNumber){
            return time > currentDaySecond;
        }

        return weekDayNumber > currentWeekDayNumber;
    }
}

