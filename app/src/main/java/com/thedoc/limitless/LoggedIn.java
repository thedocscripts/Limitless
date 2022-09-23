package com.thedoc.limitless;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
<<<<<<< HEAD
<<<<<<< HEAD
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
=======
import android.widget.Button;
>>>>>>> parent of 764d9e3 (update)
=======
import android.widget.Button;
>>>>>>> parent of 764d9e3 (update)
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
<<<<<<< HEAD
<<<<<<< HEAD
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    List<String> list;

=======

public class LoggedIn extends AppCompatActivity {
    FirebaseAuth mAuth;
>>>>>>> parent of 764d9e3 (update)
=======

public class LoggedIn extends AppCompatActivity {
    FirebaseAuth mAuth;
>>>>>>> parent of 764d9e3 (update)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);


        mAuth = FirebaseAuth.getInstance();
<<<<<<< HEAD
<<<<<<< HEAD
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
=======
=======
>>>>>>> parent of 764d9e3 (update)
        FirebaseUser user = mAuth.getCurrentUser();



        Button ss = (Button) findViewById(R.id.button);

<<<<<<< HEAD
>>>>>>> parent of 764d9e3 (update)
=======
>>>>>>> parent of 764d9e3 (update)

        TextView tt = (TextView) findViewById(R.id.s2tt);
        //
        tt.setText("Bienvenido " + user.getEmail().toString());

<<<<<<< HEAD
<<<<<<< HEAD

        listView = findViewById(R.id.ListView);
        button = findViewById(R.id.floatingActionButton);

        button.setOnClickListener(new View.OnClickListener() {
=======
        ss.setOnClickListener(new View.OnClickListener() {
>>>>>>> parent of 764d9e3 (update)
=======
        ss.setOnClickListener(new View.OnClickListener() {
>>>>>>> parent of 764d9e3 (update)
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(LoggedIn.this, MainActivity.class);
                LoggedIn.this.startActivity(myIntent);
            }
        });
<<<<<<< HEAD
<<<<<<< HEAD

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
=======
>>>>>>> parent of 764d9e3 (update)
=======
>>>>>>> parent of 764d9e3 (update)
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

    public int RoomIDGenerator() {

        ///Firstly check for existing rooms, then create lobby if non existent
        db.collection("root_collection").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }
                    Log.d(TAG, list.toString());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        System.out.println(list);
        return 1;
    }
}


