package com.suyin.books_store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BooksAdapter extends BaseAdapter {

        private final Context mContext;
        private final Book[] books;


        public BooksAdapter(Context context, Book[] books) {
            this.mContext = context;
            this.books = books;
        }

        @Override
        public int getCount() {
            return books.length;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Book book = books[position];
            String price= String.format("%.2f",book.getPrice());

            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                convertView = layoutInflater.inflate(R.layout.linearlayout_book, null);
            }

            final ImageView imageView = convertView.findViewById(R.id.imageview_cover_art);
            final TextView titleTextView = convertView.findViewById(R.id.textview_book);
            final TextView authorTextView = convertView.findViewById(R.id.textview_book_author);

            final TextView priceTextView = convertView.findViewById(R.id.textview_price);

            imageView.setImageResource(book.getImageResource());
            titleTextView.setText("Title: "+ book.getTitle());
            authorTextView.setText("Author: "+ book.getAuthor());
            priceTextView.setText(" $ "+ price);


            return convertView;
        }
    }


