package com.mohit.college.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import javax.security.auth.Subject;

/**
 * Created by Mohit on 6/9/2016.
 */
public class SubjectTable {


    public static final String TABLE_NAME = "subject";

    public static final String SID = "subject_id";
    public static final String SNAME = "subject_name";
    public static final String SSNAME = "subject_short_name";
    public static final String SCODE= "subject_code";
    public static final String SEMID = "semester_id";
    public static final String BID = "branch_id";
    public static final String CID = "course_id";


    private static final String createTableQuery = "CREATE TABLE `subject` (\n" +
            "\t`subject_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`subject_name`\tTEXT NOT NULL,\n" +
            "\t`subject_short_name`\tTEXT NOT NULL,\n" +
            "\t`subject_code`\tTEXT NOT NULL,\n" +
            "\t`semester_id`\tINTEGER,\n" +
            "\t`branch_id`\tINTEGER,\n" +
            "\t`course_id`\tINTEGER,\n" +
            "\tFOREIGN KEY(`semester_id`) REFERENCES `semester`(`semester_id`),\n" +
            "\tFOREIGN KEY(`branch_id`) REFERENCES `branch`(`branch_id`),\n" +
            "\tFOREIGN KEY(`course_id`) REFERENCES `course`(`course_id`)\n" +
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

    public static long insert(SQLiteDatabase db, String sname, String ssname, String scode, String cid,String bid,String semid)
    {
        long id = -1;

        ContentValues values = new ContentValues();
        values.put(SubjectTable.SNAME, sname);
        values.put(SubjectTable.SSNAME,ssname);
        values.put(SubjectTable.SCODE, scode);
        values.put(SubjectTable.CID,cid);
        values.put(SubjectTable.BID,bid);
        values.put(SubjectTable.SEMID,semid);


        try {
            // insert(TableName, NullColumn, ContentValues);
            id = db.insert(SubjectTable.TABLE_NAME, null, values);
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
            i= db.delete(SubjectTable.TABLE_NAME, SubjectTable.SID + "=?", s);
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
