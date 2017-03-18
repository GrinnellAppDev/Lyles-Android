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

import grinnell.appdev.edu.lyles.R;
import grinnell.appdev.edu.lyles.ScheduleItem;
import grinnell.appdev.edu.lyles.ScheduleItemInterface;
import grinnell.appdev.edu.lyles.ScheduleTabAdapter;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Mattori on 5/9/16.
 */
public class ScheduleFragment extends Fragment {

    private static final String SCHEDULE_URL = "http://www.cs.grinnell.edu/~birnbaum/appdev/lyles/schedule.json";
    private class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.d("Okhttp", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.d("Okhttp", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View view =  inflater.inflate(R.layout.schedule_layout, container, false);
        ListView schedule = (ListView) view.findViewById(R.id.schedule_list);
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .build();


            Response response = client.newCall(new Request.Builder().url(SCHEDULE_URL).build()).execute();

            final String rawTestJSON = response.body().string();
            response.close();

            JSONObject scheduleSummary = (JSONObject) new JSONTokener(rawTestJSON).nextValue();
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
        } catch (JSONException | IOException ex) {
           ex.printStackTrace();
        }

        return view;
    }
}
