package grinnell.appdev.edu.lyles;

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

public class AsyncRetrieval extends AsyncTask<Void, Void, String> {

    private final String mURL;
    private String mJsonBody;


    public AsyncRetrieval(String url) {
        super();
        mURL = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(mURL).build();

        try {
            Response response = client.newCall(request).execute();
            mJsonBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJsonBody;
    }

    @Override
    protected void onPostExecute(String aString) {
        super.onPostExecute(mJsonBody);
    }

    public JSONArray getJsonArray(String jsonBody, String arrayTitle) {

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        try {
            jsonObject = new JSONObject(jsonBody);
            jsonArray = jsonObject.getJSONArray(arrayTitle);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }


}
