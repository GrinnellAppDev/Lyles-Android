package grinnell.appdev.edu.lyles.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import grinnell.appdev.edu.lyles.R;

/**
 * Created by nannan on 2017/2/12.
 */

public class BeersAdaptor extends ArrayAdapter<Beers> {

    ArrayList<Beers> ArrayListBeers;
    int Resource;
    Context context;
    LayoutInflater vi;

    public BeersAdaptor(Context context, int resource, ArrayList<Beers> objects) {
        super(context, resource, objects);

        ArrayListBeers = objects;
        Resource = resource;
        this.context=context;

        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            convertView = vi.inflate(Resource, null);
            holder = new ViewHolder();

            holder.ivImage =(ImageView)convertView.findViewById(R.id.ivImage);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            holder.tvSubtitle = (TextView)convertView.findViewById(R.id.tvSubtitle);
            holder.tvPrice = (TextView)convertView.findViewById(R.id.tvPrice);
            holder.tvDetails = (TextView)convertView.findViewById(R.id.tvDetails);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        new LoadingImage(holder.ivImage).execute(ArrayListBeers.get(position).getImage());
        holder.tvTitle.setText(ArrayListBeers.get(position).getTitle());
        holder.tvSubtitle.setText(ArrayListBeers.get(position).getSubtitle());
        holder.tvPrice.setText("Price: $"+ArrayListBeers.get(position).getPrice());
        holder.tvDetails.setText("Details: "+ArrayListBeers.get(position).getDetails());
        return convertView;
    }

    static class ViewHolder{
        public ImageView ivImage;
        public TextView tvTitle;
        public TextView tvSubtitle;
        public TextView tvPrice;
        public TextView tvDetails;

    }

    public class LoadingImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadingImage(ImageView bmImage){
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap Icon =null;
            try{
                InputStream in = new java.net.URL(urldisplay).openStream();
                Icon = BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Icon;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            bmImage.setImageBitmap(bitmap);
        }
    }



}
