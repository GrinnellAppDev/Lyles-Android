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

import grinnell.appdev.edu.lyles.*;
import grinnell.appdev.edu.lyles.adapters.FoodMenuItemAdapter;
import grinnell.appdev.edu.lyles.models.LylesMenuItem;
import grinnell.appdev.edu.lyles.preferences.FavoritesManager;
import grinnell.appdev.edu.lyles.utils.AsyncRetrieval;

import static grinnell.appdev.edu.lyles.Constants.BEER_ARRAY_KEY;
import static grinnell.appdev.edu.lyles.Constants.BEER_URL;
import static grinnell.appdev.edu.lyles.Constants.DRINKS_ARRAY_KEY;
import static grinnell.appdev.edu.lyles.Constants.DRINKS_URL;
import static grinnell.appdev.edu.lyles.Constants.HOT_FOOD_ARRAY_KEY;
import static grinnell.appdev.edu.lyles.Constants.HOT_FOOD_URL;
import static grinnell.appdev.edu.lyles.Constants.SNACKS_ARRAY_KEY;
import static grinnell.appdev.edu.lyles.Constants.SNACKS_URL;
import static grinnell.appdev.edu.lyles.models.LylesMenuItem.fromJSON;
/**
 * Created by Mattori on 5/9/16.
 */
public class FavoritesFragment extends Fragment{

    private ArrayList<String> mAllURLs;
    private ArrayList<String> mAllArrayTitles;
    private ArrayList<LylesMenuItem> mAllLylesMenuItems; // Contains all menu items in all categories

    private FoodMenuItemAdapter mFoodMenuItemAdapter;
    private RecyclerView mRecyclerView;
    private FavoritesManager mFavoritesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_layout, container, false);
        mAllLylesMenuItems = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_items_favorites);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        setUpConstantArrays();
        getMenuItems(mAllURLs, mAllArrayTitles);

        return view;
    }

    @Override
    public void onDestroyView() {
        mAllURLs = null;
        mAllArrayTitles = null;
        mAllLylesMenuItems = null;

        mFoodMenuItemAdapter = null;
        mRecyclerView = null;
        mFavoritesManager = null;

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mAllURLs = null;
        mAllArrayTitles = null;
        mAllLylesMenuItems = null;

        mFoodMenuItemAdapter = null;
        mRecyclerView = null;
        mFavoritesManager = null;

        super.onDestroy();
    }

    /**
     * Adds all constants corresponding urls and array keys into arrays
     */
    private void setUpConstantArrays() {
        // URLs of JSON arrays to use
        mAllURLs = new ArrayList<>();
        mAllURLs.add(HOT_FOOD_URL);
        mAllURLs.add(SNACKS_URL);
        mAllURLs.add(DRINKS_URL);
        mAllURLs.add(BEER_URL);

        // Titles of JSON arrays in corresponding urls
        mAllArrayTitles = new ArrayList<>();
        mAllArrayTitles.add(HOT_FOOD_ARRAY_KEY);
        mAllArrayTitles.add(SNACKS_ARRAY_KEY);
        mAllArrayTitles.add(DRINKS_ARRAY_KEY);
        mAllArrayTitles.add(BEER_ARRAY_KEY);
    }

    /**
     * Using a list of urls and json keys, retrieves a jsonArray asynchronously and returns a list of all MenuItems
     *
     * @param urls an ArrayList of urls as Strings
     * @param keys an ArrayList of keys for parsing the Json Array as strings
     */
    private void getMenuItems(ArrayList<String> urls, ArrayList<String> keys) {
        for(int i = 0; i < urls.size(); i++) {
            if (i == urls.size() - 1) {
                AsyncRetrieval asyncRetrieval = new AsyncRetrieval(urls.get(i), keys.get(i)) {
                    @Override
                    protected void onPostExecute(JSONArray jsonArray) {
                        super.onPostExecute(jsonArray);
                        mAllLylesMenuItems.addAll(fromJSON(jsonArray));
                        mFavoritesManager = new FavoritesManager(getContext(), mAllLylesMenuItems);
                        mFoodMenuItemAdapter = new FoodMenuItemAdapter(getContext(), mFavoritesManager.getAllFavorites(), true);
                        mRecyclerView.setAdapter(mFoodMenuItemAdapter);
                    }
                };
                asyncRetrieval.execute();
            }
            else {
                AsyncRetrieval asyncRetrieval = new AsyncRetrieval(urls.get(i), keys.get(i)) {
                    @Override
                    protected void onPostExecute(JSONArray jsonArray) {
                        super.onPostExecute(jsonArray);
                        mAllLylesMenuItems.addAll(fromJSON(jsonArray));
                    }
                };
                asyncRetrieval.execute();
            }
        }
    }
}
