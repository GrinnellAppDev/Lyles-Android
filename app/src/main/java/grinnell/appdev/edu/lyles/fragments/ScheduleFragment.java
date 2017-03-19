package grinnell.appdev.edu.lyles.fragments;

import android.app.ListFragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import grinnell.appdev.edu.lyles.R;
import grinnell.appdev.edu.lyles.ScheduleItem;
import grinnell.appdev.edu.lyles.ScheduleItemInterface;
import grinnell.appdev.edu.lyles.ScheduleTabAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import grinnell.appdev.edu.lyles.AsyncRetrieval;

/**
 * Created by Mattori on 5/9/16.
 */
public class ScheduleFragment extends Fragment {

    private static final String SCHEDULE_URL = "http://www.cs.grinnell.edu/~pandeyan/schedule.json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View view =  inflater.inflate(R.layout.schedule_layout, container, false);
        ListView schedule = (ListView) view.findViewById(R.id.schedule_list);

        try {

            final String rawJSON = new AsyncRetrieval(SCHEDULE_URL).execute().get().string();

            JSONObject scheduleSummary = (JSONObject) new JSONTokener(rawJSON).nextValue();
            JSONArray scheduleJSONArray = scheduleSummary.getJSONArray("schedule");
            //convert JSONArray to ArrayList<ScheduleItem>
            //Should probably be its own method?
            ArrayList<ScheduleItemInterface> scheduleList = new ArrayList<>();
            JSONObject scheduleItemJSON;
            for (int i = 0; i < scheduleJSONArray.length(); i++) {
                scheduleItemJSON = scheduleJSONArray.getJSONObject(i);
                scheduleList.add(new ScheduleItem(scheduleItemJSON));
            }

            ScheduleTabAdapter adapter = new ScheduleTabAdapter(super.getContext(), scheduleList);
            schedule.setAdapter(adapter);
        } catch (IOException | JSONException | InterruptedException | ExecutionException ex) {
           ex.printStackTrace();
        }

        return view;
    }
}
