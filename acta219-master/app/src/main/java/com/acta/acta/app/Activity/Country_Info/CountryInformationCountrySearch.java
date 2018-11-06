package com.acta.acta.app.Activity.Country_Info;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.acta.acta.R;
import com.acta.acta.app.Activity.Acta;

public class CountryInformationCountrySearch extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting 'activity_country_information_country_search.xml' as the layout
        setContentView(R.layout.activity_country_information_country_search);

        //Setting 'toolbar_countryinfo' as the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_countryinfo);
        setSupportActionBar(toolbar);

        //Setting up a hamburger icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);

        //Setting up a customized title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_country);

        //Setting up the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //When the flag or text called 'South Korea' is clicked, go to the according page
    public void Go_CISouthKorea(View view) {
        Intent startNewActivity = new Intent(this, CountryInformationSouthKorea.class);
        startActivity(startNewActivity);
    }

    //When the flag or text called 'Burundi' is clicked, go to the according page
    public void Go_CIBurundi(View view) {
        Intent startNewActivity = new Intent(this, CountryInformationBurundi.class);
        startActivity(startNewActivity);
    }

    //When the flag or text called 'Sri Lanka' is clicked, go to the according page
    public void Go_CISriLanka(View view) {
        Intent startNewActivity = new Intent(this, CountryInformationSriLanka.class);
        startActivity(startNewActivity);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.acta) {
            startActivity(new Intent(CountryInformationCountrySearch.this,Acta.class));
        } else if (id == R.id.map) {

        } else if (id == R.id.currency) {

        } else if (id == R.id.country) {
            startActivity(new Intent(CountryInformationCountrySearch.this, CountryInformationCountrySearch.class ));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        //highlight the selected item
        item.setChecked(true);
        //close the navigation drawer

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
