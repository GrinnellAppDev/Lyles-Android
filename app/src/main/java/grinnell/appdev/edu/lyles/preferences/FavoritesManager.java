package grinnell.appdev.edu.lyles.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import grinnell.appdev.edu.lyles.AsyncRetrieval;
import grinnell.appdev.edu.lyles.MenuItem;

/**
 * Created by Shelby on 2/3/2017.
 * A class which uses SharedPreferences to keep track of each user's favorites in each category
 */

public class FavoritesManager {

    private static final String PREFS_KEY = "Preferences";

    private SharedPreferences favorites;
    private SharedPreferences.Editor editor;
    private ArrayList<MenuItem> allItems;

    /**
     * Creates a new favorites manager based on an Arraylist of menuItems
     *
     * @param context   the context of activity using the FavoritesManager
     * @param menuItems the list of all items the FavoritesManager will keep track of
     */
    public FavoritesManager(Context context, ArrayList<MenuItem> menuItems) {

        allItems = menuItems;
        favorites = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        editor = favorites.edit();

        for(int i = 0; i < allItems.size(); i++) {
            String title = allItems.get(i).getTitle();

            if(!favorites.contains(title)) {
                editor.putBoolean(title, false);
                editor.apply();
            }
        }
    }

    /**
     * Changes whether the item with id id is a favorites
     * This should be called by the click listener for any favorite button
     *
     * @param id the id of the item to be favorited or unfavorited
     */
    public void toggleFavorite(String id) {
        boolean newValue = !favorites.getBoolean(id, false);

        editor.remove(id);
        editor.apply();

        editor.putBoolean(id, newValue);
        editor.apply();
    }

    /**
     * Determines whether an item of id id has been favorited by the user
     *
     * @param  id the id of the item being investigated
     * @return    a boolean describing whether the item has been favorited
     */
    public boolean isFavorite(String id) {
        return favorites.getBoolean(id, false);
    }

    /**
     * Retrieves an ArrayList containing all items the user has favorited
     * This should be called by FavoritesFragment when providing a list of elements to the adapter
     *
     * @return     an ArrayList containing all favorited items
     */
    public ArrayList<MenuItem> getAllFavorites() {
        ArrayList<MenuItem> returnList = new ArrayList<MenuItem>();
        for(int i = 0; i < allItems.size(); i++) {
            if(favorites.getBoolean(allItems.get(i).getTitle(), false)) {
                returnList.add(allItems.get(i));
            }
        }
        return returnList;
    }

    /**
     * Unfavorites all items the user has favorited
     */
    public void clearFavorites() {
        for(int i = 0; i < allItems.size(); i++) {
            editor.putBoolean(allItems.get(i).getTitle(), false);
        }
        editor.apply();
    }

    /**
     * Determines text the favorite button should display (if displaying text)
     *
     * @param id the id of the item to being investigated
     * @return   String of text to be displayed
     */
    public String getButtonText(String id) {
        if(favorites.getBoolean(id, false))
            return "Unfavorite";
        else
            return "Favorite";
    }

    /**
     * Retrieve the information contained in the file corresponding to SharedPreferences
     *
     * @return     the contents of the SharedPreferences file as a String
     */
    public String getFile() {
        return favorites.getAll().toString();
    }

}
