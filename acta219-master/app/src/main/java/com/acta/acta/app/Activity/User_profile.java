package com.acta.acta.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.acta.acta.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class User_profile extends AppCompatActivity {
    private ImageButton userImage;
    private EditText userName;
    private Button submit;
    private ProgressDialog progressDialog;

    private static final int GALLERY_REQUEST_CODE=1;

    private Uri imageUri;

    private DatabaseReference reference;
    private FirebaseAuth auth;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userImage=(ImageButton) findViewById(R.id.user_image);
        userName=(EditText) findViewById(R.id.userName);
        submit=(Button) findViewById(R.id.submit_button);
        progressDialog = new ProgressDialog(this);

        //Create a nea path in the database for user names
        reference= FirebaseDatabase.getInstance().getReference().child("Users");

        //google user reference
        auth=FirebaseAuth.getInstance();
        //create a new path in the storage
        storageReference= FirebaseStorage.getInstance().getReference().child("Profile picture");

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupAccount();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_REQUEST_CODE && resultCode==RESULT_OK)
        {
            progressDialog.setMessage("Uploading Image");
            progressDialog.show();

            imageUri=data.getData();
            //crop the image
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
            progressDialog.dismiss();
        }
        //crop results
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                imageUri = result.getUri();
                userImage.setImageURI(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    //setting up the account profile
    private void SetupAccount() {

        final String userId=auth.getCurrentUser().getUid().toString();
        final String name= userName.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && imageUri!=null)
        {
            progressDialog.setMessage("Signing in");
            progressDialog.show();

            StorageReference filepath=storageReference.child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUri=taskSnapshot.getDownloadUrl().toString();

                    reference.child(userId).child("Name").setValue(name);
                    reference.child(userId).child("User image").setValue(downloadUri);

                    progressDialog.dismiss();
                    Toast.makeText(User_profile.this, "Upload Done", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(User_profile.this,Acta.class);

                    intent.putExtra("profilepic",imageUri);
                    intent.putExtra("profilename",name);
                }
            });

        }
        else

        {
            progressDialog.dismiss();
            if(imageUri==null)
            {

                Toast.makeText(User_profile.this,"upload an image please!",Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(name))
            {
                Toast.makeText(User_profile.this,"You need to input a name please!",Toast.LENGTH_LONG).show();
            }


        }
    }
}
