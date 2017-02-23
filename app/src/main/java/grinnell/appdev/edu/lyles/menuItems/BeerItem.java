package grinnell.appdev.edu.lyles.menuItems;

/**
 * Model beer item. Contains information on beer name and beer description.
 * Used to fill beer adapter
 */
public class BeerItem implements OrderItem {

    private String mName;
    private String mDescription;
    private double mPrice;
    private boolean mFavorite;

    public BeerItem() {}

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

    public double getPrice() { return mPrice; }

    public void setPrice(double price) {
        mPrice = price;
    }

    public boolean isFavorite() { return mFavorite; }

    public void setFavorite(boolean b) { mFavorite = b; }

    public String getImageResource() {
        // TODO: gather image resources from beer name
        // completed from switch statement on possible names
        return null;
    }
}
