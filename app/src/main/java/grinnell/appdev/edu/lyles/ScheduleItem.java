package grinnell.appdev.edu.lyles;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arp on 16/02/17.
 */

public class ScheduleItem implements ScheduleItemInterface {

    private static final SimpleDateFormat sFormatter = new SimpleDateFormat("h:mm a");

    private String mName;
    private Date mStartDate;
    private Date mEndDate;
    private int mTab;

    public ScheduleItem(JSONObject ScheduleJSONObject) {
        try {
            mName = ScheduleJSONObject.getString("title");
            // Date constructor uses milliseconds while we're using seconds
            mStartDate =  new Date(ScheduleJSONObject.getLong("start") * 1000);
            mEndDate = new Date(ScheduleJSONObject.getLong("end") * 1000);
            mTab = ScheduleJSONObject.getInt("tab");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public String timeWindowString() {
        String startTime = sFormatter.format(mStartDate);
        String endTime = sFormatter.format(mEndDate);
        return startTime + "-" + endTime;
    }

    public ScheduleItem(String eventName, Date startDate, Date endDate, int totalTab) {
        mName = eventName;
        mStartDate = startDate;
        mEndDate = endDate;
        mTab = totalTab;
    }

    public String getEventName() {
        return mName;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public int getTab() { return mTab;}

    public boolean isSeparator() {
        return false;
    }

}
