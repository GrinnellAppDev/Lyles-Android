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
    private ArrayList<String> allTitles;

    public FavoritesManager(Context context) {

        String jsonBody = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        AsyncRetrieval asyncRetrieval = new AsyncRetrieval();

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
        ArrayList<MenuItem> menuItemList = MenuItem.fromJSON(jsonArray);

        favorites = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        editor = favorites.edit();
        allTitles = new ArrayList<String>();
        Log.i("preferences", "sharedpref instance and editor created");

        //remove later
        editor.clear();

        for(int i = 0; i < jsonArray.length(); i++) {
            String title = null;
            try {
                title = jsonArray.getJSONObject(i).getString("title");
                allTitles.add(title);
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
            if(!favorites.contains(title)) {
                editor.putBoolean(title, false);
                editor.apply();
            }
        }
    }

    public void toggleFavorite(String id) {
        boolean newValue = !favorites.getBoolean(id, false);

        //editor.remove(id);
        //editor.apply();
        editor.putBoolean(id, newValue);
        editor.apply();
    }

    public boolean isFavorite(String id) {
        return favorites.getBoolean(id, false);
    }

    public ArrayList<String> getAllFavorites() {
        ArrayList<String> returnList = new ArrayList<String>();
        for(int i = 0; i < allTitles.size(); i++) {
            if(favorites.getBoolean(allTitles.get(i), false)) {
                returnList.add(allTitles.get(i));
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
