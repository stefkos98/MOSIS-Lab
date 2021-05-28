package com.example.myplaces.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.myplaces.R;
import com.example.myplaces.models.MyPlace;
import com.example.myplaces.models.MyPlacesData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyPlacesList extends AppCompatActivity {
    DatabaseReference database;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyPlacesList.this,EditMyPlaceActivity.class);
                startActivityForResult(i,NEW_PLACE1);
            }
        });

        //  3. pod D instant ucitavanje liste

        database = FirebaseDatabase.getInstance().getReference().child("my-places");
        database.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                setList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ListView myPlacesList=(ListView)findViewById(R.id.my_places_list);
        myPlacesList.setAdapter(new ArrayAdapter<MyPlace>(this,android.R.layout.simple_list_item_1, MyPlacesData.getInstance().getMyPlaces()));
        myPlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                MyPlace place=(MyPlace)parent.getAdapter().getItem(i);
                Toast.makeText(getApplicationContext(),place.name+ " selected", Toast.LENGTH_SHORT).show();
            }
        });
        myPlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle positionBundle=new Bundle();
                positionBundle.putInt("position",position);
                Intent intent=new Intent(MyPlacesList.this,ViewMyPlaceActivity.class);
                intent.putExtras(positionBundle);
                startActivity(intent);
            }
        });
        myPlacesList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo){
                AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) contextMenuInfo;
                MyPlace place=MyPlacesData.getInstance().getPlace(info.position);
                contextMenu.setHeaderTitle(place.name);
                contextMenu.add(0,1,1,"View Place");
                contextMenu.add(0,2,2,"Edit place");
                contextMenu.add(0,3,3,"Delete place");
                contextMenu.add(0,4,4,"Show on map");
            }
        });
        /**snip **/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                //At this point you should start the login activity and finish this one
                finish();
            }
        }, intentFilter);
        //** snip **//
    }
    @Override
    public boolean onCreateOptionsMenu(Menu item){
        getMenuInflater().inflate(R.menu.menu_my_places_list,item);
        return true;
    }

    static int NEW_PLACE1=1;
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
            startActivityForResult(i,NEW_PLACE1);
        }
        if (id == R.id.fourth_setting) {
            Intent i=new Intent(this,About.class);
            startActivity(i);
        }
        if (id == R.id.home) {
            finish();
        }
        if(id == R.id.fifth_setting)
        {
            //BROADCAST
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("com.package.ACTION_LOGOUT");
            sendBroadcast(broadcastIntent);
            //
            mAuth.signOut();
            Intent logoutIntent = new Intent(MyPlacesList.this, WelcomeActivity.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(logoutIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == Activity.RESULT_OK) {
            ListView myPlacesList=(ListView)findViewById(R.id.my_places_list);
            myPlacesList.setAdapter(new ArrayAdapter<MyPlace>(this,android.R.layout.simple_list_item_1,MyPlacesData.getInstance().getMyPlaces()));
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        Bundle positionBundle=new Bundle();
        positionBundle.putInt("position",info.position);
        Intent i=null;
        if(item.getItemId()==1){
            i=new Intent(this,ViewMyPlaceActivity.class);
            i.putExtras(positionBundle);
            startActivity(i);
        }
        else if(item.getItemId()==2){
            i=new Intent(this,EditMyPlaceActivity.class);
            i.putExtras(positionBundle);
            startActivityForResult(i,1);
        }
        else if(item.getItemId()==3){
            MyPlacesData.getInstance().deletePlace(info.position);
            setList();
        }
        else if(item.getItemId()==4){
            i=new Intent(this,MyPlacesMapsActivity.class);
            i.putExtra("state",MyPlacesMapsActivity.CENTER_PLACE_ON_MAP);
            MyPlace place=MyPlacesData.getInstance().getPlace(info.position);
            i.putExtra("lat",place.latitude);
            i.putExtra("lon",place.longitude);
            startActivityForResult(i,2);
        }
        return super.onContextItemSelected(item);
    }

    private void setList() {
        ListView myPlacesList = (ListView) findViewById(R.id.my_places_list);
        myPlacesList.setAdapter(new ArrayAdapter<MyPlace>(this, android.R.layout.simple_list_item_1,MyPlacesData.getInstance().getMyPlaces()));
    }


}
