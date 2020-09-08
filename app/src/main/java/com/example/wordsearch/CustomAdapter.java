package com.example.wordsearch;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomAdapter extends BaseAdapter {

    private char[][] letters;
    private LayoutInflater thisInflater;
    private Context context;

    public CustomAdapter(Context context, char[][] letterBoard) {
        super();
        this.context = context;
        letters = letterBoard;
        thisInflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("CustomAdapter", "entered constructor!");
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return letters[position/10][position%10-1];
    }

    public Object getItem(int row, int col) {
        return letters[row][col];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return null;
//        Log.d("CustomAdapter", "inside getView");
//        TextView textView;
        int row = position/10;
        int col = position%10-1;
//
//        if (convertView == null ) {
//            textView = new TextView(context);
//            textView = (TextView) thisInflater.inflate(R.layout.letters, parent);
//            Log.v("CustomAdapter", "inflated view!");
//        }
//        else {
//            textView = (TextView) convertView;
//        }
//
//        textView.setText(letters[row][col]);
//
//
//        return textView;

        ViewHolder viewHolder = null;
        if(convertView == null){

            viewHolder = new ViewHolder();
            TextView textView = new TextView(context);
            textView.setText("a");
            convertView = thisInflater.inflate(R.layout.letters, parent, false);

            viewHolder.textView = textView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }


        return convertView;

//        Log.d("CustomAdapter", "parent id: " + Integer.toString(parent.getId()));
//
//        TextView textView = (TextView) thisInflater.inflate(R.layout.letters, parent);
//        textView.setText("two");
//        return textView;

    }

    private class ViewHolder {
        public TextView textView;
    }
}
