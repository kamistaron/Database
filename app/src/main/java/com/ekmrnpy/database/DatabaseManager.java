package com.ekmrnpy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {


    private static final String DatabaseName = "myinfo.db";
    private static final int Version = 1;
    private static final String TableName = "tbl_person";
    private static final String dID = "id";
    private static final String dName = "name";
    private static final String dFamily = "family";

    public DatabaseManager(Context context) {
        super(context, DatabaseName, null, Version);
        Log.i("kamran", "Database Created");
    }


    @Override
    public void onCreate(SQLiteDatabase cdb) {

        //String cQuery = " CREATE TABLE " + TableName +  "(" + dID + "," +  dName + "TEXT," + dFamily + "TEXT," + "PRIMARY KEY(" + dID + "));";
        String cQuery = "CREATE TABLE " + TableName + "(" + dID + " INTEGER PRIMARY KEY," + dName + " TEXT, " + dFamily + " TEXT );";
        cdb.execSQL(cQuery);
        Log.i("kamran","Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertPerson(Person iprs) {


        SQLiteDatabase idb = this.getWritableDatabase();
        ContentValues icv =new ContentValues();
        icv.put(dID, iprs.pID);
        icv.put(dName, iprs.pName);
        icv.put(dFamily, iprs.pFamily);

        idb.insert(TableName, null,icv);
        idb.close();
    }

    public Person getPerson(String gID){
        Person gPrs = new Person();
        SQLiteDatabase gdb = this.getReadableDatabase();
        String gQuery = "SELECT * FROM " + TableName + " WHERE " + dID + "=" + gID;
        Cursor gCur = gdb.rawQuery(gQuery, null);
        if (gCur.moveToFirst()){
            gPrs.pName = gCur.getString(1);
            gPrs.pFamily = gCur.getString(2);
        }
        Log.i("kamran", "getPerson Method");
        return gPrs;
    }
    public void updatePerson(Person uprs){
        SQLiteDatabase udb = this.getWritableDatabase();
        ContentValues ucv = new ContentValues();
        ucv.put(dName, uprs.pName);
        ucv.put(dFamily, uprs.pFamily);
        udb.update(TableName, ucv, dID + " = ?", new String[] {String.valueOf(uprs.pID)});
        Log.i("kamran", "updated table method");
    }

    public boolean deletePerson(Person dprs){
        SQLiteDatabase ddb = this.getWritableDatabase();
        long dResult = ddb.delete(TableName, dID + "=?", new String[] {String.valueOf(dprs.pID)});

        Log.i("kamran", "deletePerson method");

        if (dResult == 0)
            return false;
        else
            return true;
    }

    public int personCount() {
        String gQuery = "SELECT * FROM " + TableName;
        SQLiteDatabase gdb = this.getReadableDatabase();
        Cursor gCur = gdb.rawQuery(gQuery, null);
        int cResult = gCur.getCount();
        return cResult;
    }

}
