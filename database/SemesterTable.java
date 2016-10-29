package com.mohit.college.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Mohit on 6/9/2016.
 */
public class SemesterTable {


    public static final String TABLE_NAME = "semester";

    public static final String SID = "semester_id";
    public static final String SNAME = "semester_name";


    private static final String createTableQuery = "CREATE TABLE `semester` (\n" +
            "\t`semester_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`semester_name`\tTEXT NOT NULL\n" +
            ")";


    public static void onCreate(SQLiteDatabase db){

        try{
            db.execSQL(createTableQuery);
            ContentValues values = new ContentValues();
            String s[]= {"I","II","III","IV","V","VI","VII","VIII"};
            for(int i=0;i<8;i++) {
                values.put(SemesterTable.SNAME,s[i]);
                db.insert(SemesterTable.TABLE_NAME, null, values);
            }
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

    public static long insert(SQLiteDatabase db, String name, String pass)
    {
        long id = -1;
/*

        ContentValues values = new ContentValues();
        values.put(PersonInfo.PNAME, name);
        values.put(PersonInfo.Pass,pass );

        try {
            // insert(TableName, NullColumn, ContentValues);
            id = db.insert(PersonInfo.TABLE_NAME, null, values);
        }
        catch(Exception e)
        {
            Log.d("1234", "exception in insert "+e);
        }
*/

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

    public static void delete()
    {

    }

    public static int update(SQLiteDatabase db, String tableName, ContentValues values, String whereClause, String[] whereArgs)
    {

        int i = db.update(tableName, values, whereClause, whereArgs );
        return i;
    }
}
