package grinnell.appdev.edu.lyles.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import grinnell.appdev.edu.lyles.AsyncRetrieval;
import grinnell.appdev.edu.lyles.ItemAdapter;
import grinnell.appdev.edu.lyles.Constants;
import grinnell.appdev.edu.lyles.MenuItem;
import grinnell.appdev.edu.lyles.R;
import grinnell.appdev.edu.lyles.preferences.FavoritesManager;

/**
 * Created by Mattori on 5/9/16.
 */
public class FavoritesFragment extends Fragment {

    private ArrayList<String> mAllURLs;
    private ArrayList<String> mAllArrayTitles;

    private ArrayList<MenuItem> mAllMenuItems; // Contains all menu items in all categories
    private ItemAdapter mItemAdapter;
    private RecyclerView mRecyclerView;

    private FavoritesManager mFavoritesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_layout, container, false);

        setUpConstantArrays();
        mAllMenuItems = allMenuItems(mAllURLs, mAllArrayTitles);

        mFavoritesManager = new FavoritesManager(getContext(), mAllMenuItems);
        mItemAdapter = new ItemAdapter(this.getContext(), mFavoritesManager.getAllFavorites(), true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_items_favorites);
        mRecyclerView.setAdapter(mItemAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAllURLs = null;
        mAllArrayTitles = null;

        mAllMenuItems = null;
        mItemAdapter = null;
        mRecyclerView = null;

        mFavoritesManager = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAllURLs = null;
        mAllArrayTitles = null;

        mAllMenuItems = null;
        mItemAdapter = null;
        mRecyclerView = null;

        mFavoritesManager = null;
    }

    /**
     * Adds all constants corresponding urls and array keys into arrays
     */

    private void setUpConstantArrays() {
        // URLs of JSON arrays to use
        mAllURLs = new ArrayList<String>();
        mAllURLs.add(Constants.HOT_FOOD_URL);
        mAllURLs.add(Constants.SNACKS_URL);
        mAllURLs.add(Constants.DRINKS_URL);
        mAllURLs.add(Constants.BEER_URL);

        // Titles of JSON arrays in corresponding urls
        mAllArrayTitles = new ArrayList<String>();
        mAllArrayTitles.add(Constants.HOT_FOOD_ARRAY_KEY);
        mAllArrayTitles.add(Constants.SNACKS_ARRAY_KEY);
        mAllArrayTitles.add(Constants.DRINKS_ARRAY_KEY);
        mAllArrayTitles.add(Constants.BEER_ARRAY_KEY);
    }

    private ArrayList<MenuItem> allMenuItems(ArrayList<String> urls, ArrayList<String> keys) {

        ArrayList<MenuItem> returnList = new ArrayList<>();

        for(int i = 0; i < urls.size(); i++) {
            AsyncRetrieval asyncRetrieval = new AsyncRetrieval(urls.get(i));

            String jsonBody = "";
            JSONArray jsonArray = new JSONArray();

            try {
                jsonBody = asyncRetrieval.execute().get();
                jsonArray = asyncRetrieval.getJsonArray(jsonBody, keys.get(i));
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
            catch(ExecutionException e) {
                e.printStackTrace();
            }

            returnList.addAll(MenuItem.fromJSON(jsonArray));
        }

        return returnList;
    }
}
