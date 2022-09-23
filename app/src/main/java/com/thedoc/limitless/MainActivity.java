package com.thedoc.limitless;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        loader = new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Toast.makeText(MainActivity.this, "Welcome back",
                    Toast.LENGTH_SHORT).show();
            updateUI(currentUser);
        }

        EditText username = (EditText) findViewById(R.id.emailinp);
        EditText password = (EditText) findViewById(R.id.passinp);


        Button loginbtt  = (Button) findViewById(R.id.loginbtt);


        //Strings
        String emptyemail = getString(R.string.emptyemail);
        String emptypass = getString(R.string.emptypass);

        String authsucc = getString(R.string.authsucc);

        loginbtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, emptyemail,
                            Toast.LENGTH_SHORT).show();

                }else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, emptypass,
                            Toast.LENGTH_SHORT).show();

                }else if (password.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Email and password is empty",
                            Toast.LENGTH_SHORT).show();

                }else{
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    LoginAccount(username.getText().toString(), password.getText().toString());
                }
            }
        });
        
    }



    private void LoginAccount(String email, String password) {
        // [START create_user_with_email]

        loader.setMessage("Logging in...");
        loader.show();

        mAuth.signInWithEmailAndPassword(email, password)


                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            loader.hide();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(MainActivity.this, "Bienvenido",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);



                        } else {
                            // If sign in fails, display a message to the user.
                            loader.hide();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }


    private void updateUI(FirebaseUser currentUser) {
        Intent myIntent = new Intent(MainActivity.this, LoggedIn.class);

        MainActivity.this.startActivity(myIntent);
    }
}