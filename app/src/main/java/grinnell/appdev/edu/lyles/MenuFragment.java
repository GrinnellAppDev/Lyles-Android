package grinnell.appdev.edu.lyles;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A generalized fragment to be used to display a list of menu items to the user
 */

public class MenuFragment extends android.support.v4.app.Fragment {

    private ArrayList<MenuItem> mMenuItemArrayList;
    private ItemAdapter mItemAdapter;
    private RecyclerView mRecyclerView;

    public MenuFragment() {
        // required empty public constructor
    }

    /**
     * Creates a new instance of the fragment (with parameters)
     * @param url               url of the jsonArray to retrieve data from
     * @param arrayKey          key of the array within the json file
     * @param layoutXml         xml where the layout of the fragment is located
     * @param recyclerViewId    id of the recycler view where menuItems should be placed
     *
     * @return                  newly created MenuFragment with parameters attached
     */
    public static MenuFragment newInstance(String url, String arrayKey, int layoutXml, int recyclerViewId) {
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("arrayKey", arrayKey);
        args.putInt("layoutXml", layoutXml);
        args.putInt("recyclerViewId", recyclerViewId);

        MenuFragment menuFragment = new MenuFragment();
        menuFragment.setArguments(args);
        return menuFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String url = (String) this.getArguments().get("url");
        String arrayKey = (String) this.getArguments().get("arrayKey");
        int layoutXml = (int) this.getArguments().get("layoutXml");
        int recyclerViewId = (int) this.getArguments().get("recyclerViewId");

        View view = inflater.inflate(layoutXml, container, false);
        mMenuItemArrayList = new ArrayList<MenuItem>();
        mRecyclerView = (RecyclerView) view.findViewById(recyclerViewId);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        retrieveMenuData(url, arrayKey);
        return view;
    }

    @Override
    public void onDestroyView() {
        mMenuItemArrayList = null;
        mItemAdapter = null;
        mRecyclerView = null;

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mMenuItemArrayList = null;
        mItemAdapter = null;
        mRecyclerView = null;

        super.onDestroy();
    }

    /**
     * Asynchronously retrieve json array from url titled arrayKey, pass data to MenuItem.fromJSON to fill
     * mMenuItemArrayList with corresponding menuItems
     *
     * @param url           url where the data is held
     * @param arrayKey      key of the json array containing the data
     */
    private void retrieveMenuData(String url, String arrayKey) {

        final AsyncRetrieval asyncRetrieval = new AsyncRetrieval(url, arrayKey) {
            @Override
            protected void onPostExecute(JSONArray result) {
                super.onPostExecute(result);
                Log.d("Async", result.toString());
                mMenuItemArrayList.addAll(MenuItem.fromJSON(result));
                mItemAdapter = new ItemAdapter(getContext(), mMenuItemArrayList, false);
                //mItemAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mItemAdapter);
//                mItemAdapter.notifyItemRangeInserted(0, mMenuItemArrayList.size());
            }
        };
        asyncRetrieval.execute();
    }
}
