package com.mohit.college.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Mohit on 6/9/2016.
 */
public class LoginTable {

    public static final String TABLE_NAME = "login";

    public static final String EMAIL = "email";
    public static final String PASS = "pass";


    private static final String createTableQuery = "CREATE TABLE `login` (\n" +
            "\t`email`\tTEXT,\n" +
            "\t`pass`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(email)\n" +
            ")";


    public static void onCreate(SQLiteDatabase db){

        try{
            db.execSQL(createTableQuery);
            long id = LoginTable.insert(db, "admin@admin.com", "admin");
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

        ContentValues values = new ContentValues();
        values.put(LoginTable.EMAIL, name);
        values.put(LoginTable.PASS,pass );

        try {
            // insert(TableName, NullColumn, ContentValues);
            id = db.insert(LoginTable.TABLE_NAME, null, values);
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

    public static void delete()
    {

    }

    public static int update(SQLiteDatabase db, String tableName, ContentValues values, String whereClause, String[] whereArgs)
    {

        int i = db.update(tableName, values, whereClause, whereArgs );
        return i;
    }
}
