package grinnell.appdev.edu.lyles;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

/**
 * This class adds information to each item in a menu tab list.
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

            InputStream imagedata = new AsyncRetrieval(item.getString("image")).execute().get().byteStream();
            image.setImageBitmap(BitmapFactory.decodeStream(imagedata));
        } catch (Exception e) {
        }
        return view;
    }
}
