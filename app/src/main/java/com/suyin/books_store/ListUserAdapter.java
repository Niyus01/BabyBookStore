package com.suyin.books_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ListUserAdapter extends BaseAdapter  {


    private final Context mContext;
    private ArrayList<ListUser> mlists;
    private  int mViewResourceId;


    public ListUserAdapter(Context context,  ArrayList<ListUser> list) {
        // super(context, 0, list);
        this.mContext = context;
        this.mlists = list;
    }

    @Override
    public int getCount() {
        return mlists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // super.getView(position, convertView, parent);

        ListUser list;
        list = mlists.get(position);

        if(convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_list, null);
        }

        TextView bookTitle = convertView.findViewById(R.id.texttitle);
        TextView bookCount = convertView.findViewById(R.id.textcount);
        TextView bookPrice = convertView.findViewById(R.id.textprice);

        bookTitle.setText(list.getTitle());
        bookCount.setText(" x  "+list.getCount());
        bookPrice.setText(" $ "+String.format("%.2f",list.getPrice()));

        return convertView;

    }


}



