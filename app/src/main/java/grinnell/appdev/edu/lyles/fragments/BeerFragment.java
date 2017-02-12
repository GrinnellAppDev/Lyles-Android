package grinnell.appdev.edu.lyles.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import grinnell.appdev.edu.lyles.R;

public class BeerFragment extends AppCompatActivity {

    ListView list;
    BeersAdaptor adaptor;
    ArrayList<Beers> beersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beer_layout);

        list = (ListView) findViewById(R.id.lvBeers);
        beersList = new ArrayList<Beers>();

        new BeersAsynTask().execute("http://www.cs.grinnell.edu/~birnbaum/appdev/lyles/beer.json");

    }



    public class BeersAsynTask extends AsyncTask<String, Void, Boolean> {

        HttpURLConnection urlConnection;

        @Override
        protected Boolean doInBackground(String... params) {

            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL("http://www.cs.grinnell.edu/~birnbaum/appdev/lyles/beer.json");
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }

            String data = result.toString();
            try  {
                JSONObject beerInformation = new JSONObject(data);
                JSONArray beers = beerInformation.getJSONArray("beer");

                for (int i=0;i<beers.length();i++){
                    Beers beer = new Beers();

                    JSONObject aBeer = beers.getJSONObject(i);

                    beer.setTitle(aBeer.getString("title"));
                    beer.setSubtitle(aBeer.getString("subtitle"));
                    beer.setPrice(aBeer.getDouble("price"));
                    beer.setImage(aBeer.getString("image"));
                    beer.setDetails(aBeer.getString("details"));

                    beersList.add(beer);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(!result){
                // print message
            }else {
                BeersAdaptor adaptor = new BeersAdaptor(getApplicationContext(),R.layout.beer_row,beersList);
                list.setAdapter(adaptor);

            }
        }
    }


}




