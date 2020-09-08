package com.example.wordsearch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private char[][] letterBoard;
    private LayoutInflater inflater;
    private Context context;
    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onLetterClicked(int position);
    }

    public GridAdapter(Context context, char[][] letters) {
        Log.d("GridAdapter", "inside constructor");
        this.letterBoard = letters;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("GridAdapter", "inside onCreateViewHolder");
        View view = inflater.inflate(R.layout.letters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("GridAdapter", "inside onBindViewHolder");
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public void setClickListener(OnClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    public void clearLetter(ViewHolder holder) {
        holder.letter.setBackgroundColor(context.getResources().getColor(R.color.unhighlightedButton));

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView letter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("GridAdapter", "inside ViewHolder constructor");
            letter = itemView.findViewById(R.id.letter);
        }

        public void bind(int position) {
            Log.d("GridAdapter", "position: " + position + ", entry: " + position/10 + " " + (position%10));
            char toBind = letterBoard[position/10][Math.max(0, position%10)];
            this.letter.setText(Character.toString(toBind));
            this.letter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // handle color change of block
                    ColorDrawable letterColor = (ColorDrawable) letter.getBackground();
                    int colorID = letterColor.getColor();
                    if (colorID == context.getResources().getColor(R.color.unhighlightedButton)) {
                        // set to highlighted
                        letter.setBackgroundColor(context.getResources().getColor(R.color.highlightedButton));
                    }
                    else {
                        letter.setBackgroundColor(context.getResources().getColor(R.color.unhighlightedButton));
                    }

                    onClickListener.onLetterClicked(getAdapterPosition());
                }
            });
        }


    }
}
