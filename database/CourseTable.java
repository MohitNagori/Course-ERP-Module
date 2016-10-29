package com.mohit.college.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Mohit on 6/9/2016.
 */
public class CourseTable {


    public static final String TABLE_NAME = "course";

    public static final String CID = "course_id";
    public static final String CNAME = "course_name";


    private static final String createTableQuery = "create table course("
            + "course_id integer primary key autoincrement,"                                //varchar(255)
            + "course_name text not null"
            + ")";


    public static void onCreate(SQLiteDatabase db){

        try{
            db.execSQL(createTableQuery);
        }
        catch(Exception e)
        {
            Log.d("1234", "create table exception " + e);
        }

    }

    public static void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion){
		/*
		 * Check here for old version and new version
		 * */
        try{
            String query = "drop table if exists "+TABLE_NAME;
            db.execSQL(query);
            onCreate(db);
        }
        catch(Exception e)
        {
            Log.d("1234", "update table exception "+e);
        }

    }

    public static long insert(SQLiteDatabase db, String name)
    {

        long id = -1;
        ContentValues values = new ContentValues();
        values.put(CourseTable.CNAME, name);

        try {
            // insert(TableName, NullColumn, ContentValues);
            id = db.insert(CourseTable.TABLE_NAME, null, values);
        }
        catch(Exception e)
        {
            Log.d("1234", "exception in insert "+e);
        }
        return id;
    }

    public static Cursor select(SQLiteDatabase db, String tableName, String[] columns, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy, String limit)
    {

        Cursor cursor = null;
        try{

            cursor = db.query(tableName, columns, whereClause, whereArgs, groupBy, having, orderBy);
        }
        catch(Exception e)
        {
            Log.d("1234", "exception in select "+e);
        }
        return cursor;
    }

    public static int delete(SQLiteDatabase db,String id)
    {   String s[] = {id};
        int i=-1;
        try {
           i= db.delete(CourseTable.TABLE_NAME, CourseTable.CID + "=?", s);
        }
        catch(Exception e)
        {
            Log.d("1234", "exception in insert "+e);
        }
        return i;
    }

    public static int update(SQLiteDatabase db, String tableName, ContentValues values, String whereClause, String[] whereArgs)
    {

        int i = db.update(tableName, values, whereClause, whereArgs );
        return i;
    }

}
