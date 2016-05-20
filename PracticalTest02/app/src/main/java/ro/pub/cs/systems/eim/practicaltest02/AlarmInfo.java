package ro.pub.cs.systems.eim.practicaltest02;

/**
 * Created by Jorj on 20-May-16.
 */
public class AlarmInfo {
    int hour;
    int min;
    String status;
    public AlarmInfo(int hour, int min, String status) {
        this.hour = hour;
        this.min = min;
        this.status = status;
    }

}
