package no.dat153.quizzler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import no.dat153.quizzler.R;

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.ViewHolder> {

    private static final String TAG = "GalleryItemAdapter";
    private @DrawableRes Integer[] imageList;
    private Context context;

    public GalleryItemAdapter(Context context, @DrawableRes Integer... imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(imageList[position]); // Example with drawable
    }

    @Override
    public int getItemCount() {
        return imageList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "GalleryItemAdapter.ViewHolder";
        ImageView imageView;
        TextView textView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardImage);
            textView = itemView.findViewById(R.id.cardText);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

