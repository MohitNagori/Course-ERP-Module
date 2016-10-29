package com.mohit.college.activity.Subject;

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

import com.mohit.college.R;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.adapter.SubjectMyArrayAdapter;
import com.mohit.college.database.BranchTable;
import com.mohit.college.database.CourseTable;
import com.mohit.college.database.SemesterTable;
import com.mohit.college.database.SubjectTable;
import com.mohit.college.pojoclass.PojoSubject;

import java.util.Vector;

public class ViewSubject extends ActionBarActivity {
   SubjectMyArrayAdapter adapter;
    public static Spinner s,s1,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsubject);
        MySQLiteHelper helper = new MySQLiteHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();
        final ListView listView = (ListView) findViewById(R.id.courselist);



        s = (Spinner) findViewById(R.id.coursespinner);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        Cursor cursor  = CourseTable.select(db, CourseTable.TABLE_NAME, null, null, null, null, null, null, null);
        Vector<String> cname = new Vector<String>();
        final Vector<String> cid = new Vector<String>();
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                cname.add(cursor.getString(1));
                cid.add(cursor.getString(0));
            }
            cursor.close();
            arrayAdapter.addAll(cname);
            s.setAdapter(arrayAdapter);
        }



        s2 = (Spinner) findViewById(R.id.semesterspinner);
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        cursor  = SemesterTable.select(db, SemesterTable.TABLE_NAME, null, null, null, null, null, null, null);
        final Vector<String> semname = new Vector<String>();
        final Vector<String> semid = new Vector<String>();
        while(cursor.moveToNext())
        {
            semname.add(cursor.getString(1));
            semid.add(cursor.getString(0));
        }
        cursor.close();
        arrayAdapter2.addAll(semname);
        s2.setAdapter(arrayAdapter2);



        s1 = (Spinner) findViewById(R.id.subjectspinner);
        final Vector<String> bname = new Vector<String>();
        final Vector<String> bid = new Vector<String>();


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(ViewSubject.this,android.R.layout.simple_spinner_dropdown_item);
                String arg[] = {cid.get(position)};
                Cursor cursor = BranchTable.select(db, BranchTable.TABLE_NAME, null, BranchTable.CID + "=?", arg, null, null, null, null);
                bname.removeAllElements();
                bid.removeAllElements();
                if(cursor != null) {
                    while (cursor.moveToNext()) {
                        bname.add(cursor.getString(1));
                        bid.add(cursor.getString(0));
                    }
                    cursor.close();
                    arrayAdapter1.addAll(bname);
                    s1.setAdapter(arrayAdapter1);
                }

                Cursor cursor1;
                Vector<PojoSubject> contacts = new Vector<PojoSubject>();
                if(s1.getSelectedItemPosition() ==-1 ||s2.getSelectedItemPosition() ==-1)
                {
                    cursor1 = null;
                }
                else {
                    String arg1[] = {cid.get(position), bid.get(s1.getSelectedItemPosition()), semid.get(s2.getSelectedItemPosition())};
                    cursor1 = SubjectTable.select(db, SubjectTable.TABLE_NAME, null, SubjectTable.CID + "=? AND " + SubjectTable.BID + "=? AND " + SubjectTable.SEMID + "=?", arg1, null, null, null, null);
                }
                if(cursor1 !=null) {
                    while (cursor1.moveToNext()) {
                        contacts.add(new PojoSubject(cursor1.getInt(0) + "", cursor1.getString(1), cursor1.getString(2), cursor1.getString(3)));
                    }
                    cursor1.close();
                    adapter = new SubjectMyArrayAdapter(getApplicationContext(), contacts);
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

                Vector<PojoSubject> contacts = new Vector<PojoSubject>();
                Cursor cursor1;
                if(s.getSelectedItemPosition() ==-1 ||s2.getSelectedItemPosition() ==-1)
                {
                    cursor1 = null;
                }
                else {
                    String arg1[] = {cid.get(s.getSelectedItemPosition()),bid.get(position),  semid.get(s2.getSelectedItemPosition())};
                    cursor1 = SubjectTable.select(db, SubjectTable.TABLE_NAME, null, SubjectTable.CID + "=? AND " + SubjectTable.BID + "=? AND " + SubjectTable.SEMID +"=?", arg1, null, null, null, null);
                }
                if(cursor1!=null) {
                    while (cursor1.moveToNext()) {
                        contacts.add(new PojoSubject(cursor1.getInt(0) + "", cursor1.getString(1), cursor1.getString(2), cursor1.getString(3)));
                    }

                    adapter = new SubjectMyArrayAdapter(getApplicationContext(), contacts);
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


                Vector<PojoSubject> contacts = new Vector<PojoSubject>();
                Cursor cursor1;
                if(s.getSelectedItemPosition() ==-1 ||s1.getSelectedItemPosition() ==-1)
                {
                    cursor1 = null;
                }
                else {
                    String arg1[] = {cid.get(s.getSelectedItemPosition()),bid.get(s1.getSelectedItemPosition()),semid.get(position)};
                    cursor1 = SubjectTable.select(db, SubjectTable.TABLE_NAME, null, SubjectTable.CID + "=? AND " + SubjectTable.BID + "=? AND " + SubjectTable.SEMID +"=?", arg1, null, null, null, null);
                }

                if(cursor1!=null) {
                    while (cursor1.moveToNext()) {
                        contacts.add(new PojoSubject(cursor1.getInt(0) + "", cursor1.getString(1), cursor1.getString(2), cursor1.getString(3)));
                    }

                    adapter = new SubjectMyArrayAdapter(getApplicationContext(), contacts);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






/*
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener()

    {
        @Override
        public void onItemClick (AdapterView parent, View view,int position, long id){
        ListView lv = (ListView) parent;
        PojoSubject current = (PojoSubject) lv.getItemAtPosition(position);
        Toast.makeText(getBaseContext(), current.getId() + "\n" + current.getName(), Toast.LENGTH_SHORT).show();

    }
    }

    );
*/


    listView.setTextFilterEnabled(true);

    final SearchView sv = (SearchView) findViewById(R.id.search);

    sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()

    {
        @Override
        public boolean onQueryTextSubmit (String query){
        return false;
    }

        @Override
        public boolean onQueryTextChange (String newText){
        if(adapter!=null)
        adapter.getFilter().filter(newText);
        return false;
    }
    }

    );

    sv.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener()

    {
        @Override
        public void onFocusChange (View v,boolean hasFocus){
        if (!hasFocus) {
            if(adapter!=null) {
                adapter.getFilter().filter("");
            }
            sv.onActionViewCollapsed();
            Log.d("1234", "focus change");

        }
     }
    }

    );


}

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewsubject, menu);
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
