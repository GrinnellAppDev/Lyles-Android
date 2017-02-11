package grinnell.appdev.edu.lyles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shelby on 2/3/2017.
 */

public class MenuItem {
    private String title;
    private double price;
    private String imageUrl;
    private String details;

    public MenuItem() {
        title = null;
        price = 0;
        imageUrl = null;
        details = null;
    }

    public MenuItem(JSONObject jsonObject) {
        try {
            this.title = jsonObject.getString("title");
            this.price = jsonObject.getDouble("price");
            this.imageUrl = jsonObject.getString("image");
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

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDetails() {
        return details;
    }
}
