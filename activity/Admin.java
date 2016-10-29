package com.mohit.college.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mohit.college.activity.Classes.AddClass;
import com.mohit.college.activity.Classes.ViewClas;
import com.mohit.college.R;
import com.mohit.college.activity.Branch.AddBranch;
import com.mohit.college.activity.Branch.ViewBranch;
import com.mohit.college.activity.Course.AddCourse;
import com.mohit.college.activity.Course.ViewCourse;
import com.mohit.college.activity.Subject.AddSubject;
import com.mohit.college.activity.Subject.ViewSubject;
import com.mohit.college.activity.sqlitehelper.MySQLiteHelper;


public class Admin extends ActionBarActivity {

    SQLiteDatabase db;
    MySQLiteHelper dbHelper;

    String[] course  ={"Add Course", "View Course"};
    String[] branch ={"Add Branch", "View Branch"};
    String[] subject ={"Add Subject", "View Subject"};
    String[] clas={"Add Class", "View Class"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dbHelper = new MySQLiteHelper(this);


        db = dbHelper.getWritableDatabase();



        LinearLayout l1 = (LinearLayout) findViewById(R.id.course);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.branch);
        LinearLayout l3 = (LinearLayout) findViewById(R.id.subject);
        LinearLayout l4 = (LinearLayout) findViewById(R.id.clas);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setTitle("Select A Option").setItems(course, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent i = new Intent(getApplicationContext(), AddCourse.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(getApplicationContext(), ViewCourse.class);
                            startActivity(i);
                        }
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

            }
        });


        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setTitle("Select A Option").setItems(branch, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent i = new Intent(getApplicationContext(), AddBranch.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(getApplicationContext(), ViewBranch.class);
                            startActivity(i);
                        }
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

            }
        });


        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setTitle("Select A Option").setItems(subject, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent i = new Intent(getApplicationContext(), AddSubject.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(getApplicationContext(), ViewSubject.class);
                            startActivity(i);
                        }
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

            }
        });


        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setTitle("Select A Option").setItems(clas, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent i = new Intent(getApplicationContext(), AddClass.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(getApplicationContext(), ViewClas.class);
                            startActivity(i);
                        }
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

            }
        });

    }
}
