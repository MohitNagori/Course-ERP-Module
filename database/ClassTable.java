package com.mohit.college.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Mohit on 6/9/2016.
 */
public class ClassTable {

    public static final String TABLE_NAME = "class";

    public static final String CLASSID = "class_id";
    public static final String CNAME = "class_name";
    public static final String SID = "subject_id";
    public static final String CLASSDUR = "class_duration";
    public static final String STIME = "start_time";
    public static final String SEMID = "semester_id";
    public static final String BID = "branch_id";
    public static final String CID = "course_id";



    private static final String createTableQuery = "CREATE TABLE `class` (\n" +
            "\t`class_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`class_name`\tTEXT NOT NULL,\n" +
            "\t`subject_id`\tINTEGER,\n" +
            "\t`class_duration`\tINTEGER NOT NULL,\n" +
            "\t`start_time`\tTEXT NOT NULL,\n" +
            "\t`semester_id`\tINTEGER,\n" +
            "\t`branch_id`\tINTEGER,\n" +
            "\t`course_id`\tINTEGER,\n" +
            "\tFOREIGN KEY(`subject_id`) REFERENCES `subject`(`subject_id`),\n" +
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

    public static long insert(SQLiteDatabase db, String cname, String cduration, String cstart, String cid , String bid, String sid, String semid)
    {
        long id = -1;

        ContentValues values = new ContentValues();
        values.put(ClassTable.CNAME, cname);
        values.put(ClassTable.CLASSDUR,cduration);
        values.put(ClassTable.STIME, cstart);
        values.put(ClassTable.CID,cid);
        values.put(ClassTable.BID, bid);
        values.put(ClassTable.SID,sid);
        values.put(ClassTable.SEMID,semid);


        try {
            // insert(TableName, NullColumn, ContentValues);
            id = db.insert(ClassTable.TABLE_NAME, null, values);
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
            i= db.delete(ClassTable.TABLE_NAME, ClassTable.CLASSID + "=?", s);
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
