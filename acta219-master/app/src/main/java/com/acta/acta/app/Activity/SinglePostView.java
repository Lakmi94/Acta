package com.acta.acta.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.acta.acta.R;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class SinglePostView extends AppCompatActivity{

    private ImageView imageView;
    private TextView titleView;
    private TextView descView;
    private TextView dateView;
    private Uri uri;
    private StorageReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get a support Action
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);


        imageView=(ImageView) findViewById(R.id.image);
        titleView=(TextView) findViewById(R.id.title);
        descView=(TextView) findViewById(R.id.description);
        dateView=(TextView) findViewById(R.id.date_posted);
        //dateView= DateFormat.getDateTimeInstance().format(new Date());

        //get intent
        Intent i=this.getIntent();

        //Receive data
        Context context;
        String image=i.getExtras().getString("IMAGE_KEY");
        Picasso.with(this)
                .load(image)
                .into(imageView);

        String title=i.getExtras().getString("TITLE_KEY");
        String description=i.getExtras().getString("DESC_KEY");
        String date=i.getExtras().getString("DATE_KEY");

        //bind the data
       imageView.setImageURI(uri);
        titleView.setText(title);
        descView.setText(description);
        dateView.setText(date);


    }
    //Action bar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_post_view_menu, menu);
        return true;
    }

}
