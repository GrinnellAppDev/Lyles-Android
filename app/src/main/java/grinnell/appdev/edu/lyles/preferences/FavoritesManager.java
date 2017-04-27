package grinnell.appdev.edu.lyles.preferences;

import android.content.Context;
import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;

import java.util.ArrayList;

import grinnell.appdev.edu.lyles.MenuItem;

/**
 * A class which uses SharedPreferences to keep track of each user's favorite items in each category
 *
 * @author Shelby Frazier
 */

public class FavoritesManager {

    private static final String PREFS_KEY = "Preferences";

    private SharedPreferences mPreferencesFile;
    private SharedPreferences.Editor mPreferencesEditor;
    private ArrayList<MenuItem> mAllItemsArrayList;

    /**
     * Creates a new favorites manager based on an Arraylist of menuItems
     *
     * @param context   the context of activity using the FavoritesManager
     * @param menuItems the list of all items the FavoritesManager will keep track of
     */
    public FavoritesManager(Context context, ArrayList<MenuItem> menuItems) {
        mAllItemsArrayList = menuItems;
        mPreferencesFile = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        mPreferencesEditor = mPreferencesFile.edit();

        for(int i = 0; i < mAllItemsArrayList.size(); i++) {
            String title = mAllItemsArrayList.get(i).getTitle();

            if(!mPreferencesFile.contains(title)) {
                mPreferencesEditor.putBoolean(title, false);
                mPreferencesEditor.apply();
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
        boolean newValue = !mPreferencesFile.getBoolean(id, false);
        mPreferencesEditor.putBoolean(id, newValue);
        mPreferencesEditor.apply();
    }

    /**
     * Determines whether an item of id id has been favorited by the user
     *
     * @param  id the id of the item being investigated
     * @return    a boolean describing whether the item has been favorited
     */
    public boolean isFavorite(String id) {
        return mPreferencesFile.getBoolean(id, false);
    }

    /**
     * Retrieves an ArrayList containing all items the user has favorited
     * This should be called by FavoritesFragment when providing a list of elements to the adapter
     *
     * @return     an ArrayList containing all favorited items
     */
    public ArrayList<MenuItem> getAllFavorites() {
        ArrayList<MenuItem> returnList = new ArrayList<MenuItem>();
        for(int i = 0; i < mAllItemsArrayList.size(); i++) {
            if(mPreferencesFile.getBoolean(mAllItemsArrayList.get(i).getTitle(), false)) {
                returnList.add(mAllItemsArrayList.get(i));
            }
        }
        return returnList;
    }

    /**
     * Unfavorites all items the user has favorited
     */
    public void clearFavorites() {
        for(int i = 0; i < mAllItemsArrayList.size(); i++) {
            mPreferencesEditor.putBoolean(mAllItemsArrayList.get(i).getTitle(), false);
        }
        mPreferencesEditor.apply();
    }

    /**
     * Retrieve the information contained in the file corresponding to SharedPreferences
     *
     * @return     the contents of the SharedPreferences file as a String
     */
    public String getFile() {
        return mPreferencesFile.getAll().toString();
    }
}
