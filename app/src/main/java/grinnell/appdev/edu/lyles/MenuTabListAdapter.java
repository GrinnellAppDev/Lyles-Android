package grinnell.appdev.edu.lyles;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * todo: find a way of adding list items that doesn't use an extra class
 */
public class MenuTabListAdapter extends ArrayAdapter<JSONObject> {
    public MenuTabListAdapter(Context context, List<JSONObject> items) {
        super(context, 0, items);
    }

    public View getView(int position, View view, ViewGroup parent) {
        JSONObject item = getItem(position);

        if(view == null) { /* not being reused */
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_menutab, parent, false);
        }

        TextView title = (TextView) view.findViewById(R.id.menutab_title);
        TextView price = (TextView) view.findViewById(R.id.menutab_price);
        TextView details = (TextView) view.findViewById(R.id.menutab_details);
        ImageView image = (ImageView) view.findViewById(R.id.menutab_image);

        try {
            title.setText(item.getString("title"));
            price.setText(item.getString("price"));
            details.setText(item.getString("details"));

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(new Request.Builder().url(item.getString("image")).build()).execute();
            image.setImageBitmap(BitmapFactory.decodeStream(response.body().byteStream()));
            response.close();

        } catch (IOException | JSONException e) {
            Log.d("error",e.getMessage());
;        }
        return view;
    }
}
