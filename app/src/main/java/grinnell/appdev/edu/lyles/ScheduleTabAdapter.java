package grinnell.appdev.edu.lyles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by arp on 11/02/17.
 */



public class ScheduleTabAdapter extends ArrayAdapter<ScheduleItemInterface>{
    public ScheduleTabAdapter(Context context, ArrayList<ScheduleItemInterface> scheduleObjects) {
        super(context, 0, scheduleObjects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScheduleItemInterface scheduleItem = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.schedule_item, parent, false);
        TextView eventName = (TextView) convertView.findViewById(R.id.event_name);
        TextView eventTime = (TextView) convertView.findViewById(R.id.event_time);
        TextView eventTab = (TextView) convertView.findViewById(R.id.event_tab);


        if (!scheduleItem.isSeparator()) {
            ScheduleItem castedItem = (ScheduleItem) scheduleItem;
            eventName.setText(castedItem.getEventName());
            eventTime.setText(castedItem.timeWindowString());
            eventTab.setText("$" + castedItem.getTab() + " Tab");
        }

        return convertView;

    }
}
