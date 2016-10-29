package com.mohit.college.activity.Classes;

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
import com.mohit.college.database.ClassTable;
import com.mohit.college.database.CourseTable;
import com.mohit.college.database.SemesterTable;
import com.mohit.college.database.SubjectTable;

import java.util.Vector;


public class AddClass extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
        final SQLiteDatabase db = helper.getReadableDatabase();

        final Spinner s3 = (Spinner) findViewById(R.id.class_semester);
        final Vector<String> semname = new Vector<String>();
        final Vector<String> semid = new Vector<String>();


        final Spinner s = (Spinner) findViewById(R.id.class_course);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
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


        final Spinner s1 = (Spinner) findViewById(R.id.class_branch);
        final Vector<String> bname = new Vector<String>();
        final Vector<String> bid = new Vector<String>();
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
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddClass.this, android.R.layout.simple_spinner_dropdown_item);
                arrayAdapter.addAll(bname);
                s1.setAdapter(arrayAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        final Spinner s2 = (Spinner) findViewById(R.id.class_subject);
        final Vector<String> sname = new Vector<String>();
        final Vector<String> sid = new Vector<String>();
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String i[] = {cid.get(s.getSelectedItemPosition()),bid.get(position)};
                Cursor cursor = SubjectTable.select(db, SubjectTable.TABLE_NAME, null, SubjectTable.CID + "=? and "+SubjectTable.BID + "=? ", i, null, null, null, null);
                sname.removeAllElements();
                sid.removeAllElements();
                while (cursor.moveToNext()) {
                    sname.add(cursor.getString(1));
                    sid.add(cursor.getString(0));
                }
                cursor.close();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddClass.this, android.R.layout.simple_spinner_dropdown_item);
                arrayAdapter.addAll(sname);
                s2.setAdapter(arrayAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String i[] = {cid.get(s.getSelectedItemPosition()),bid.get(s1.getSelectedItemPosition()),sid.get(position)};
                Cursor cursor = SubjectTable.select(db, SubjectTable.TABLE_NAME, null, SubjectTable.CID + "=? and "+SubjectTable.BID + "=? and "+SubjectTable.SID + "=?", i, null, null, null, null);
                semname.removeAllElements();
                semid.removeAllElements();
                while (cursor.moveToNext()) {
                    semid.add(cursor.getInt(4)+"");
                    String ii[] = {cursor.getString(4)};
                    Cursor cursor1 = SemesterTable.select(db, SemesterTable.TABLE_NAME, null, SemesterTable.SID + "=?", ii, null, null, null, null);
                    while(cursor1.moveToNext())
                    {
                        semname.add(cursor1.getString(1));
                    }
                    cursor1.close();
                }
                cursor.close();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddClass.this, android.R.layout.simple_spinner_dropdown_item);
                arrayAdapter.addAll(semname);
                s3.setAdapter(arrayAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button save = (Button) findViewById(R.id.course_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = (EditText) findViewById(R.id.cname);
                String cname = edit.getText().toString();
                edit.setText("");
                edit = (EditText) findViewById(R.id.cduration);
                String cduration = edit.getText().toString();
                edit.setText("");
                edit = (EditText) findViewById(R.id.cstart);
                String cstart = edit.getText().toString();
                edit.setText("");

                if (!(cname.isEmpty() && cduration.isEmpty() && cstart.isEmpty())) {
                    Long id = ClassTable.insert(db, cname, cduration, cstart, cid.get(s.getSelectedItemPosition()), bid.get(s1.getSelectedItemPosition()), sid.get(s2.getSelectedItemPosition()), semid.get(s3.getSelectedItemPosition()));
                    if (id != -1) {
                        Toast.makeText(getApplicationContext(), "Subject Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        Button clear = (Button) findViewById(R.id.course_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = (EditText) findViewById(R.id.cname);
                edit.setText("");
                edit = (EditText) findViewById(R.id.cduration);
                edit.setText("");
                edit = (EditText) findViewById(R.id.cstart);
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
        getMenuInflater().inflate(R.menu.menu_add_class, menu);
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
