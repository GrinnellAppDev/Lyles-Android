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
 * Created by Shelby on 2/3/2017.
 */

public class AsyncRetrieval extends AsyncTask<Void, Void, String> {

    private final String url;
    private String body;

    public AsyncRetrieval(String url) {
        super();
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    @Override
    protected void onPostExecute(String aString) {
        super.onPostExecute(body);
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
