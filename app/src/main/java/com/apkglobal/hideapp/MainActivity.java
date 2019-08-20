package com.apkglobal.hideapp;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
EditText blog, idea;
Button save, fetch;
SQLiteDatabase sd;
FirebaseDatabase fd;
DatabaseReference dr, dr1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fd=FirebaseDatabase.getInstance();
        dr=fd.getReference("Titlename");

        dr1=fd.getReference("Titlename").child("Fname");
        //to insert the value
        dr.setValue(getSupportActionBar().getTitle());

        dr1.setValue("Pawan Sharma");
        dr1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*String  name=dataSnapshot.getValue(String.class);
                getSupportActionBar().setTitle(name);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        save=findViewById(R.id.save);
        fetch=findViewById(R.id.fetch);
        blog=findViewById(R.id.blog);
        idea=findViewById(R.id.idea);


        //1. to create or open the database
        sd=openOrCreateDatabase("dbname", 0, null);
        //2. to create or open table
        sd.execSQL("create table if not exists tname(blog varchar(250), idea varchar(250))");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sd.execSQL("insert into tname values('"+blog.getText().toString()+"', '"+idea.getText().toString()+"')");
              blog.setText("");
              idea.setText("");
            }
        });

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=sd.rawQuery("select * from tname", null);

                if (cursor.moveToFirst())
                {
                    while (cursor.moveToNext())
                    {
                        Toast.makeText(MainActivity.this,
                                cursor.getString(0)+cursor.getString(1),
                                Toast.LENGTH_SHORT).show();
                        /*blog.append(cursor.getString(0));
                        idea.append(cursor.getString(1));*/
                    }
                }

            }
        });
        /*PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this,
                com.apkglobal.hideapp.MainActivity.class);
        p.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);*/

    }
}
