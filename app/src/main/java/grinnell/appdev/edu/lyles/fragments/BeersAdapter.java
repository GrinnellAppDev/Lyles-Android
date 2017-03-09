package grinnell.appdev.edu.lyles.fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import grinnell.appdev.edu.lyles.R;

/**
 * Created by nannan on 2017/2/12.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public TextView tvSubtitle;
        public TextView tvPrice;
        public TextView tvDetails;
        public ImageView ivImage;
        public ImageButton btFavourite;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSubtitle = (TextView) itemView.findViewById(R.id.tvSubtitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvDetails = (TextView) itemView.findViewById(R.id.tvDetails);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            btFavourite = (ImageButton) itemView.findViewById(R.id.btFavourite);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (tvDetails.getVisibility() == View.GONE) {
                        // it's collapsed - expand it
                        tvDetails.setVisibility(View.VISIBLE);
                       // mDescriptionImg.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    } else {
                        // it's expanded - collapse it
                        tvDetails.setVisibility(View.GONE);
                        //mDescriptionImg.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    }

                    ObjectAnimator animation = ObjectAnimator.ofInt(tvDetails, "maxLines", tvDetails.getMaxLines());
                    animation.setDuration(200).start();

                }
            });
        }
    }
    // ... view holder defined above...

    // Store a member variable for the contacts
    private List<Beers> mBeers;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public BeersAdapter(BeerFragment context, List<Beers> Beers) {
        mBeers = Beers;
        mContext = context.getActivity().getApplicationContext();
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // ... constructor and member variables

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public BeersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View beerView = inflater.inflate(R.layout.beer_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(beerView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(BeersAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Beers beer = mBeers.get(position);

        // Set item views based on your views and data model
        TextView tvTitle = viewHolder.tvTitle;
        tvTitle.setText(beer.getTitle());
        TextView tvSubtitle = viewHolder.tvSubtitle;
        tvSubtitle.setText(beer.getSubtitle());
        TextView tvPrice = viewHolder.tvPrice;
        tvPrice.setText("$"+beer.getPrice());
        TextView tvDetails = viewHolder.tvDetails;
        tvDetails.setText("\nDetails: \n"+beer.getDetails()+"\n");
        ImageView imageView= viewHolder.ivImage;
        new LoadingImage(viewHolder.ivImage).execute(beer.getImage());
        ImageButton btFavourite = viewHolder.btFavourite;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mBeers.size();
    }

}
/*
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
*/
class LoadingImage extends AsyncTask<String, Void, Bitmap> {
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
