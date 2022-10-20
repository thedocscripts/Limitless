package com.thedoc.limitless;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LoggedIn extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.QUERY_ALL_PACKAGES) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.

        }


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextView tt = (TextView) findViewById(R.id.s2tt);
        //
        tt.setText("Bienvenido " + user.getEmail().toString());



        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);


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

        if(savedInstanceState != null){
            items = savedInstanceState.getStringArrayList("items");
            for (int i = 0; i < items.size(); i++) {
                System.out.println(items.get(i));
                itemsAdapter.add(items.get(i));
                itemsAdapter.notifyDataSetChanged();
            }

        }
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/thedocscripts"));
                startActivity(browserIntent);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

            outState.putStringArrayList("items", items);


    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

                itemsAdapter.remove(items.remove(i));
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Checked!", Toast.LENGTH_LONG).show();

                view.setBackgroundColor(context.getResources().getColor(R.color.green));
                itemsAdapter.notifyDataSetChanged();
            }
        });
    }





    private void addItem(View view) {
        EditText input = findViewById(R.id.AdditemInp);
        String itemText = input.getText().toString();

        if(!itemText.isEmpty()){
            itemsAdapter.add("Author: " + user.getEmail() + " \nItem: " + itemText);
            itemsAdapter.notifyDataSetChanged();
            items.add("Author: " + user.getEmail() + " \nItem: " + itemText);
            System.out.println(itemsAdapter.getCount());






            input.setText("");
        }else{
            Toast.makeText(getApplicationContext(), "Please add an item", Toast.LENGTH_LONG
            ).show();
        }
    }
}