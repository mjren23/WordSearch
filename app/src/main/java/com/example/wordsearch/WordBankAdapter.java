package com.example.wordsearch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WordBankAdapter extends RecyclerView.Adapter<WordBankAdapter.ViewHolder> {

    private ArrayList<HashMap<String, HashSet<Point>>> wordBank;
    private LayoutInflater inflater;
    private Context context;


    public WordBankAdapter(Context context, ArrayList<HashMap<String, HashSet<Point>>> bank) {
//        Log.d("GridAdapter", "inside constructor");
        this.wordBank = bank;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("GridAdapter", "inside onCreateViewHolder");
        View view = inflater.inflate(R.layout.wordbank_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return wordBank.size();
    }

    public void markWordFound(ViewHolder holder) {
        holder.wordBankItem.setTextColor(Color.parseColor("green"));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wordBankItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordBankItem = itemView.findViewById(R.id.wordBankItem);
//            Log.d("GridAdapter", "inside ViewHolder constructor");
        }

        public void bind(int position) {
            String word = "";
            for (String key : wordBank.get(position).keySet()) {
                word = key;
            }
//            Log.d("GridAdapter", "word: " + word);
            this.wordBankItem.setText(word);
        }
    }
}
