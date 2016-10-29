package com.mohit.college.activity.Course;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mohit.college.R;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.CourseTable;


public class AddCourse extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);


        Button b = (Button) findViewById(R.id.save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = (EditText) findViewById(R.id.pass);
                String s = edit.getText().toString();
                edit.setText("");
                if (s.length() != 0) {
                    MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
                    SQLiteDatabase db = helper.getWritableDatabase();

                    long id = CourseTable.insert(db, s);
                    if (id != -1) {
                        Toast.makeText(getApplicationContext(), "Course Added", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        Button b1 = (Button) findViewById(R.id.Clear);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = (EditText) findViewById(R.id.pass);
                edit.setText("");

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
