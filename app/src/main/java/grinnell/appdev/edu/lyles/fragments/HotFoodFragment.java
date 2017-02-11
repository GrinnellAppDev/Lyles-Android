package grinnell.appdev.edu.lyles.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import grinnell.appdev.edu.lyles.AsyncRetrieval;
import grinnell.appdev.edu.lyles.ItemAdapter;
import grinnell.appdev.edu.lyles.MenuItem;
import grinnell.appdev.edu.lyles.R;

/**
 * A fragment to display available hot food to user
 */
public class HotFoodFragment extends Fragment {

    private AsyncRetrieval asyncRetrieval;
    private String jsonBody;
    private JSONArray jsonArray;

    private ArrayList<MenuItem> menuItemList;
    private ItemAdapter itemsAdapter;
    private ListView lvItems;

    public HotFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_food, container, false);

        asyncRetrieval = new AsyncRetrieval();

        try {
            jsonBody = asyncRetrieval.execute().get();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }

        jsonArray = asyncRetrieval.getJsonArray(jsonBody);
        menuItemList = MenuItem.fromJSON(jsonArray);

        itemsAdapter = new ItemAdapter(this.getContext(), menuItemList, false);
        lvItems = (ListView) view.findViewById(R.id.lv_items_hotfood);
        lvItems.setAdapter(itemsAdapter);

        return view;
    }

}
