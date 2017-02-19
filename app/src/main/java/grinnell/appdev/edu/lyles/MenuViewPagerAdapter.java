package grinnell.appdev.edu.lyles;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import grinnell.appdev.edu.lyles.fragments.FavoritesFragment;
import grinnell.appdev.edu.lyles.fragments.MenuTabFragment;

/**
 * A {@code FragmentStatePagerAdapter} to handle the display of the various {@code Fragment}s,
 * dispatching the correct {@code Fragment} and title name.
 *
 * Created by Mattori on 9/25/16.
 */
public class MenuViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int HOT_FOOD_INDEX = 0;
    public static final int SNACKS_INDEX = 1;
    public static final int DRINKS_INDEX = 2;
    public static final int FAVORITES_INDEX = 3;
    public static final int NUM_TABS = 4;

    private final Resources mResources;

    public MenuViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mResources = context.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == FAVORITES_INDEX)
            return new FavoritesFragment();
        return new MenuTabFragment(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case HOT_FOOD_INDEX: return mResources.getString(R.string.hot_food_tab_title);
            case SNACKS_INDEX: return mResources.getString(R.string.snacks_tab_title);
            case DRINKS_INDEX: return mResources.getString(R.string.drinks_tab_title);
            case FAVORITES_INDEX: return mResources.getString(R.string.favorites_tab_title);
            default: return "";
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}
