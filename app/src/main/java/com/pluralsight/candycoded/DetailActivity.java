package com.pluralsight.candycoded;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.pluralsight.candycoded.DB.CandyContract;
import com.pluralsight.candycoded.DB.CandyContract.CandyEntry;
import com.pluralsight.candycoded.DB.CandyDbHelper;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String SHARE_DESCRIPTION = "Look at this delicious candy from Candy Coded - ";
    public static final String HASHTAG_CANDYCODED = " #candycoded";
    String mCandyImageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = DetailActivity.this.getIntent();

        if (intent != null && intent.hasExtra("position")) {
            int position = intent.getIntExtra("position", 0);

            CandyDbHelper dbHelper = new CandyDbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
            cursor.moveToPosition(position);

            String candyName = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyContract.CandyEntry.COLUMN_NAME_NAME));
            String candyPrice = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_PRICE));
            mCandyImageUrl = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_IMAGE));
            String candyDesc = cursor.getString(cursor.getColumnIndexOrThrow(
                    CandyEntry.COLUMN_NAME_DESC));


            TextView textView = (TextView) this.findViewById(R.id.text_view_name);
            textView.setText(candyName);

            TextView textViewPrice = (TextView) this.findViewById(R.id.text_view_price);
            textViewPrice.setText(candyPrice);

            TextView textViewDesc = (TextView) this.findViewById(R.id.text_view_desc);
            textViewDesc.setText(candyDesc);

            ImageView imageView = (ImageView) this.findViewById(
                    R.id.image_view_candy);
            Picasso.with(this).load(mCandyImageUrl).into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    // ***
    // TODO - Task 4 - Share the Current Candy with an Intent
    // ***
    //-- options item selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_detail:

                // -- execute candy share intent
                shareCandyIntent();

                break;

            default:
                break;
        }
        return true;
    }

    // - share candy method
    void shareCandyIntent() {
        Intent shareCandyIntent = new Intent(Intent.ACTION_SEND);
        shareCandyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareCandyIntent.setType("text/plain");
        shareCandyIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        shareCandyIntent.putExtra(android.content.Intent.EXTRA_TEXT, SHARE_DESCRIPTION + HASHTAG_CANDYCODED);
        startActivity(shareCandyIntent);
    }


}
