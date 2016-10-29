package com.mohit.college.activity.Course;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.mohit.college.R;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.adapter.CourseMyArrayAdapter;
import com.mohit.college.database.CourseTable;
import com.mohit.college.pojoclass.PojoCourse;

import java.util.Vector;

public class ViewCourse extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcours);


        Vector<PojoCourse> contacts = new Vector<PojoCourse>();

        MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor  = CourseTable.select(db, CourseTable.TABLE_NAME, null, null,null, null, null, null, null);

        if(cursor!=null) {
            while (cursor.moveToNext()) {
                contacts.add(new PojoCourse(cursor.getString(1), cursor.getInt(0) + ""));
            }
        }

        final CourseMyArrayAdapter adapter = new CourseMyArrayAdapter(this, contacts);

        ListView listView = (ListView) findViewById(R.id.courselist);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListView lv = (ListView) parent;
                PojoCourse current = (PojoCourse) lv.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), current.getId() + "\n" + current.getName(), Toast.LENGTH_SHORT).show();

            }
        });

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
        getMenuInflater().inflate(R.menu.menu_viewcours, menu);
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
