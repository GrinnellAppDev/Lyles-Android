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
 */

public class FavoritesManager {

    private static final String PREFS_KEY = "Preferences";

    private SharedPreferences favorites;
    private SharedPreferences.Editor editor;
    private ArrayList<MenuItem> allItems;

    public FavoritesManager(Context context, ArrayList<MenuItem> menuItems) {

        allItems = menuItems;
        favorites = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        editor = favorites.edit();

        Log.i("preferences", "sharedpref instance and editor created");

        for(int i = 0; i < allItems.size(); i++) {
            String title = allItems.get(i).title;

            if(!favorites.contains(title)) {
                editor.putBoolean(title, false);
                editor.apply();
            }
        }
    }

    public void toggleFavorite(String id) {
        boolean newValue = !favorites.getBoolean(id, false);

        editor.remove(id);
        editor.apply();
        editor.putBoolean(id, newValue);
        editor.apply();
    }

    public boolean isFavorite(String id) {
        return favorites.getBoolean(id, false);
    }

    public ArrayList<MenuItem> getAllFavorites() {
        ArrayList<MenuItem> returnList = new ArrayList<MenuItem>();
        for(int i = 0; i < allItems.size(); i++) {
            if(favorites.getBoolean(allItems.get(i).title, false)) {
                returnList.add(allItems.get(i));
            }
        }
        return returnList;
    }

    public void clearFavorites() {
        editor.clear();
        editor.apply();
    }

    public String getButtonText(String id) {
        if(favorites.getBoolean(id, false))
            return "Unfavorite";
        else
            return "Favorite";
    }

    public String getFile() {
        return favorites.getAll().toString();
    }

}
