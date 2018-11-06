package com.acta.acta.app.Activity.Country_Info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.acta.acta.R;

public class CountryInformationBurundi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting 'activity_country_information_burundi.xml' as the layout
        setContentView(R.layout.activity_country_information_burundi);

        //Setting 'toolbar' as the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //Setting a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);

        //Setting up a customized title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) myToolbar.findViewById(R.id.toolbar_title);
    }

    //Make Back button work
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //When the image or text called 'Information' is clicked, go to the according page
    public void Go_CIBurundiInfo(View view) {
        Intent startNewActivity = new Intent(this, CountryInformationBurundiGeneralInfo.class);
        startActivity(startNewActivity);
    }

    //When the image or text called 'Language' is clicked, go to the according page
    public void Go_CIBurundiLanguage(View view) {
        Intent startNewActivity = new Intent(this, CountryInformationBurundiLanguage.class);
        startActivity(startNewActivity);
    }
}
