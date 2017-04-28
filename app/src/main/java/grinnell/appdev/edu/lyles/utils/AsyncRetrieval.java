package grinnell.appdev.edu.lyles.utils;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A class which asynchronously fetches and parses JSON files
 *
 * @author Shelby Frazier
 */

public class AsyncRetrieval extends AsyncTask<Void, Void, JSONArray> {

    private final String mUrl, mArrayKey;
    private String mJsonBody;

    public AsyncRetrieval(String url, String arrayKey) {
        super();
        mUrl = url;
        mArrayKey = arrayKey;
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(mUrl).build();
        try {
            final Response response = client.newCall(request).execute();
            mJsonBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getJsonArray(mJsonBody, mArrayKey);
    }
    
    /**
     * Parses a JSON array from a given string and returns it
     *
     * @param jsonBody   A string containing a jsonArray
     * @param arrayTitle The title of the jsonArray to retrieve
     * @return           The parsed jsonArray
     */
    public JSONArray getJsonArray(String jsonBody, String arrayTitle) {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(jsonBody);
            jsonArray = jsonObject.getJSONArray(arrayTitle);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
