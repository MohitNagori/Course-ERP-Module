package com.mohit.college.activity.Branch;

import android.content.ContentValues;
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
import com.mohit.college.activity.Course.ViewCourse;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.BranchTable;
import com.mohit.college.database.CourseTable;

public class EditBranch extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_branch);



        Intent i = getIntent();
        final String branch= i.getExtras().getString("branch");
        EditText edit = (EditText) findViewById(R.id.branch);
        edit.setText(branch);
        final String seats= i.getExtras().getString("seats");
        edit = (EditText) findViewById(R.id.seats);
        edit.setText(seats);
        String course=i.getExtras().getString("course");
        edit = (EditText) findViewById(R.id.branch_course);
        edit.setText(course);
        final String id = i.getExtras().getString("id");


        Button b = (Button) findViewById(R.id.course_save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.branch);
                String b= edit.getText().toString();
                edit = (EditText) findViewById(R.id.seats);
                String s = edit.getText().toString();
                if (b.equals(branch)&&s.equals(seats)) {
                    Toast.makeText(getApplicationContext(), "Same Branch Name And Seats", Toast.LENGTH_SHORT).show();
                } else if (b.length() != 0 && s.length()!=0) {
                    MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(BranchTable.BNAME, b);
                    values.put(BranchTable.Available, s);

                    String arg[] = {id};
                    long i = CourseTable.update(db, BranchTable.TABLE_NAME, values, BranchTable.BID + "=?", arg);
                    if (i != -1) {
                        Intent intent = new Intent(getApplicationContext(), ViewBranch.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

        Button b1 = (Button) findViewById(R.id.course_clear);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ViewBranch.class);
                startActivity(i);
                finish();
            }
        });









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_branch, menu);
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
