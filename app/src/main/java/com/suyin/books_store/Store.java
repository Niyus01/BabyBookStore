package com.suyin.books_store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class Store extends AppCompatActivity {

        private String title, infoBook;
        private double price;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

            GridView gridView = (GridView)findViewById(R.id.gridview);
            final BooksAdapter booksAdapter = new BooksAdapter(this, books);
            gridView.setAdapter(booksAdapter);


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    Book book = books[position];
                    title = book.getTitle();
                    infoBook = book.getInfo();
                    price = book.getPrice();
                    Intent intent = new Intent(Store.this, SelectBookActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("info", infoBook);
                    intent.putExtra("price",price);

                    startActivity(intent);
                    booksAdapter.notifyDataSetChanged();
                }
            });
    }





    private Book[] books = {
            new Book("ABC an amazing alphabet book", "Dr.Seuss",7.80, R.drawable.book1,
                    "With Dr. Seuss as your guide, learning the alphabet is as easy as A, B, C."),
            new Book("Are you my mother?","Eastman, P. D.", 5.25, R.drawable.book2,
                    "This easy to read, must have classic about a baby bird in search of his mother is perfect for every day!"),
            new Book("Where is babys belly button?", "Karen Katz", 8.90, R.drawable.book3,
                    "Karen Katz's adorable babies play peekaboo in this delightful interactive book. The sturdy format " +
                            "and easy tolift flaps are perfect for parents and children to share."),
            new Book("On the night you were born","Nancy Tillman",5.98, R.drawable.book4,
                    "On the night you were born you brought wonder and magic to the world. Here is a book that celebrates you."),
            new Book("Hand hand fingers thumb"," Dr.Seuss", 10.58,R.drawable.book5,
                    "Illus. in full color. A madcap band of dancing, prancing monkeys explain hands, fingers, and thumbs to beginning readers."),
            new Book("The very hungry caterpillar", "Eric Carle", 11.30, R.drawable.book6,
                    "The Very Hungry Caterpillar is a classic children's story, the essential first book for babies. "),
            new Book("The going to bed book","Sandra Boynton",9.90, R.drawable.book7,
                    "This classic bedtime story is just right for winding down the day as a joyful, silly group" +
                            " of animals scrub scrub scrub in the tub, brush and brush and brush their teeth, " +
                            "and finally rock and rock and rock to sleep."),
            new Book("Oh baby go baby!", "Dr.Seuss",15.10, R.drawable.book8,
                    "Oh, the Places You'll Go!, cheers babies and toddlers on as they take on life's adventures! "),
            new Book("The tooth book","Dr.Seuss", 7.20, R.drawable.book9,
                    "In Dr. Seuss s hilarious ode to teeth, little ones will laugh out loud as they find out all the " +
                            "things teeth can do and how to take care of them so they last a lifetime! "),
            new Book("One fish two fish red fish blue fish", "Dr.Seuss",4.90 ,R.drawable.book10,
                    "Beginning with just five fish and continuing into flights of fancy, One Fish Two Fish Red Fish " +
                            "Blue Fish celebrates how much fun imagination can be. ")


    };
}
