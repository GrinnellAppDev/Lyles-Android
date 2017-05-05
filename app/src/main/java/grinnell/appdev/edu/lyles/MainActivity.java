package grinnell.appdev.edu.lyles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import grinnell.appdev.edu.lyles.fragments.BeerFragment;
import grinnell.appdev.edu.lyles.fragments.MenuFragment;
import grinnell.appdev.edu.lyles.fragments.ScheduleFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFragmentContainer((BottomNavigationView) findViewById(R.id.bottom_bar));
        changeFragment(new ScheduleFragment());

        setupToolbar();
    }

    private void setupFragmentContainer(BottomNavigationView bar) {
        bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int tabId = item.getItemId();
                Fragment fragment;
                switch(tabId) {
                    case R.id.schedule: fragment = new ScheduleFragment(); break;
                    case R.id.menu: fragment = new MenuFragment(); break;
                    case R.id.beer: fragment = new BeerFragment(); break;
                    default: fragment = new ScheduleFragment(); // something went wrong
                }
                changeFragment(fragment);
                return true;
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
