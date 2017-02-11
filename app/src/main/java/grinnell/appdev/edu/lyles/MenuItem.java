package grinnell.appdev.edu.lyles;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import grinnell.appdev.edu.lyles.preferences.FavoritesManager;

/**
 * Created by Shelby on 2/3/2017.
 */

public class MenuItem {
    public String title;
    public double price;
    public String image;
    public String details;

    public MenuItem() {
        title = null;
        price = 0;
        image = null;
        details = null;
    }

    public MenuItem(JSONObject jsonObject) {
        try {
            this.title = jsonObject.getString("title");
            this.price = jsonObject.getDouble("price");
            this.image = jsonObject.getString("image");
            this.details = jsonObject.getString("details");
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MenuItem> fromJSON(JSONArray jsonArray) {
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                menuItems.add(new MenuItem(jsonArray.getJSONObject(i)));
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }

        return menuItems;
    }
}
