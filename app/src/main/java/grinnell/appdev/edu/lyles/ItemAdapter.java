package grinnell.appdev.edu.lyles;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import grinnell.appdev.edu.lyles.preferences.FavoritesManager;

/**
 * Created by Shelby on 2/4/2017.
 */

public class ItemAdapter extends ArrayAdapter<MenuItem> {

    FavoritesManager favoritesManager;

    public ItemAdapter(Context context, ArrayList<MenuItem> menuItems) {
        super(context, 0, menuItems);
        favoritesManager = new FavoritesManager(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MenuItem menuItem = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        final Button btnFavorite = (Button) convertView.findViewById(R.id.btnFavorite);
        //ImageView imFood = (ImageView) convertView.findViewById(R.id.ivFood);

        tvTitle.setText(menuItem.title);
        tvPrice.setText("$" + menuItem.price);
        btnFavorite.setText(favoritesManager.getButtonText(menuItem.title));

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoritesManager.toggleFavorite(menuItem.title);
                btnFavorite.setText(favoritesManager.getButtonText(menuItem.title));
                //notifyDataSetChanged();
                Log.i("preferences", favoritesManager.getFile());
            }
        });
        return convertView;
    }

}
