package com.mohit.college.activity.Subject;

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
import com.mohit.college.activity.Branch.ViewBranch;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.BranchTable;
import com.mohit.college.database.CourseTable;
import com.mohit.college.database.SubjectTable;

public class EditSubject extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);



        Intent i = getIntent();
        final String name= i.getExtras().getString("sname");
        EditText edit = (EditText) findViewById(R.id.sname);
        edit.setText(name);

        final String shname= i.getExtras().getString("ssname");
        edit = (EditText) findViewById(R.id.ssname);
        edit.setText(shname);

        final String code = i.getExtras().getString("scode");
        edit = (EditText) findViewById(R.id.code);
        edit.setText(code);

        final String id = i.getExtras().getString("sid");



        edit = (EditText) findViewById(R.id.subject_course);
        edit.setText(ViewSubject.s.getSelectedItem().toString());


        edit = (EditText) findViewById(R.id.subject_branch);
        edit.setText(ViewSubject.s1.getSelectedItem().toString());


        final Button b = (Button) findViewById(R.id.course_save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.sname);
                String sname= edit.getText().toString();
                edit = (EditText) findViewById(R.id.ssname);
                String ssname = edit.getText().toString();
                edit = (EditText) findViewById(R.id.code);
                String scode = edit.getText().toString();

                if (sname.equals(name)&&ssname.equals(shname)&&scode.equals(code) ) {
                    Toast.makeText(getApplicationContext(), "Same Info Of Subject", Toast.LENGTH_SHORT).show();
                } else if (sname.length() != 0 && ssname.length()!=0 && scode.length()!=0) {
                    MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(SubjectTable.SNAME, sname);
                    values.put(SubjectTable.SSNAME, ssname);
                    values.put(SubjectTable.SCODE, scode);


                    String arg[] = {id};
                    long i = SubjectTable.update(db, SubjectTable.TABLE_NAME, values, SubjectTable.SID + "=?", arg);
                    if (i != -1) {
                        Intent intent = new Intent(getApplicationContext(), ViewSubject.class);
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
                Intent i = new Intent(getApplicationContext(), ViewBranch.class);
                startActivity(i);
                finish();
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_subject, menu);
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
