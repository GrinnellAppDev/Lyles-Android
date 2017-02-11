package grinnell.appdev.edu.lyles.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import grinnell.appdev.edu.lyles.AsyncRetrieval;
import grinnell.appdev.edu.lyles.ItemAdapter;
import grinnell.appdev.edu.lyles.MenuItem;
import grinnell.appdev.edu.lyles.R;
import grinnell.appdev.edu.lyles.preferences.FavoritesManager;

/**
 * Created by Mattori on 5/9/16.
 */
public class FavoritesFragment extends Fragment {
    private final String HOT_FOOD_URL = "http://www.cs.grinnell.edu/~birnbaum/appdev/lyles/hotfood.json";

    private AsyncRetrieval asyncRetrieval;

    private String jsonBody;
    private JSONArray jsonArray;

    private ArrayList<MenuItem> menuItemList;
    private ItemAdapter itemAdapter;
    private ListView lvItems;

    private FavoritesManager favoritesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_layout, container, false);

        asyncRetrieval = new AsyncRetrieval(HOT_FOOD_URL);

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
        favoritesManager = new FavoritesManager(getContext(), menuItemList);

        itemAdapter = new ItemAdapter(this.getContext(), favoritesManager.getAllFavorites(), true);
        lvItems = (ListView) view.findViewById(R.id.lv_items_favorites);
        lvItems.setAdapter(itemAdapter);

        return view;
    }

}
