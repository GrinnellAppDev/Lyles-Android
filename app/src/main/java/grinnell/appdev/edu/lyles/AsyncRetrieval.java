package grinnell.appdev.edu.lyles;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A class which asynchronously fetches and parses JSON files
 *
 * @author Shelby Frazier
 */

public class AsyncRetrieval extends AsyncTask<Void, Void, ResponseBody> {

    private final String mUrl;
    private ResponseBody mBody;


    public AsyncRetrieval(String url) {
        super();
        mUrl = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ResponseBody doInBackground(Void... voids) {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(mUrl).build();

        try {
            final Response response = client.newCall(request).execute();
            mBody = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mBody;
    }

    @Override
    protected void onPostExecute(ResponseBody aBody) {
        super.onPostExecute(mBody);
    }

    /* Parses the response as a JSON array. */
    public JSONArray getJsonArray(ResponseBody jsonBody, String arrayTitle) {

        JSONArray jsonArray = new JSONArray();

        try {
            JSONObject jsonObject = new JSONObject(jsonBody.string());
            jsonArray = jsonObject.getJSONArray(arrayTitle);
        }
        catch(IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }


}
