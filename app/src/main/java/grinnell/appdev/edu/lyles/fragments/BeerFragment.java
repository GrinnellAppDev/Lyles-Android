package grinnell.appdev.edu.lyles.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import grinnell.appdev.edu.lyles.R;

/**
 * Created by Mattori on 5/9/16.
 */
public class BeerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.beer_layout, container, false);
    }

}
