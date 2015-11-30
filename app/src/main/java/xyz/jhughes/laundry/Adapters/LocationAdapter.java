package xyz.jhughes.laundry.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import xyz.jhughes.laundry.LaundryParser.Constants;
import xyz.jhughes.laundry.MainActivity;
import xyz.jhughes.laundry.R;

import java.util.HashMap;

/**
 * Created by vieck on 10/29/15.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private Context mContext;

    private HashMap<String,Integer[]> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public LocationAdapter(HashMap<String,Integer[]> mDataset, Context mContext) {
        this.mContext = mContext;
        this.mDataset = mDataset;
    }

    public void setMachines(HashMap<String,Integer[]> mDataset) {
        this.mDataset.clear();
        this.mDataset = mDataset;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_location, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String location = Constants.getListOfRooms()[position];
        Integer[] count = mDataset.get(location);
        holder.location.setText(location);
        holder.dryerCount.setText(count[1] + "/" + count[0]);
        holder.washerCount.setText(count[3] + "/" + count[2]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sharedPreferenceEditor = mContext.getSharedPreferences("xyz.jhughes.laundry", mContext.MODE_PRIVATE).edit();
                sharedPreferenceEditor.putString("lastRoom",location);
                sharedPreferenceEditor.apply();
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.keySet().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public ImageView imageView;
        public TextView location, dryerCount, washerCount;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.card_view);
            imageView = (ImageView) v.findViewById(R.id.image_view_location);
            location = (TextView) v.findViewById(R.id.text_view_location_name);
            dryerCount = (TextView) v.findViewById(R.id.text_view_dryer_count);
            washerCount = (TextView) v.findViewById(R.id.text_view_washer_count);
        }
    }

}