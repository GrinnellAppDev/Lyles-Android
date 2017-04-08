package grinnell.appdev.edu.lyles.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import grinnell.appdev.edu.lyles.AsyncRetrieval;
import grinnell.appdev.edu.lyles.ItemAdapter;
import grinnell.appdev.edu.lyles.Constants;
import grinnell.appdev.edu.lyles.MenuItem;
import grinnell.appdev.edu.lyles.R;

import static grinnell.appdev.edu.lyles.R.layout.fragment_snacks;

/**
 * A fragment to display available hot food to user
 */
public class SnacksFragment extends Fragment {

    private AsyncRetrieval mAsyncRetrieval;
    private String mJsonBody;
    private JSONArray mAllItemsAsJsonArray;

    private ArrayList<MenuItem> mMenuItemArrayList;
    private ItemAdapter mItemAdapter;
    private RecyclerView mRecyclerView;

    public SnacksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_snacks, container, false);

        mMenuItemArrayList = MenuItem.fromJSON(getMenuItemsAsJsonArray());

        mItemAdapter = new ItemAdapter(this.getContext(), mMenuItemArrayList, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_items_snacks);
        mRecyclerView.setAdapter(mItemAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    @Override
    public void onDestroyView() {
        mAsyncRetrieval = null;
        mJsonBody = null;
        mAllItemsAsJsonArray = null;

        mMenuItemArrayList = null;
        mItemAdapter = null;
        mRecyclerView = null;

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mAsyncRetrieval = null;
        mJsonBody = null;
        mAllItemsAsJsonArray = null;

        mMenuItemArrayList = null;
        mItemAdapter = null;
        mRecyclerView = null;

        super.onDestroy();
    }

    /**
     * Asynchronously retrieve items using url constant and return a JsonArray
     *
     * @return a JsonArray retrieved asynchronously using url constant
     */

    private JSONArray getMenuItemsAsJsonArray() {
        AsyncRetrieval asyncRetrieval = new AsyncRetrieval(Constants.SNACKS_URL);
        String jsonBody = "";

        try {
            jsonBody = asyncRetrieval.execute().get();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }

        return asyncRetrieval.getJsonArray(jsonBody, Constants.SNACKS_ARRAY_KEY);
    }
}
