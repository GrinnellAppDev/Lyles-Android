package grinnell.appdev.edu.lyles.adapters;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import grinnell.appdev.edu.lyles.R;
import grinnell.appdev.edu.lyles.menuItems.BeerItem;

/**
 * Created by mattmurphy on 9/11/16.
 *
 * General adapter to be used by beer
 * adapter contains image, and description of catelog item
 */

public class BeerAdapter extends ArrayAdapter<BeerItem> {

    public BeerAdapter(Context context, int resource) {
        super(context, resource);
    }

    /**
     * TODO: set adapter content according to server
     *       learn how to gather information from server
     *
     * item picture
     * item description
     */

    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.beer_list_item, parent, false);
        }

        final BeerItem beer = BeerAdapter.super.getItem(pos);

        if (beer != null) {
            final ImageView beerImage = (ImageView) convertView.findViewById(R.id.beer_image);
            // TODO: figure out how to set beer images
            // beerImage.setImageDrawable((drawable) beer.getImageResource());

            final TextView  beerName  = (TextView)  convertView.findViewById(R.id.beer_name);
            beerName.setText(beer.getName());

            final TextView  beerType  = (TextView)  convertView.findViewById(R.id.beer_type);
            beerType.setText(beer.getDescription());

            final ImageButton beerFavorite = (ImageButton) convertView.findViewById(R.id.beer_favorite);
            if (beer.isFavorite()) {
                beerFavorite.setImageResource(R.drawable.ic_favorite_select);
                beerFavorite.setColorFilter(Color.parseColor("red"));
            } else {
                beerFavorite.setImageResource(R.drawable.ic_favorite_unselect);
                beerFavorite.clearColorFilter();
            }

            beerFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (beer.isFavorite()) {
                        beer.setFavorite(false);
                        beerFavorite.setImageResource(R.drawable.ic_favorite_unselect);
                    } else {
                        beer.setFavorite(true);
                        beerFavorite.setImageResource(R.drawable.ic_favorite_select);
                    }
                }
            });
        }

        return convertView;
    }
}
