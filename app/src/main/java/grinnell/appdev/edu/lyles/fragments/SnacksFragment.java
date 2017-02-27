package grinnell.appdev.edu.lyles.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import grinnell.appdev.edu.lyles.AsyncRetrieval;
import grinnell.appdev.edu.lyles.ItemAdapter;
import grinnell.appdev.edu.lyles.JSONConstants;
import grinnell.appdev.edu.lyles.MenuItem;
import grinnell.appdev.edu.lyles.R;

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
        View view = inflater.inflate(R.layout.fragment_snacks, container, false);

        mAsyncRetrieval = new AsyncRetrieval(JSONConstants.SNACKS_URL);

        try {
            mJsonBody = mAsyncRetrieval.execute().get();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }

        mAllItemsAsJsonArray = mAsyncRetrieval.getJsonArray(mJsonBody, JSONConstants.SNACKS_ARRAY_KEY);
        mMenuItemArrayList = MenuItem.fromJSON(mAllItemsAsJsonArray);

        mItemAdapter = new ItemAdapter(this.getContext(), mMenuItemArrayList, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_items_snacks);
        mRecyclerView.setAdapter(mItemAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mAsyncRetrieval = null;
        mJsonBody = null;
        mAllItemsAsJsonArray = null;

        mMenuItemArrayList = null;
        mItemAdapter = null;
        mRecyclerView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mAsyncRetrieval = null;
        mJsonBody = null;
        mAllItemsAsJsonArray = null;

        mMenuItemArrayList = null;
        mItemAdapter = null;
        mRecyclerView = null;
    }
}
