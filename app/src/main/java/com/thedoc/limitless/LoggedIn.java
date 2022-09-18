package com.thedoc.limitless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedIn extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);



        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();



        Button ss = (Button) findViewById(R.id.button);


        TextView tt = (TextView) findViewById(R.id.s2tt);
        //
        tt.setText("Bienvenido " + user.getEmail().toString());

        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(LoggedIn.this, MainActivity.class);
                LoggedIn.this.startActivity(myIntent);
            }
        });
    }
}