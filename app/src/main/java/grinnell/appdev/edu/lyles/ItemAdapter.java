package grinnell.appdev.edu.lyles;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import grinnell.appdev.edu.lyles.preferences.FavoritesManager;

/**
 * An adapter for translating between an ArrayAdapter for MenuItems to a ListView
 *
 * @author Shelby Frazier
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTitleTextView;
        public TextView mPriceTextView;
        public ImageView mImageView;
        public ImageButton mFavoriteButton;
        public TextView mDetailsTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tvTitle);
            mPriceTextView = (TextView) itemView.findViewById(R.id.tvPrice);
            mImageView = (ImageView) itemView.findViewById(R.id.ivFood);
            mFavoriteButton = (ImageButton) itemView.findViewById(R.id.btnFavorite);
            mDetailsTextView = (TextView) itemView.findViewById(R.id.tvDetails);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mExpandedIndex != -1) {
                this.getAdapter().expandContractItem((ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(mExpandedIndex));
            }
            this.getAdapter().expandContractItem(this);
            mExpandedIndex = (mExpandedIndex == this.getAdapterPosition())? -1: this.getAdapterPosition();
        }

        public ItemAdapter getAdapter() {
            return ItemAdapter.this;
        }
    }

    private static final int RESOURCE_ID = 0;
    private static final String DOLLAR_SIGN = "$";

    private Context mContext;
    private ArrayList<MenuItem> mMenuItems;

    private FavoritesManager mFavoritesManager;
    private boolean mIsFavoritesTabClicked;

    private int mExpandedIndex = -1;
    private RecyclerView mRecyclerView;

    public ItemAdapter(Context context, ArrayList<MenuItem> menuItems, boolean favTab) {
        mContext = context;
        mMenuItems = menuItems;
        mFavoritesManager = new FavoritesManager(context, menuItems);
        mIsFavoritesTabClicked = favTab;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mRecyclerView = (RecyclerView) parent;

        View menuItemView = inflater.inflate(R.layout.item_user_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(menuItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final MenuItem menuItem = mMenuItems.get(position);
        final int itemPosition = position;

        TextView titleTextView = viewHolder.mTitleTextView;
        titleTextView.setText(menuItem.getTitle());

        TextView priceTextView = viewHolder.mPriceTextView;
        priceTextView.setText(DOLLAR_SIGN + menuItem.getPrice());

        ImageView imageView = viewHolder.mImageView;
        Glide.with(this.getContext()).load(menuItem.getImageUrl())
                .override(300, 300).into(imageView);

        ImageButton favoriteButton = viewHolder.mFavoriteButton;
        setFaveButtonDrawable(viewHolder);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavoritesManager.toggleFavorite(menuItem.getTitle());
                setFaveButtonDrawable(viewHolder);
                if (mIsFavoritesTabClicked) {
                    mMenuItems.remove(menuItem);
                    notifyItemRemoved(itemPosition);
                }
            }
        });

        TextView detailsTextView = viewHolder.mDetailsTextView;
        detailsTextView.setText(menuItem.getDetails());
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }

    private Context getContext() {
        return mContext;
    }

    /**
     * Changes a view to or from its expanded mode
     *
     * @param  viewHolder  the ViewHolder to be altered
     */
    private void expandContractItem(ViewHolder viewHolder) {
        if (viewHolder.mDetailsTextView.getVisibility() == View.GONE) {
            viewHolder.mDetailsTextView.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.mDetailsTextView.setVisibility(View.GONE);
        }
        ObjectAnimator animator = ObjectAnimator.ofInt(viewHolder.mDetailsTextView, "maxLines", TextViewCompat.getMaxLines(viewHolder.mDetailsTextView));
        animator.setDuration(500).start();
    }

    private void setFaveButtonDrawable(ViewHolder viewHolder) {
        if (mFavoritesManager.isFavorite(viewHolder.mTitleTextView.getText().toString())) {
            viewHolder.mFavoriteButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_star_filled_in));
        }
        else {
            viewHolder.mFavoriteButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_star_unfilled));
        }
    }
}
