package grinnell.appdev.edu.lyles.menuItems;

/**
 * Created by mattmurphy on 9/25/16.
 */

public class DrinkItem {

    private String mName;
    private String mDescription;
    private double mPrice;

    public DrinkItem() {
        mName = null;
        mDescription = null;
        mPrice = 0.00;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }


    public String getImageResource() {
        String resName = null;

        return resName;
    }
}
