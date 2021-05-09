package com.example.myplaces.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.myplaces.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,EditMyPlaceActivity.class);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    static int NEW_PLACE=1;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.first_setting) {
            Intent i=new Intent(this,MyPlacesMapsActivity.class);
            startActivity(i);
        }
        if (id == R.id.second_setting) {
            Intent i=new Intent(this,EditMyPlaceActivity.class);
            startActivityForResult(i,NEW_PLACE);
        }
        if (id == R.id.third_setting) {
            Intent i=new Intent(this,MyPlacesList.class);
            startActivity(i);
        }
        if (id == R.id.fourth_setting) {
            Intent i=new Intent(this,About.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK) {
            Toast.makeText(this,"New place added!",Toast.LENGTH_LONG).show();
        }
    }
}
