package com.pluralsight.candycoded;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Uri uri = Uri.parse("android.resource://com.codeschool.candycoded/" + R.drawable.store_front);
        ImageView candyStoreImageView = (ImageView) findViewById(R.id.image_view_candy_store);
        Picasso.with(this).
                load(uri).
                into(candyStoreImageView);



    }


    // ***
    // TODO - Task 2 - Launch the Google Maps Activity
    // ***
    public void createMapIntent(View view) {

        Uri uriAddress = Uri.parse("geo:0,0?q=618 E South St Orlando, FL 32801");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uriAddress);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            // -- map available
            startActivity(mapIntent);
        }

    }

    // ***
    // TODO - Task 3 - Launch the Phone Activity
    // ***

    public void createPhoneIntent(View view){

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);

        if (intent.resolveActivity(getPackageManager()) != null) {
            // -- map available
            startActivity(intent);
        }


    }


    void launchPhoneActivity(String phoneNumber) {

        // - catch
        try {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
            // catch error
        } catch (Exception ex) {

            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();

        }
    }
}
