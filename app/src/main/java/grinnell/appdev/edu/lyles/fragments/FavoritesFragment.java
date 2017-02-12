package grinnell.appdev.edu.lyles.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    private ArrayList<String> urlList;
    private ArrayList<String> arrayTitles;

    private ArrayList<MenuItem> menuItemList; // Contains all menu items in all categories
    private ItemAdapter itemAdapter;
    private ListView lvItems;

    private FavoritesManager favoritesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_layout, container, false);

        // URLs of JSON arrays to use
        urlList = new ArrayList<String>();
        urlList.add(getString(R.string.hot_food_url));
        urlList.add(getString(R.string.snacks_url));
        urlList.add(getString(R.string.drinks_url));
        urlList.add(getString(R.string.beer_url));

        // Titles of JSON arrays in corresponding urls
        arrayTitles = new ArrayList<String>();
        arrayTitles.add(getString(R.string.hot_food_array_title));
        arrayTitles.add(getString(R.string.snacks_array_title));
        arrayTitles.add(getString(R.string.drinks_array_title));
        arrayTitles.add(getString(R.string.beer_array_title));

        menuItemList = new ArrayList<MenuItem>();

        for(int i = 0; i < urlList.size(); i++) {
            AsyncRetrieval asyncRetrieval = new AsyncRetrieval(urlList.get(i));

            String jsonBody = null;
            JSONArray jsonArray = null;

            try {
                jsonBody = asyncRetrieval.execute().get();
                jsonArray = asyncRetrieval.getJsonArray(jsonBody, arrayTitles.get(i));
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            catch(ExecutionException e) {
                e.printStackTrace();
            }

            menuItemList.addAll(MenuItem.fromJSON(jsonArray));
        }

        favoritesManager = new FavoritesManager(getContext(), menuItemList);

        itemAdapter = new ItemAdapter(this.getContext(), favoritesManager.getAllFavorites(), true);
        lvItems = (ListView) view.findViewById(R.id.lv_items_favorites);
        lvItems.setAdapter(itemAdapter);

        return view;
    }

}
