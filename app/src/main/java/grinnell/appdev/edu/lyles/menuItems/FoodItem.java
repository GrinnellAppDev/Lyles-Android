package grinnell.appdev.edu.lyles.menuItems;

/**
 * Created by mattmurphy on 9/25/16.
 */

public class FoodItem implements OrderItem {

    String mName;
    String mDescription;
    double mPrice;

    public FoodItem(){}

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

    // TODO: create switch statements
    public String getImageResource() {
        return null;
    }
}
