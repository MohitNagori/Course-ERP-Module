package com.mohit.college.activity.Subject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mohit.college.R;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.BranchTable;
import com.mohit.college.database.CourseTable;
import com.mohit.college.database.SemesterTable;
import com.mohit.college.database.SubjectTable;

import java.util.Vector;


public class AddSubject extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
        final SQLiteDatabase db = helper.getReadableDatabase();

        final Spinner s = (Spinner) findViewById(R.id.subject_course);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        Cursor cursor  = CourseTable.select(db, CourseTable.TABLE_NAME, null, null, null, null, null, null, null);
        final Vector<String> cname = new Vector<String>();
        final Vector<String> cid = new Vector<String>();
        while(cursor.moveToNext())
        {
            cname.add(cursor.getString(1));
            cid.add(cursor.getString(0));
        }
        cursor.close();
        arrayAdapter.addAll(cname);
        s.setAdapter(arrayAdapter);


        final Spinner s1 = (Spinner) findViewById(R.id.subject_semester);
        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        Cursor cursor1  = SemesterTable.select(db, SemesterTable.TABLE_NAME, null, null, null, null, null, null, null);
        final Vector<String> sname = new Vector<String>();
        final Vector<String> sid = new Vector<String>();
        while(cursor1.moveToNext())
        {
            sname.add(cursor1.getString(1));
            sid.add(cursor1.getString(0));

        }
        cursor1.close();
        arrayAdapter1.addAll(sname);
        s1.setAdapter(arrayAdapter1);


        final Vector<String> bname = new Vector<String>();
        final Vector<String> bid = new Vector<String>();

        final Spinner s2 = (Spinner) findViewById(R.id.subjectbranch);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String i[] = {cid.get(position)};
                Cursor cursor = BranchTable.select(db, BranchTable.TABLE_NAME, null, BranchTable.CID + "=?", i, null, null, null, null);
                bname.removeAllElements();
                bid.removeAllElements();
                while (cursor.moveToNext()) {
                    bname.add(cursor.getString(1));
                    bid.add(cursor.getString(0));
                }
                cursor.close();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddSubject.this, android.R.layout.simple_spinner_dropdown_item);
                arrayAdapter.addAll(bname);
                s2.setAdapter(arrayAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button save = (Button) findViewById(R.id.course_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = (EditText) findViewById(R.id.sname);
                String sname = edit.getText().toString();
                edit.setText("");
                edit = (EditText) findViewById(R.id.ssname);
                String ssname = edit.getText().toString();
                edit.setText("");
                edit = (EditText) findViewById(R.id.scode);
                String scode = edit.getText().toString();
                edit.setText("");

                if(s.getSelectedItemPosition()==-1 ||s2.getSelectedItemPosition()==-1)
                {
                    Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!(sname.isEmpty() && ssname.isEmpty() && scode.isEmpty())) {
                        Long id = SubjectTable.insert(db, sname, ssname, scode, cid.get(s.getSelectedItemPosition()), bid.get(s2.getSelectedItemPosition()), sid.get(s1.getSelectedItemPosition()));
                        if (id != -1) {
                            Toast.makeText(getApplicationContext(), "Subject Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


        Button clear = (Button) findViewById(R.id.course_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = (EditText) findViewById(R.id.sname);
                edit.setText("");
                edit = (EditText) findViewById(R.id.ssname);
                edit.setText("");
                edit = (EditText) findViewById(R.id.scode);
                edit.setText("");
                s.setSelection(0);
                s1.setSelection(0);
                s2.setSelection(0);

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_subject, menu);
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
