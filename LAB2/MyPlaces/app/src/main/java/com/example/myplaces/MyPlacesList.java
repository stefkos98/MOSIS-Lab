package com.example.myplaces;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPlacesList extends AppCompatActivity {
    ArrayList<String> places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        places=new ArrayList<>();
        places.add("Cele kula");
        places.add("Medijana");
        places.add("Bubanj");
        places.add("Cegar");
        ListView myPlacesList=(ListView)findViewById(R.id.my_places_list);
        myPlacesList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu item){
        getMenuInflater().inflate(R.menu.menu_my_places_list,item);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.first_setting) {
            Toast.makeText(this,"Show Map!",Toast.LENGTH_LONG).show();
        }
        if (id == R.id.second_setting) {
            Toast.makeText(this,"New place!",Toast.LENGTH_LONG).show();
        }
        if (id == R.id.fourth_setting) {
            Intent i=new Intent(this,About.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
