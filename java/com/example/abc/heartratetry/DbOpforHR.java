package com.example.abc.heartratetry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sadhana on 28-02-2017.
 */
public class DbOpforHR extends SQLiteOpenHelper {

    public final static int DATABASE_VERSION=1;
    public String CREATE_QUERY="CREATE TABLE "+ HeartRateData.HeartRateInfo.TABLE_NAME+" ("+HeartRateData.HeartRateInfo.HRID+" INTEGER primary key autoincrement,"+ HeartRateData.HeartRateInfo.UID+" INTEGER,"+ HeartRateData.HeartRateInfo.VALUE+" INTEGER,"+ HeartRateData.HeartRateInfo.STATUS+" TEXT,"+ HeartRateData.HeartRateInfo.DATETIME+" INTEGER );";
    public DbOpforHR(Context context) {
        super(context, HeartRateData.HeartRateInfo.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations HR","Database created");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operations HR","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long putInfo(DbOpforHR dop,long uid,int value,String status){
        SQLiteDatabase sq=dop.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(HeartRateData.HeartRateInfo.UID,uid);
        cv.put(HeartRateData.HeartRateInfo.VALUE,value);
        cv.put(HeartRateData.HeartRateInfo.STATUS,status);

        long time=System.currentTimeMillis();
        cv.put(HeartRateData.HeartRateInfo.DATETIME,time);

        long k= sq.insert(HeartRateData.HeartRateInfo.TABLE_NAME,null,cv);
        Log.d("Database Operations HR","One Row inserted");
        return k;
    }


    public Cursor getInfo(DbOpforHR dop){
        SQLiteDatabase sq=dop.getReadableDatabase();
        String []columns={HeartRateData.HeartRateInfo.HRID,HeartRateData.HeartRateInfo.UID,HeartRateData.HeartRateInfo.VALUE,HeartRateData.HeartRateInfo.STATUS,HeartRateData.HeartRateInfo.DATETIME};

        Cursor cr=sq.query(HeartRateData.HeartRateInfo.TABLE_NAME,columns,null,null,null,null,null);
        return cr;
    }

}
