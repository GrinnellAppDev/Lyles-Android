package grinnell.appdev.edu.lyles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import grinnell.appdev.edu.lyles.preferences.FavoritesManager;

/**
 * An adapter for translating between an ArrayAdapter for MenuItems to a ListView
 *
 * @author Shelby Frazier
 */

public class ItemAdapter extends ArrayAdapter<MenuItem> {

    private static final int RESOURCE_ID = 0;
    private static final String DOLLAR_SIGN = "$";

    private FavoritesManager mFavoritesManager;
    private boolean mIsFavoritesTabClicked;

    public ItemAdapter(Context context, ArrayList<MenuItem> menuItems, boolean favTab) {
        super(context, RESOURCE_ID, menuItems);

        mFavoritesManager = new FavoritesManager(context, menuItems);
        mIsFavoritesTabClicked = favTab;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MenuItem menuItem = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        final TextView titleTextView = (TextView) convertView.findViewById(R.id.tvTitle);
        final TextView priceTextView = (TextView) convertView.findViewById(R.id.tvPrice);
        final Button favoriteButton = (Button) convertView.findViewById(R.id.btnFavorite);

        titleTextView.setText(menuItem.getTitle());
        priceTextView.setText(DOLLAR_SIGN + menuItem.getPrice());
        favoriteButton.setText(mFavoritesManager.getButtonText(menuItem.getTitle()));

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavoritesManager.toggleFavorite(menuItem.getTitle());
                favoriteButton.setText(mFavoritesManager.getButtonText(menuItem.getTitle()));

                if (mIsFavoritesTabClicked) {
                    remove(menuItem);
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

}
