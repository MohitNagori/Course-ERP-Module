package com.mohit.college.activity.Classes;

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
import com.mohit.college.activity.Subject.ViewSubject;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.ClassTable;
import com.mohit.college.database.SubjectTable;

public class EditClasss extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_classs);



        Intent i = getIntent();
        final String cname= i.getExtras().getString("cname");
        EditText   edit = (EditText) findViewById(R.id.cname);
        edit.setText(cname);

        final String cduration= i.getExtras().getString("cdur");
        edit = (EditText) findViewById(R.id.cduration);
        edit.setText(cduration);

        final String cstart= i.getExtras().getString("cstart");
        edit = (EditText) findViewById(R.id.cstart);
        edit.setText(cstart);

        final String id = i.getExtras().getString("cid");

        edit = (EditText) findViewById(R.id.class_course);
        edit.setText(ViewClas.s.getSelectedItem().toString());
        edit = (EditText) findViewById(R.id.class_branch);
        edit.setText(ViewClas.s1.getSelectedItem().toString());
        edit = (EditText) findViewById(R.id.class_subject);
        edit.setText(ViewClas.s2.getSelectedItem().toString());
        edit = (EditText) findViewById(R.id.class_semester);
        edit.setText(ViewClas.s3.getSelectedItem().toString());

        final Button b = (Button) findViewById(R.id.course_save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.cname);
                String name = edit.getText().toString();
                edit = (EditText) findViewById(R.id.cduration);
                String dur = edit.getText().toString();
                edit = (EditText) findViewById(R.id.cstart);
                String start = edit.getText().toString();

                if (cname.equals(name) && cduration.equals(dur) && cstart.equals(start)) {
                    Toast.makeText(getApplicationContext(), "Same Info Of Subject", Toast.LENGTH_SHORT).show();
                } else if (name.length() != 0 && dur.length() != 0 && start.length() != 0) {
                    MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(ClassTable.CNAME, name);
                    values.put(ClassTable.CLASSDUR, dur);
                    values.put(ClassTable.STIME, start);


                    String arg[] = {id};
                    long i = ClassTable.update(db, ClassTable.TABLE_NAME, values, ClassTable.CLASSID + "=?", arg);
                    if (i != -1) {
                        Intent intent = new Intent(getApplicationContext(), ViewClas.class);
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
                Intent i = new Intent(getApplicationContext(), ViewClas.class);
                startActivity(i);
                finish();
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_classs, menu);
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
