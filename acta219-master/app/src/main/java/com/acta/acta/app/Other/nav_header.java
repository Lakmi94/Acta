package com.acta.acta.app.Other;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.TextView;

import com.acta.acta.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by wareja on 16. 12. 9.
 */

public class nav_header extends AppCompatActivity implements NavigationView.OnApplyWindowInsetsListener{
    private TextView name;
    private TextView email;
    private String user_email;
    ImageView profile;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_acta);

        mAuth=FirebaseAuth.getInstance();
        name=(TextView) findViewById(R.id.user_name);
        email=(TextView) findViewById(R.id.email);

    }

    @Override
    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
        user_email=mAuth.getCurrentUser().getEmail().toString().trim();
        email.setText(user_email);
        return null;
    }
}
