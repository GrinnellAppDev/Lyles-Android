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

    private ArrayList<String> mAllURLs;
    private ArrayList<String> mAllTabTitles;

    private ArrayList<MenuItem> mAllMenuItems; // Contains all menu items in all categories
    private ItemAdapter mItemAdapter;
    private ListView mListView;

    private FavoritesManager mFavoritesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_layout, container, false);

        // URLs of JSON arrays to use
        mAllURLs = new ArrayList<String>();
        mAllURLs.add(getString(R.string.hot_food_url));
        mAllURLs.add(getString(R.string.snacks_url));
        mAllURLs.add(getString(R.string.drinks_url));
        mAllURLs.add(getString(R.string.beer_url));

        // Titles of JSON arrays in corresponding urls
        mAllTabTitles = new ArrayList<String>();
        mAllTabTitles.add(getString(R.string.hot_food_array_title));
        mAllTabTitles.add(getString(R.string.snacks_array_title));
        mAllTabTitles.add(getString(R.string.drinks_array_title));
        mAllTabTitles.add(getString(R.string.beer_array_title));

        mAllMenuItems = new ArrayList<MenuItem>();

        for(int i = 0; i < mAllURLs.size(); i++) {
            AsyncRetrieval asyncRetrieval = new AsyncRetrieval(mAllURLs.get(i));

            String jsonBody = null;
            JSONArray jsonArray = null;

            try {
                jsonBody = asyncRetrieval.execute().get();
                jsonArray = asyncRetrieval.getJsonArray(jsonBody, mAllTabTitles.get(i));
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            catch(ExecutionException e) {
                e.printStackTrace();
            }

            mAllMenuItems.addAll(MenuItem.fromJSON(jsonArray));
        }

        mFavoritesManager = new FavoritesManager(getContext(), mAllMenuItems);

        mItemAdapter = new ItemAdapter(this.getContext(), mFavoritesManager.getAllFavorites(), true);
        mListView = (ListView) view.findViewById(R.id.lv_items_favorites);
        mListView.setAdapter(mItemAdapter);

        return view;
    }

}
