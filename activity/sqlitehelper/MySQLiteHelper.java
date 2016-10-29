package com.mohit.college.activity.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mohit.college.database.BranchTable;
import com.mohit.college.database.ClassTable;
import com.mohit.college.database.CourseTable;
import com.mohit.college.database.LoginTable;
import com.mohit.college.database.SemesterTable;
import com.mohit.college.database.SubjectTable;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static String databaseName = "mycollegedb.db";
    public static int databaseVersion = 16;


    public MySQLiteHelper(Context context)
    {
        super(context, databaseName, null, databaseVersion);  //(String dbName, SQLiteDatabase.CursorFactory factory,	int version)
		/*
		 * CursorFactory is used to create Cursor Objects with customized implementation.
		 * Use it when you want Cursor from a custom factory
		 * Pass null if you want to use default factory.
		 * */
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SubjectTable.onCreate(db);
        BranchTable.onCreate(db);
        SubjectTable.onCreate(db);
        ClassTable.onCreate(db);
        LoginTable.onCreate(db);
        SemesterTable.onCreate(db);

//        String query = "create table PersonInfo("
//                + "_id integer primary key autoincrement,"
//                + "pname text not null,"
//                + "dob text not null"
//                + ")";
//        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        CourseTable.onUpdate(db, oldVersion, newVersion);
        SemesterTable.onUpdate(db, oldVersion, newVersion);
        SubjectTable.onUpdate(db, oldVersion, newVersion);
        ClassTable.onUpdate(db, oldVersion, newVersion);
        LoginTable.onUpdate(db, oldVersion, newVersion);
        BranchTable.onUpdate(db, oldVersion, newVersion);
//        String query = "drop table if exists PersonInfo";
//        db.execSQL(query);
//        onCreate(db);
    }

}

