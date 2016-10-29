package com.mohit.college.activity.Branch;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mohit.college.R;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.BranchTable;
import com.mohit.college.database.CourseTable;

import java.util.Vector;


public class AddBranch extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        final Spinner s = (Spinner) findViewById(R.id.branch_course);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);

        MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor  = CourseTable.select(db, CourseTable.TABLE_NAME, null, null, null, null, null, null, null);
        Vector<String> name = new Vector<String>();
        final Vector<String> id = new Vector<String>();
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(1));
                id.add(cursor.getString(0));
            }
        }
        arrayAdapter.addAll(name);
        s.setAdapter(arrayAdapter);


        Button b = (Button) findViewById(R.id.course_save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = (EditText) findViewById(R.id.enter_branch);
                String branch = edit.getText().toString();
                EditText edit1 = (EditText) findViewById(R.id.enter_seats);
                String seats = edit1.getText().toString();
                edit.setText("");
                edit1.setText("");
                int i=s.getSelectedItemPosition();
                String cid = id.get(i);
                if (!(branch.isEmpty() && seats.isEmpty())) {
                    MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
                    SQLiteDatabase db = helper.getWritableDatabase();

                    long id = BranchTable.insert(db,branch,cid,seats);
                    if (id != -1) {
                        Toast.makeText(getApplicationContext(), "Branch Added", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        Button b1 = (Button) findViewById(R.id.course_clear);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = (EditText) findViewById(R.id.enter_branch);
                edit.setText("");
                edit = (EditText) findViewById(R.id.enter_seats);
                edit.setText("");


            }
        });








    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_branch, menu);
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
