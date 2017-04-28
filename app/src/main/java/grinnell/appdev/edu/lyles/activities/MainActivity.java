package grinnell.appdev.edu.lyles.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import grinnell.appdev.edu.lyles.R;
import grinnell.appdev.edu.lyles.fragments.BeerFragment;
import grinnell.appdev.edu.lyles.fragments.FavoritesFragment;
import grinnell.appdev.edu.lyles.fragments.MenuTabFragment;
import grinnell.appdev.edu.lyles.fragments.ScheduleFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFragmentContainer((BottomBar) findViewById(R.id.bottom_bar));
        changeFragment(new ScheduleFragment());

        setupToolbar();
    }

    private void setupFragmentContainer(BottomBar bar) {
        bar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment fragment;
                switch(tabId) {
                    case R.id.tab_schedule: fragment = new ScheduleFragment(); break;
                    case R.id.tab_menu: fragment = new MenuTabFragment(); break;
                    case R.id.tab_beer: fragment = new BeerFragment(); break;
                    case R.id.tab_favorites: fragment = new FavoritesFragment(); break;
                    default: fragment = new ScheduleFragment(); // something went wrong
                }
                changeFragment(fragment);
            }
        });
    }

    private void changeFragment(Fragment target) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, target);
        transaction.commit();
    }

    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }
}
