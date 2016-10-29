package com.mohit.college.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Mohit on 6/9/2016.
 */
public class BranchTable {


    public static final String TABLE_NAME = "branch";

    public static final String BID = "branch_id";
    public static final String BNAME = "branch_name";
    public static final String CID = "course_id";
    public static final String Available = "available_seats";



    private static final String createTableQuery = "CREATE TABLE branch(" +
            "branch_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "branch_name TEXT NOT NULL," +
            "course_id INTEGER," +
            "available_seats INTEGER NOT NULL," +
            "FOREIGN KEY(course_id) REFERENCES course(course_id)" +
            ")";


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

    public static long insert(SQLiteDatabase db, String name, String cid ,String seats)
    {
        long id = -1;

        ContentValues values = new ContentValues();

        values.put(BranchTable.BNAME,name );
        values.put(BranchTable.CID, cid);
        values.put(BranchTable.Available,seats );

        try {
            // insert(TableName, NullColumn, ContentValues);
            id = db.insert(BranchTable.TABLE_NAME, null, values);
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
    {String s[] = {id};
        int i=-1;
        try {
            i= db.delete(BranchTable.TABLE_NAME, BranchTable.BID + "=?", s);
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
