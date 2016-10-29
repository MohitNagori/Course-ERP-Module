package com.mohit.college.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mohit.college.R;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;
import com.mohit.college.database.LoginTable;


public class Login extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView email = (TextView) findViewById(R.id.email);
        final TextView password = (TextView) findViewById(R.id.pass);
        final Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s =  email.getText().toString();
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
                    SQLiteDatabase db = helper.getReadableDatabase();
                    String s1[] = {s, password.getText().toString()};
                    Cursor cursor  = LoginTable.select(db, LoginTable.TABLE_NAME, null, "email = ? AND pass= ?", s1, null, null, null, null);

                    // Cursor cursor = db.rawQuery("Select * from PersonInfo", null);
                    //Cursor cursor = db.rawQuery("Select pname, dob from PersonInfo where pname=? or pname=?",  new String[]{"Arun", "Amit"});
                    //Cursor cursor = db.query("PersonInfo", new String[]{"pname", "dob"}, "pname=?", new String[]{"Sumit"},null, null, null);
                    //Cursor cursor = db.query("PersonInfo", new String[]{"pname", "dob"}, "pname=? and dob=?", new String[]{"Amit", "4"},null, null, null);

                    if(cursor.getCount()==1)
                    {
                        cursor.close();
                        Intent i = new Intent(getApplicationContext(),Admin.class);
                        startActivity(i);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"User Not Registered",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {    Toast.makeText(getApplicationContext(),"Invalid Email",Toast.LENGTH_SHORT).show();
                }
                }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finishAffinity();
    }
}
