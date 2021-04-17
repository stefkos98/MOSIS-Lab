package com.example.myplaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditMyPlaceActivity extends AppCompatActivity implements View.OnClickListener{
    boolean editMode=true;
    int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_place);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button finishedButon = (Button)findViewById(R.id.editmyplace_finished_button);
        finishedButon.setOnClickListener(this);
        finishedButon.setEnabled(false);
        finishedButon.setText("Add");
        EditText nameEditText=(EditText)findViewById(R.id.editmyplace_name_edit);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                finishedButon.setEnabled(s.length()>0);
            }
        });

        Button cancelButon = (Button)findViewById(R.id.editmyplace_cancel_button);
        cancelButon.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        try{
            Intent listIntent=getIntent();
            Bundle positionBundle=listIntent.getExtras();
            if(positionBundle!=null){
                position=positionBundle.getInt("position");
            }
            else editMode=false;
        }catch(Exception e){
            editMode=false;
        }
        if(!editMode){
            finishedButon.setEnabled(false);
            finishedButon.setText("Add");
        }
        else if(position>=0){
            finishedButon.setText("Save");
            MyPlace place=MyPlacesData.getInstance().getPlace(position);
            nameEditText.setText(place.getName());
            EditText descEditText=(EditText)findViewById(R.id.editmyplace_desc_edit);
            descEditText.setText(place.getDesc());
        }
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.editmyplace_finished_button: {
            String name=((EditText)findViewById(R.id.editmyplace_name_edit)).getText().toString();
            String desc=((EditText)findViewById(R.id.editmyplace_desc_edit)).getText().toString();
            if(!editMode){
            MyPlace place=new MyPlace(name,desc);
            MyPlacesData.getInstance().addNewPlace(place);}
            else{
                MyPlace place=MyPlacesData.getInstance().getPlace(position);
                place.setName(name);
                place.setDecs(desc);
            }
            setResult(Activity.RESULT_OK);
            finish();
            break;
            }
            case R.id.editmyplace_cancel_button: {
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_my_place, menu);
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
        if (id == R.id.third_setting) {
            Intent i=new Intent(this,MyPlacesList.class);
            startActivity(i);
        }
        if (id == R.id.fourth_setting) {
            Intent i=new Intent(this,About.class);
            startActivity(i);
        }
        if (id == R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
