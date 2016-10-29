package com.mohit.college.activity.Branch;

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
import com.mohit.college.adapter.BranchMyArrayAdapter;
import com.mohit.college.database.BranchTable;
import com.mohit.college.database.CourseTable;
import com.mohit.college.pojoclass.PojoBranch;

import java.util.Vector;

public class ViewBranch extends ActionBarActivity {
    BranchMyArrayAdapter adapter;
    public static Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbranch);

        s = (Spinner) findViewById(R.id.coursespinner);
        Spinner s1 = (Spinner) findViewById(R.id.coursespinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        MySQLiteHelper helper = new MySQLiteHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();

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

        final ListView listView = (ListView) findViewById(R.id.courselist);

        /*Vector<PojoBranch> contacts = new Vector<PojoBranch>();

        String arg[] = {id.get(s.getSelectedItemPosition())};
        Cursor cursor1 = BranchTable.select(db, BranchTable.TABLE_NAME, null, BranchTable.CID + "=?", arg, null, null, null, null);

        while (cursor.moveToNext()) {
            contacts.add(new PojoBranch(cursor.getString(1), cursor.getInt(0) + "", cursor.getInt(0) + ""));
        }

        adapter = new BranchMyArrayAdapter(getApplicationContext(), contacts);


        listView.setAdapter(adapter);
*/
      s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {


                Vector<PojoBranch> contacts = new Vector<PojoBranch>();

                String arg[] = {id.get(position)};
                Cursor cursor = BranchTable.select(db, BranchTable.TABLE_NAME, null, BranchTable.CID + "=?", arg, null, null, null, null);
                if(cursor!=null) {
                    while (cursor.moveToNext()) {
                        contacts.add(new PojoBranch(cursor.getString(1), cursor.getInt(0) + "", cursor.getInt(3) + ""));
                    }
                }
                adapter = new BranchMyArrayAdapter(getApplicationContext(), contacts);


                listView.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListView lv = (ListView) parent;
                PojoBranch current = (PojoBranch) lv.getItemAtPosition(position);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewbranch, menu);
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
