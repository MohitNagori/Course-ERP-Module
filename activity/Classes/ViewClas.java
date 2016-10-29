package com.mohit.college.activity.Classes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mohit.college.R;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.adapter.ClassMyArrayAdapter;
import com.mohit.college.database.BranchTable;
import com.mohit.college.database.ClassTable;
import com.mohit.college.database.CourseTable;
import com.mohit.college.database.SemesterTable;
import com.mohit.college.database.SubjectTable;
import com.mohit.college.pojoclass.PojoClas;

import java.util.Vector;

public class ViewClas extends ActionBarActivity {
    int backpress=0;
    ClassMyArrayAdapter adapter;
    public static Spinner s,s1,s2,s3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewclas);
        MySQLiteHelper helper = new MySQLiteHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();
        final ListView listView = (ListView) findViewById(R.id.courselist);


        s = (Spinner) findViewById(R.id.coursespinner);
        s1 = (Spinner) findViewById(R.id.subjectspinner);
        s2 = (Spinner) findViewById(R.id.SubjectSubject);
        s3 = (Spinner) findViewById(R.id.Subjectsemester);

        final Vector<String> cname = new Vector<String>();
        final Vector<String> cid = new Vector<String>();
        final Vector<String> bname = new Vector<String>();
        final Vector<String> bid = new Vector<String>();
        final Vector<String> semname = new Vector<String>();
        final Vector<String> semid = new Vector<String>();
        final Vector<String> sname = new Vector<String>();
        final Vector<String> sid = new Vector<String>();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        Cursor cursor  = CourseTable.select(db, CourseTable.TABLE_NAME, null, null, null, null, null, null, null);
        if(cursor!=null) {
            while(cursor.moveToNext())
        {
            cname.add(cursor.getString(1));
            cid.add(cursor.getString(0));
        }
        }
        cursor.close();
        arrayAdapter.addAll(cname);
        s.setAdapter(arrayAdapter);




        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(ViewClas.this, android.R.layout.simple_spinner_dropdown_item);
                String arg[] = {cid.get(position)};
                Cursor cursor = BranchTable.select(db, BranchTable.TABLE_NAME, null, BranchTable.CID + "=?", arg, null, null, null, null);
                bname.removeAllElements();
                bid.removeAllElements();
                if(cursor!=null) {
                    while (cursor.moveToNext()) {
                        bname.add(cursor.getString(1));
                        bid.add(cursor.getString(0));
                    }
                    cursor.close();
                    arrayAdapter1.addAll(bname);
                    s1.setAdapter(arrayAdapter1);
                }
                else
                {
                    s1.removeAllViews();
                    s2.removeAllViews();
                    s3.removeAllViews();
                }
                Cursor cursor1;
                Vector<PojoClas> contacts = new Vector<PojoClas>();
                if (s1.getSelectedItemPosition() == -1 || s2.getSelectedItemPosition() == -1 || s3.getSelectedItemPosition() == -1) {
                    cursor1 = null;
                } else {
                    String arg1[] = {cid.get(position), bid.get(s1.getSelectedItemPosition()), semid.get(s3.getSelectedItemPosition()), sid.get(s2.getSelectedItemPosition())};
                    cursor1 = ClassTable.select(db, ClassTable.TABLE_NAME, null, ClassTable.CID + "=? AND " + ClassTable.BID + "=? AND " + ClassTable.SEMID + "=?" + ClassTable.SID + "=?", arg1, null, null, null, null);
                }
                if (cursor1 != null) {
                    while (cursor1.moveToNext()) {
                        contacts.add(new PojoClas(cursor1.getString(1), cursor1.getInt(3) + "", cursor1.getString(4), cursor1.getInt(0) + ""));
                    }
                    cursor1.close();
                    adapter = new ClassMyArrayAdapter(getApplicationContext(), contacts);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(ViewClas.this, android.R.layout.simple_spinner_dropdown_item);
                String arg[] = {cid.get(s.getSelectedItemPosition()), bid.get(position)};
                Cursor cursor = SubjectTable.select(db, SubjectTable.TABLE_NAME, null, SubjectTable.CID + "=? AND " + SubjectTable.BID + "=?", arg, null, null, null, null);
                sname.removeAllElements();
                sid.removeAllElements();
                if(cursor!=null) {
                    while (cursor.moveToNext()) {
                        sname.add(cursor.getString(1));
                        sid.add(cursor.getString(0));
                    }
                    cursor.close();
                    arrayAdapter1.addAll(sname);
                    s2.setAdapter(arrayAdapter1);
                }
                else
                {    s2.removeAllViews();
                    s3.removeAllViews();

                }
                Cursor cursor1;
                Vector<PojoClas> contacts = new Vector<PojoClas>();
                if (s3.getSelectedItemPosition() == -1 || s2.getSelectedItemPosition() == -1) {
                    cursor1 = null;
                } else {
                    String arg1[] = {cid.get(s.getSelectedItemPosition()), bid.get(position), semid.get(s3.getSelectedItemPosition()), sid.get(s2.getSelectedItemPosition())};
                    cursor1 = ClassTable.select(db, ClassTable.TABLE_NAME, null, ClassTable.CID + "=? AND " + ClassTable.BID + "=? AND " + ClassTable.SEMID + "=? AND " + ClassTable.SID + "=?", arg1, null, null, null, null);
                }
                if (cursor1 != null) {
                    while (cursor1.moveToNext()) {
                        contacts.add(new PojoClas(cursor1.getString(1), cursor1.getInt(3) + "", cursor1.getString(4), cursor1.getInt(0) + ""));
                    }
                    cursor1.close();
                    adapter = new ClassMyArrayAdapter(getApplicationContext(), contacts);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {

                String ii[] = {cid.get(s.getSelectedItemPosition()),bid.get(s1.getSelectedItemPosition()),sid.get(position)};
                Cursor cursor = SubjectTable.select(db, SubjectTable.TABLE_NAME, null, SubjectTable.CID + "=? and "+SubjectTable.BID + "=? and "+SubjectTable.SID + "=?", ii, null, null, null, null);
                semname.removeAllElements();
                semid.removeAllElements();
                if(cursor!=null) {
                    while (cursor.moveToNext()) {
                        semid.add(cursor.getInt(4) + "");
                        String iii[] = {cursor.getString(4)};
                        Cursor cursor1 = SemesterTable.select(db, SemesterTable.TABLE_NAME, null, SemesterTable.SID + "=?", iii, null, null, null, null);
                        while (cursor1.moveToNext()) {
                            semname.add(cursor1.getString(1));
                        }
                        cursor1.close();
                    }
                    cursor.close();

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewClas.this, android.R.layout.simple_spinner_dropdown_item);
                    arrayAdapter.addAll(semname);
                    s3.setAdapter(arrayAdapter);
                }
                else
                {
                   s3.removeAllViews();
                }


                Cursor cursor1;
                Vector<PojoClas> contacts = new Vector<PojoClas>();
                if (s1.getSelectedItemPosition() == -1 || s2.getSelectedItemPosition() == -1 || s3.getSelectedItemPosition() == -1) {
                    cursor1 = null;
                } else {
                    String arg1[] = {cid.get(s.getSelectedItemPosition()), bid.get(s1.getSelectedItemPosition()), sid.get(position), semid.get(s3.getSelectedItemPosition())};
                    cursor1 = ClassTable.select(db, ClassTable.TABLE_NAME, null, ClassTable.CID + "=? AND " + ClassTable.BID + "=? AND " + ClassTable.SID + "=? AND"+ ClassTable.SEMID +"=?", arg1, null, null, null, null);
                }
                if (cursor1 != null) {
                    while (cursor1.moveToNext()) {
                        contacts.add(new PojoClas(cursor1.getString(1), cursor1.getInt(3) + "", cursor1.getString(4), cursor1.getInt(0) + ""));
                    }
                    cursor1.close();
                    adapter = new ClassMyArrayAdapter(getApplicationContext(), contacts);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                Cursor cursor1;
                Vector<PojoClas> contacts = new Vector<PojoClas>();
                if (s1.getSelectedItemPosition() == -1 || s2.getSelectedItemPosition() == -1) {
                    cursor1 = null;
                } else {
                    String arg1[] = {cid.get(s.getSelectedItemPosition()), bid.get(s1.getSelectedItemPosition()), sid.get(s2.getSelectedItemPosition()), semid.get(position)};
                    cursor1 = ClassTable.select(db, ClassTable.TABLE_NAME, null, ClassTable.CID + "=? AND " + ClassTable.BID + "=? AND "  + ClassTable.SID + "=? AND "+ ClassTable.SEMID + "=?", arg1, null, null, null, null);
                }
                if (cursor1 != null) {
                    while (cursor1.moveToNext()) {
                        contacts.add(new PojoClas(cursor1.getString(1), cursor1.getInt(3) + "", cursor1.getString(4), cursor1.getInt(0) + ""));
                    }
                    cursor1.close();
                    adapter = new ClassMyArrayAdapter(getApplicationContext(), contacts);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListView lv = (ListView) parent;
                PojoClas current = (PojoClas) lv.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), current.getId() + "\n" + current.getName(), Toast.LENGTH_SHORT).show();

            }
        });
*/

        listView.setTextFilterEnabled(true);

        final SearchView sv = (SearchView) findViewById(R.id.search);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        sv.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    adapter.getFilter().filter("");
                    sv.onActionViewCollapsed();
                    Log.d("1234", "focus change");
                }
            }
        });

    }





        public void onBackPressed(){
            backpress = (backpress + 1);
            Toast.makeText(getApplicationContext(), " Press Back again to Exit ", Toast.LENGTH_SHORT).show();

            if (backpress>1) {
                this.finish();
            }
        }




  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewclas, menu);
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
