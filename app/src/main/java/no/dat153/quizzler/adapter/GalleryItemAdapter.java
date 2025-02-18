package no.dat153.quizzler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import no.dat153.quizzler.R;
import no.dat153.quizzler.entity.QuestionItem;

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.ViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private List<QuestionItem> questions;
    private final Consumer<Integer> longClickListener;

    /**
     * Konstruktør for adapter til Recyclerview i GalleryActivity
     *
     * @param context           Context som skal brukes
     * @param longClickListener Callback metode som blir kjørt når et element blir lang-trykket på
     */
    public GalleryItemAdapter(Context context, Consumer<Integer> longClickListener) {
        this.context = context;
        this.questions = List.of();
        this.longClickListener = longClickListener;
    }

    public void setQuestions(List<QuestionItem> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageURI(questions.get(position).getImageUri());
        holder.textView.setText(questions.get(position).getImageText());
        holder.cardView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.accept(holder.getAdapterPosition());
                return true; // Return true to indicate the event was handled
            }
            return false;
        });
    }

    public QuestionItem getItemAtPosition(int position) {
        return questions.get(position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
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

