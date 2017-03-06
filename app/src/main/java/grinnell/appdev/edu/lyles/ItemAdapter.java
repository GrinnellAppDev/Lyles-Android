package grinnell.appdev.edu.lyles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import grinnell.appdev.edu.lyles.preferences.FavoritesManager;

/**
 * An adapter for translating between an ArrayAdapter for MenuItems to a ListView
 *
 * @author Shelby Frazier
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView priceTextView;
        public ImageButton favoriteButton;
        public TextView detailsTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.tvTitle);
            priceTextView = (TextView) itemView.findViewById(R.id.tvPrice);
            favoriteButton = (ImageButton) itemView.findViewById(R.id.btnFavorite);
            detailsTextView = (TextView) itemView.findViewById(R.id.tvDetails);
        }
    }

    private static final int RESOURCE_ID = 0;
    private static final String DOLLAR_SIGN = "$";

    private Context mContext;
    private ArrayList<MenuItem> mMenuItems;

    private FavoritesManager mFavoritesManager;
    private boolean mIsFavoritesTabClicked;

    public ItemAdapter(Context context, ArrayList<MenuItem> menuItems, boolean favTab) {
        mContext = context;
        mMenuItems = menuItems;
        mFavoritesManager = new FavoritesManager(context, menuItems);
        mIsFavoritesTabClicked = favTab;
    }

    private Context getContext() {
        return mContext;
    }


    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View menuItemView = inflater.inflate(R.layout.item_user_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(menuItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final MenuItem menuItem = mMenuItems.get(position);
        final int itemPosition = position;

        TextView titleTextView = viewHolder.titleTextView;
        titleTextView.setText(menuItem.getTitle());

        TextView priceTextView = viewHolder.priceTextView;
        priceTextView.setText(DOLLAR_SIGN + menuItem.getPrice());

        ImageButton favoriteButton = viewHolder.favoriteButton;
        //favoriteButton.setText(mFavoritesManager.getButtonText(menuItem.getTitle()));
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavoritesManager.toggleFavorite(menuItem.getTitle());
                //favoriteButton.setText(mFavoritesManager.getButtonText(menuItem.getTitle()));

                if (mIsFavoritesTabClicked) {
                    mMenuItems.remove(menuItem);
                    notifyItemRemoved(itemPosition);
                }
            }
        });

        TextView detailsTextView = viewHolder.detailsTextView;
        detailsTextView.setText(menuItem.getDetails());
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }
}
