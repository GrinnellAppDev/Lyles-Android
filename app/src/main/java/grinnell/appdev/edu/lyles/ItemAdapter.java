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
 * Created by Shelby on 2/4/2017.
 */

public class ItemAdapter extends ArrayAdapter<MenuItem> {

    private FavoritesManager favoritesManager;
    private boolean isFavTab;

    public ItemAdapter(Context context, ArrayList<MenuItem> menuItems, boolean favTab) {
        super(context, 0, menuItems);

        favoritesManager = new FavoritesManager(context, menuItems);
        isFavTab = favTab;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MenuItem menuItem = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        final TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        final TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        final Button btnFavorite = (Button) convertView.findViewById(R.id.btnFavorite);

        tvTitle.setText(menuItem.getTitle());
        tvPrice.setText("$" + menuItem.getPrice());
        btnFavorite.setText(favoritesManager.getButtonText(menuItem.getTitle()));

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritesManager.toggleFavorite(menuItem.getTitle());
                btnFavorite.setText(favoritesManager.getButtonText(menuItem.getTitle()));
                if (isFavTab) {
                    remove(menuItem);
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

}
