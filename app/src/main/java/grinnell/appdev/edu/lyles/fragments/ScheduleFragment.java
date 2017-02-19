package grinnell.appdev.edu.lyles.fragments;

import android.app.ListFragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import grinnell.appdev.edu.lyles.R;
import grinnell.appdev.edu.lyles.ScheduleItem;
import grinnell.appdev.edu.lyles.ScheduleTabAdapter;

/**
 * Created by Mattori on 5/9/16.
 */
public class ScheduleFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            String rawTestJSON = "{\n" +
                "  \"status\": \"ok\",\n" +
                "  \"count\": 3,\n" +
                "  \"schedule\": [\n" +
                "    {\n" +
                "      \"title\": \"AppDev Karaoke Night\",\n" +
                "      \"start\": \"1506456000\",\n" +
                "      \"end\": \"1506456000\",\n" +
                "      \"tab\": 100\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Political Science SEPC Pub Night\",\n" +
                "      \"start\": \"1506540600\",\n" +
                "      \"end\": \"1506544200\",\n" +
                "      \"tab\": 100\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Anita DeWitt and Maddie O'Meara Talk\",\n" +
                "      \"start\": \"1506544200\",\n" +
                "      \"end\": \"1506547800\",\n" +
                "      \"tab\": 100\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        final View view =  inflater.inflate(R.layout.schedule_layout, container, false);
        ListView schedule = (ListView) view.findViewById(R.id.schedule_list);
        try {

            JSONObject scheduleSummary = (JSONObject) new JSONTokener(rawTestJSON).nextValue();
            JSONArray scheduleJSONArray = scheduleSummary.getJSONArray("schedule");
            //convert JSONArray to ArrayList<ScheduleItem>
            //Should probably be its own method?
            ArrayList<ScheduleItem> scheduleList = new ArrayList<ScheduleItem>();
            JSONObject scheduleItemJSON;
            for (int i = 0; i < scheduleJSONArray.length(); i++) {
                scheduleItemJSON = scheduleJSONArray.getJSONObject(i);
                scheduleList.add(new ScheduleItem(scheduleItemJSON));
            }

            ScheduleTabAdapter adapter = new ScheduleTabAdapter(super.getContext(), scheduleList);
            schedule.setAdapter(adapter);
        } catch (JSONException ex) {
           ex.printStackTrace();
        }

        return view;
    }
}
