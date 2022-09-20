package com.thedoc.limitless;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoggedIn extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private FloatingActionButton button;
    ListView lvItems;
    FirebaseFirestore db;
    int cci = -1;
    String Author;
    String dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        TextView tt = (TextView) findViewById(R.id.s2tt);
        //
        tt.setText("Bienvenido " + user.getEmail().toString());


        listView = findViewById(R.id.ListView);
        button = findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Checked!", Toast.LENGTH_LONG).show();
                itemsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.listcodeinp);
        String itemText = input.getText().toString();

        if(!itemText.isEmpty()){

            if(checkifemptycode(itemText)){
                System.out.println("hola");
            }else{

                // Create a new user with a first and last name
                Map<String, Object> userr = new HashMap<>();
                Map<String, Object> datt = new HashMap<>();

                userr.put("Tasks", datt);



                // Add a new document with a generated ID
                db.collection("codes").document(itemText).set(userr);


                itemsAdapter.add(itemText);

            }

        }else{
            Toast.makeText(getApplicationContext(), "Please add an item", Toast.LENGTH_LONG
            ).show();
        }
    }

    public Boolean checkifemptycode(String code) {


        DocumentReference docRef = db.collection("codes").document(code);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        dat = document.getString("Author");
                        System.out.println(dat);
                        cci = 1;
                    } else {
                        Log.d(TAG, "No such document");
                        cci = 404;
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    cci = 401;
                }
            }
        });
        if(cci == 1){
            return true;
        }else if(cci == 404){
            return false;
        }else{
            return false;
        }
    }
}