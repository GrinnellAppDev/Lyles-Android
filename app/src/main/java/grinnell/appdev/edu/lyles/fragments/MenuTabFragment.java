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

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import grinnell.appdev.edu.lyles.AsyncRetrieval;
import grinnell.appdev.edu.lyles.MenuTabListAdapter;
import grinnell.appdev.edu.lyles.R;

import static grinnell.appdev.edu.lyles.MenuViewPagerAdapter.DRINKS_INDEX;
import static grinnell.appdev.edu.lyles.MenuViewPagerAdapter.HOT_FOOD_INDEX;
import static grinnell.appdev.edu.lyles.MenuViewPagerAdapter.SNACKS_INDEX;

/**
 * A fragment to display available drinks to users
 */
public class MenuTabFragment extends Fragment {

    private final int mPosition;

    public MenuTabFragment() {
        this.mPosition = -1;
    }

    public MenuTabFragment(int position) {
        this.mPosition = position;
    }

    private String retrieveJson() {
        try {
            String url;
            switch (this.mPosition) {
                case HOT_FOOD_INDEX:
                    url = "http://www.cs.grinnell.edu/~birnbaum/appdev/lyles/hotfood.json";
                    break;
                case SNACKS_INDEX:
                    url = "http://www.cs.grinnell.edu/~birnbaum/appdev/lyles/snacks.json";
                    break;
                case DRINKS_INDEX:
                    url = "http://www.cs.grinnell.edu/~birnbaum/appdev/lyles/drinks.json";
                    break;
                default:
                    Log.d("error", "unexpected position " + mPosition);
                    return null;
            }

            return new AsyncRetrieval(url).execute().get().string();
        } catch (IOException | InterruptedException | ExecutionException e) {
            Log.d("error",e.getMessage());
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menutab, container, false);
        ListView list = (ListView) view.findViewById(R.id.menutab_list);

        try {
            String buffer = this.retrieveJson();
            if(buffer != null) {
                JSONObject jsonobject = new JSONObject(buffer);
                JSONArray jsonarray;
                switch (this.mPosition) {
                    case HOT_FOOD_INDEX:
                        jsonarray = jsonobject.getJSONArray("hotfood");
                        break;
                    case DRINKS_INDEX:
                        jsonarray = jsonobject.getJSONArray("drinks");
                        break;
                    case SNACKS_INDEX:
                        jsonarray = jsonobject.getJSONArray("snacks");
                        break;
                    default:
                    /* this is literally unreachable code but java requires it anyway. thanks java */
                        return view;
                }
                ArrayList<JSONObject> items = new ArrayList<JSONObject>();
                for (int i = 0; i < jsonarray.length(); i++) {
                    items.add(jsonarray.getJSONObject(i));
                }
                list.setAdapter(new MenuTabListAdapter(getContext(), items));
            }
        } catch (JSONException e) {
            Log.d("error",e.getMessage());
        }

        return view;
    }
}