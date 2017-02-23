package grinnell.appdev.edu.lyles.menuItems;

/**
 * Created by mattmurphy on 9/25/16.
 *
 * Interface to base all models of items to order off of menu
 * Each model must be used to fill adapter to display on Lyle's menu
 */

public interface OrderItem {

    String getName();

    String getDescription();

    void setName(String name);

    void setDescription(String description);

    double getPrice();

    void setPrice(double price);

    String getImageResource();

}
