package com.acta.acta.app.Other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acta.acta.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by wareja on 16. 12. 28.
 */

public class StoryView extends RecyclerView.ViewHolder {

    public DatabaseReference databaseReference;
    public String user_id= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
    public View view;

    public StoryView(View itemView) {
        super(itemView);

        view=itemView;

    }

    //get the title
    public void setTitle(String title) {
        TextView post_title = (TextView) view.findViewById(R.id.title);
        post_title.setText(title);
    }
    //get the description
       /*public void setDescription(String desc) {
            TextView post_desc = (TextView) view.findViewById(R.id.description);
            post_desc.setText(desc);
        }*/
    //get the image
    public void setImages(Context ctx, String image){
        ImageView post_image=(ImageView) view.findViewById(R.id.Post_image);
        Picasso.with(ctx).load(image).into(post_image);

    }
       /* public void setDateTime(String dateTime){
            TextView datetime=(TextView) view.findViewById(R.id.date);
            databaseReference= FirebaseDatabase.getInstance().getReference().child("Blog of "+ user_id);
            String date=databaseReference.child("Date_posted").toString();
            datetime.setText(date);

        }*/
}
