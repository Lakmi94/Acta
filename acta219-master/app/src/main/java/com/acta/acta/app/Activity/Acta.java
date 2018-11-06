package com.acta.acta.app.Activity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.acta.acta.app.Activity.Country_Info.CountryInformationCountrySearch;
import com.acta.acta.app.Fragments.Calendar;
import com.acta.acta.R;
import com.acta.acta.app.Other.StoryView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class Acta extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CircleImageView profilePic;
    private TextView email;
    private TextView name;

    private FirebaseAuth user;
    private String userEmail;
    private String userId;

    private RecyclerView story_list;
    public DatabaseReference database;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email=(TextView) findViewById(R.id.email);
        //check for User info
        user=FirebaseAuth.getInstance();
        userEmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //email.setText(userEmail);


        //check if the user is signed in already
        userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAuthListener=new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    //if the user is not logged in
                    Intent SigninIntent=new Intent(Acta.this,SignIn.class);
                    SigninIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(SigninIntent);
                }
            }
        };

        //Setting up the database
        String blogName="Blog of "+userId;
        database=FirebaseDatabase.getInstance().getReference().child(blogName);


        //setting up the recycler view
        story_list=(RecyclerView) findViewById(R.id.story_list);
        story_list.setHasFixedSize(true);
        story_list.setLayoutManager(new LinearLayoutManager(this));
        //story_list.setItemAnimator(new SlideInUpAnimator());


        /**********************************  Fragment Matter  *************************************/
        Fragment fragment=null;
        Class fragmentClass=null;
        fragmentClass=Acta.class;

        try{
            fragment=(Fragment) fragmentClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        /******************************************************************************************/

        //Floating action button
        //When clicked, it opens up a new activity that let you create a new post
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Acta.this, NewPostActivity.class);
                startActivity(intent);
            }
        });

        //Set up the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //acta view
    protected void onStart() {
        super.onStart();


        user.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<Blog, StoryView> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, StoryView>(
                Blog.class,
                R.layout.story_row,
                StoryView.class,
                database
        )

        {
            public void populateViewHolder(final StoryView viewHolder, final Blog model, final int position) {
                viewHolder.setTitle(model.getTitle());
               // viewHolder.setDescription(model.getDescription());
                viewHolder.setImages(getApplicationContext(),model.getImages());
                //viewHolder.setDateTime(model.getDateTime());


                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //open a new activity

                        openActivity(model.getImages(),model.getTitle(),model.getDescription(),model.getDateTime());

                    }
                });
            }
        };
                story_list.setAdapter(firebaseRecyclerAdapter);
    }

    private void openActivity(String...details)
    {
        Intent intent=new Intent(Acta.this,SinglePostView.class);

        intent.putExtra("IMAGE_KEY",details[0]);
        intent.putExtra("TITLE_KEY",details[1]);
        intent.putExtra("DESC_KEY",details[2]);
        intent.putExtra("DATE_KEY",details[3]);

        startActivity(intent);
    }
    
    //setting up the back button
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            backButtonAlert();
        }
    }

    //when back button pressed on the main activity
    //the user gets prompted
    public void backButtonAlert()
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Acta.this)
            // Setting Dialog Title
            .setTitle("Leave application?")
            // Setting Dialog Message
            .setMessage("Are you sure you want to leave the application?")
            // Setting Icon to Dialog
            .setIcon(android.R.drawable.ic_dialog_alert)
            // Setting Positive  Button
            .setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.acta, menu);
        return true;
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
        if (id==R.id.logout)
        {
            signOut();
        }

        return super.onOptionsItemSelected(item);
    }
    //Signing out the user
    private void signOut() {
        user.signOut();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        /*Fragment fragment = null;
        Class fragmentClass=null;
*/
        if (id == R.id.acta) {
            startActivity(new Intent(Acta.this,Acta.class));
        } else if (id == R.id.map) {

            Fragment fragment = null;
            Class fragmentClass=null;
            fragmentClass=Map.class;

            try{
                fragment=(Fragment) fragmentClass.newInstance();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            //insert the fragment by replacing any existing fragment
            android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();

        } else if (id == R.id.currency) {

        } else if (id == R.id.country) {
            startActivity(new Intent(Acta.this, CountryInformationCountrySearch.class ));

        } else if (id == R.id.calendar) {
            //fragmentClass=Calendar.class;

        } else if (id == R.id.nav_share) {

          //  startActivity(new Intent(Acta.this,Share.class));

        } else if (id == R.id.nav_send) {

        }

       /* try{
            fragment=(Fragment) fragmentClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //insert the fragment by replacing any existing fragment
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,fragment)
                        .commit();
*/
        //highlight the selected item
        item.setChecked(true);
        //set action bar title
        setTitle(item.getTitle());
        //close the navigation drawer

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
