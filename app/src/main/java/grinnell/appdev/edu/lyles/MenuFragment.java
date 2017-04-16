package grinnell.appdev.edu.lyles;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A generalized fragment to be used to display a list of menu items to the user
 */

public class MenuFragment extends Fragment {

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

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String url = (String) this.getArguments().get("url");
        String arrayKey = (String) this.getArguments().get("arrayKey");
        int layoutXml = (int) this.getArguments().get("layoutXml");
        int recyclerViewId = (int) this.getArguments().get("recyclerViewId");

        View view = inflater.inflate(layoutXml, container, false);
        mMenuItemArrayList = MenuItem.fromJSON(getMenuItemsAsJsonArray(url, arrayKey));
        mItemAdapter = new ItemAdapter(this.getContext(), mMenuItemArrayList, false);
        mRecyclerView = (RecyclerView) view.findViewById(recyclerViewId);
        mRecyclerView.setAdapter(mItemAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

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
     * Asynchronously retrieve items using url constant and return a JsonArray
     *
     * @return a JsonArray retrieved asynchronously using url constant
     */
    private JSONArray getMenuItemsAsJsonArray(String url, String arrayKey) {
        AsyncRetrieval asyncRetrieval = new AsyncRetrieval(url);
        String jsonBody = Constants.EMPTY_STRING;

        try {
            jsonBody = asyncRetrieval.execute().get();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }

        return asyncRetrieval.getJsonArray(jsonBody, arrayKey);
    }

}
