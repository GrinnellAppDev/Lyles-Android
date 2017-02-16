package grinnell.appdev.edu.lyles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A model to describe menu items
 *
 * @author Shelby Frazier
 */

public class MenuItem {
    private static final double DEFAULT_PRICE = 0;

    private String mTitle;
    private double mPrice;
    private String mImageUrl;
    private String mDetails;

    public MenuItem() {
        mTitle = "";
        mPrice = DEFAULT_PRICE;
        mImageUrl = "";
        mDetails = "";
    }

    public MenuItem(JSONObject jsonObject) {
        try {
            this.mTitle = jsonObject.getString(JSONConstants.TITLE_KEY);
            this.mPrice = jsonObject.getDouble(JSONConstants.PRICE_KEY);
            this.mImageUrl = jsonObject.getString(JSONConstants.IMAGE_URL_KEY);
            this.mDetails = jsonObject.getString(JSONConstants.DETAILS_KEY);
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
