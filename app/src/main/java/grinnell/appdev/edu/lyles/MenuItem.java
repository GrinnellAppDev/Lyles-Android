package grinnell.appdev.edu.lyles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static grinnell.appdev.edu.lyles.Constants.DEFAULT_PRICE;
import static grinnell.appdev.edu.lyles.Constants.DETAILS_KEY;
import static grinnell.appdev.edu.lyles.Constants.EMPTY_STRING;
import static grinnell.appdev.edu.lyles.Constants.IMAGE_URL_KEY;
import static grinnell.appdev.edu.lyles.Constants.PRICE_KEY;
import static grinnell.appdev.edu.lyles.Constants.TITLE_KEY;

/**
 * A model to describe menu items
 *
 * @author Shelby Frazier
 */

public class MenuItem {

    private String mTitle;
    private double mPrice;
    private String mImageUrl;
    private String mDetails;

    public MenuItem() {
        mTitle = EMPTY_STRING;
        mPrice = DEFAULT_PRICE;
        mImageUrl = EMPTY_STRING;
        mDetails = EMPTY_STRING;
    }

    public MenuItem(JSONObject jsonObject) {
        try {
            this.mTitle = jsonObject.getString(TITLE_KEY);
            this.mPrice = jsonObject.getDouble(PRICE_KEY);
            this.mImageUrl = jsonObject.getString(IMAGE_URL_KEY);
            this.mDetails = jsonObject.getString(DETAILS_KEY);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MenuItem> fromJSON(JSONArray jsonArray) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

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
        return mTitle;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getDetails() {
        return mDetails;
    }
}
