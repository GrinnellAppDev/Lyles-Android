package grinnell.appdev.edu.lyles.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import grinnell.appdev.edu.lyles.MenuViewPagerAdapter;
import grinnell.appdev.edu.lyles.R;
import grinnell.appdev.edu.lyles.slidingtab.MenuTabColorizer;
import grinnell.appdev.edu.lyles.slidingtab.SlidingTabLayout;

/**
 * Contributors:
 *  Mattori Birnbaum
 *  Matt Murphy
 */
public class MenuFragment extends Fragment {
    private FragmentTabHost menuHost;

    public MenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_layout, container, false);

        setupViewPager(view);
        
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setupViewPager(View view) {
        final MenuViewPagerAdapter adapter = new MenuViewPagerAdapter(getFragmentManager(), getContext());
        final ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        final SlidingTabLayout tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new MenuTabColorizer(getContext()));

        tabs.setViewPager(pager);
    }
}
