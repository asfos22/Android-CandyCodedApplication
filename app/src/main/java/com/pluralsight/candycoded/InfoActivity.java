package com.pluralsight.candycoded;

import android.content.ActivityNotFoundException;
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

    TextView googleMapTextView, phoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Uri uri = Uri.parse("android.resource://com.codeschool.candycoded/" + R.drawable.store_front);
        ImageView candyStoreImageView = (ImageView) findViewById(R.id.image_view_candy_store);
        Picasso.with(this).
                load(uri).
                into(candyStoreImageView);

        // init

        googleMapTextView = (TextView) findViewById(R.id.text_view_address);
        phoneTextView = (TextView) findViewById(R.id.text_view_phone);

        // -- event set On click listener
        //-- click on Address TextView event
        googleMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (googleMapTextView.getText().toString().isEmpty()) {
                    // -- address not available
                    Toast.makeText(getApplicationContext(), getString(R.string.address_not_available), Toast.LENGTH_LONG).show();
                } else {
                    //--Launch the Google Maps Activity method
                    launchGoogleMapsActivity(googleMapTextView.getText().toString());
                }

            }
        });

        //-- click on phone TextView event
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phoneTextView.getText().toString().isEmpty()) {
                    // -- phone not available
                    Toast.makeText(getApplicationContext(), getString(R.string.phone_not_available), Toast.LENGTH_LONG).show();
                } else {
                    // -- Launch the Phone Activity method
                    launchPhoneActivity(phoneTextView.getText().toString());
                }


            }
        });


    }

    // ***
    // TODO - Task 2 - Launch the Google Maps Activity
    // ***

    void launchGoogleMapsActivity(String addressLocation) {

        Intent googleMapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=" + addressLocation.toString()));
        googleMapIntent.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        // -- catch any error
        try {

            if (googleMapIntent.resolveActivity(getPackageManager()) != null) {
                // -- map available
                startActivity(googleMapIntent);
            } else {
                // -- hey  there map not available
                Toast.makeText(getApplicationContext(), getString(R.string.install_map_desc), Toast.LENGTH_LONG).show();
            }
            // catch error
        } catch (ActivityNotFoundException ex) {

            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();

        }
    }

    // ***
    // TODO - Task 3 - Launch the Phone Activity
    // ***


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
