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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static grinnell.appdev.edu.lyles.Constants.DOLLAR_SIGN;
import static grinnell.appdev.edu.lyles.Constants.IMAGE_DIMENSION;
import static grinnell.appdev.edu.lyles.Constants.MS_DURATION_ANIMATOR;
import static grinnell.appdev.edu.lyles.Constants.NONE_SELECTED;

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
            if (mExpandedIndex != NONE_SELECTED && mExpandedIndex != this.getAdapterPosition()) {
                this.getAdapter().expandContractItem((ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(mExpandedIndex));
            }
            this.getAdapter().expandContractItem(this);
            mExpandedIndex = (mExpandedIndex == this.getAdapterPosition())?  NONE_SELECTED: this.getAdapterPosition();
        }

        public ItemAdapter getAdapter() {
            return ItemAdapter.this;
        }
    }

    private Context mContext;
    private ArrayList<MenuItem> mMenuItems;

    private FavoritesManager mFavoritesManager;
    private boolean mIsFavoritesTabClicked;

    private int mExpandedIndex = NONE_SELECTED;
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
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final MenuItem menuItem = mMenuItems.get(position);
        final int itemPosition = position;

        TextView titleTextView = viewHolder.mTitleTextView;
        titleTextView.setText(menuItem.getTitle());

        TextView priceTextView = viewHolder.mPriceTextView;
        priceTextView.setText(DOLLAR_SIGN + menuItem.getPrice());

        ImageView imageView = viewHolder.mImageView;
        Glide.with(this.getContext()).load(menuItem.getImageUrl())
                .override(IMAGE_DIMENSION, IMAGE_DIMENSION).into(imageView);

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

                    if (mExpandedIndex != NONE_SELECTED) {
                        if (mExpandedIndex > position) {
                            mExpandedIndex--;
                        }
                        else if (mExpandedIndex == position) {
                            mExpandedIndex = NONE_SELECTED;
                        }
                    }
                    //notifyDataSetChanged();
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
        if (viewHolder.mDetailsTextView.getVisibility() == GONE) {
            viewHolder.mDetailsTextView.setVisibility(VISIBLE);
        }
        else {
            viewHolder.mDetailsTextView.setVisibility(GONE);
        }
        ObjectAnimator animator = ObjectAnimator.ofInt(viewHolder.mDetailsTextView, "maxLines", TextViewCompat.getMaxLines(viewHolder.mDetailsTextView));
        animator.setDuration(MS_DURATION_ANIMATOR).start();
    }

    /**
     * Change the drawable of the favorite button to the appropriate drawable
     *
     * @param viewHolder   the ViewHolder whose favorite button's drawable should be set
     */
    private void setFaveButtonDrawable(ViewHolder viewHolder) {
        if (mFavoritesManager.isFavorite(viewHolder.mTitleTextView.getText().toString())) {
            viewHolder.mFavoriteButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_star_filled_in));
        }
        else {
            viewHolder.mFavoriteButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_star_unfilled));
        }
    }
}
