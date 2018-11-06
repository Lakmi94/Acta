package com.acta.acta.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.acta.acta.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

public class NewPostActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private EditText title;
    private EditText desc;
    private Button postBtn;
    private Uri imageUri;
    private Uri imUri;
    private Context ctx;

    private final static int REQUEST_IMAGE = 1;

    private StorageReference storage;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String mfirebaseAuth;
    private Uri profilePic;
    private String user_id;
    private String date;
    private String blogName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
    /******************************Firebase database and storage information***********************/
        //our firebase storage reference
        storage = FirebaseStorage.getInstance().getReference();
        //The Userid
        user_id=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        //the user email
        mfirebaseAuth=FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        //get the date4
        date= DateFormat.getDateTimeInstance().format(new Date()).toString();

        //We will store our data into this database based on the user_id
        //In order to have the single user blog
        blogName="Blog of "+ user_id;
        databaseReference= FirebaseDatabase.getInstance().getReference().child(blogName);
    /**********************************************************************************************/

    /**********************************Widgets references******************************************/
        imageButton = (ImageButton) findViewById(R.id.add_img);
        title = (EditText) findViewById(R.id.story_title);
        desc = (EditText) findViewById(R.id.desc);
        postBtn = (Button) findViewById(R.id.post_btn);
        progressDialog = new ProgressDialog(this);
    /**********************************************************************************************/
        //When post button is pressed
        //It will call the post() method
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post();
            }
        });

        //When the image Button is pressed
        //it will open up the gallery
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get an image from your gallery
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);

                galleryIntent.setType("image/*");

                //open the gallery
                startActivityForResult(galleryIntent, REQUEST_IMAGE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check if there is no error
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            progressDialog.setMessage("Uploading Image");
            progressDialog.show();

            imageUri = data.getData();
            
            imageButton.setImageURI(imageUri);
            //better cropping image to fit it in the space provided for the image button
            Picasso.with(NewPostActivity.this)
                    .load(imageUri).fit().
                    centerCrop().
                    into(imageButton);
            //dismiss the progress dialog
            progressDialog.dismiss();



        }
        /************************************************************************************************************/
    }

    /**
     * Posting option
     */
    public void Post() {
        progressDialog.setMessage("Posting Story..");
        progressDialog.show();

        final String title_post = title.getText().toString().trim();
        final String desc_post = desc.getText().toString().trim();



        if (!TextUtils.isEmpty(title_post) && !TextUtils.isEmpty(desc_post) && imageButton!=null) {
        //****************************************Database Storage********************************************************/

            Log.d("TEXT","there is something");
            ///**********************Storing the image in firebase storage***************************/
            //This is the name of the path the images will go to in the database
            StorageReference filepath = storage.child("Story_Photos").child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imUri = taskSnapshot.getDownloadUrl();

                    DatabaseReference newStory = databaseReference.push();
                    newStory.child("Title").setValue(title_post);
                    newStory.child("Description").setValue(desc_post);
                    newStory.child("Images").setValue(imUri.toString());
                    newStory.child("User").setValue(mfirebaseAuth);
                    newStory.child("Date_posted").setValue(date);

                    progressDialog.dismiss();
                    Toast.makeText(NewPostActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(NewPostActivity.this,Acta.class));
                    //If the user uploaded a picture
                    /*if (imUri!=null)
                    {
                        newStory.child("Images").setValue(imUri.toString());
                        newStory.child("User").setValue(mfirebaseAuth);

                        progressDialog.dismiss();
                        Toast.makeText(NewPostActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(NewPostActivity.this,Acta.class));
                    }

                    else {
                        //if the user did not upload any picture
                        newStory.child("User").setValue(mfirebaseAuth);

                        progressDialog.dismiss();
                        Toast.makeText(NewPostActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(NewPostActivity.this,Acta.class));
                    }*/

                    //better cropping image
                    Picasso.with(NewPostActivity.this).load(imUri).resize(imageButton.getWidth(),imageButton.getHeight()).noFade().into(imageButton);

                    Toast.makeText(NewPostActivity.this, "the width is: ", Toast.LENGTH_LONG).show();
                }

            });

            filepath.putFile(imageUri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(NewPostActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                }
            });

        }
        else {

            progressDialog.dismiss();
            Toast.makeText(NewPostActivity.this, "Please provide the title and description", Toast.LENGTH_LONG).show();
        }
    }
}
