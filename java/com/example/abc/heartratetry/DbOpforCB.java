package com.example.abc.heartratetry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sadhana on 23-03-2017.
 */

public class DbOpforCB extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION=1;
    public String CREATE_QUERY="CREATE TABLE "+ CalorieData.CalorieInfo.TABLE_NAME+" ("+CalorieData.CalorieInfo.CID+" INTEGER primary key autoincrement,"+ CalorieData.CalorieInfo.UID+" INTEGER,"+ CalorieData.CalorieInfo.HRID+" INTEGER,"+ CalorieData.CalorieInfo.VALUE+" REAL,"+ HeartRateData.HeartRateInfo.DATETIME+" INTEGER );";
    public DbOpforCB(Context context) {
        super(context, CalorieData.CalorieInfo.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations CB","Database created");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operations CB","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long putInfo(DbOpforCB dop,long uid,long hrid,double value){
        SQLiteDatabase sq=dop.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(CalorieData.CalorieInfo.UID,uid);
        cv.put(CalorieData.CalorieInfo.HRID,hrid);
        cv.put(CalorieData.CalorieInfo.VALUE,value);


        long time=System.currentTimeMillis();
        cv.put(CalorieData.CalorieInfo.DATETIME,time);

        long k= sq.insert(CalorieData.CalorieInfo.TABLE_NAME,null,cv);
        Log.d("Database Operations CB","One Row inserted");
        return k;
    }


    public Cursor getInfo(DbOpforCB dop){
        SQLiteDatabase sq=dop.getReadableDatabase();
        String []columns={CalorieData.CalorieInfo.CID,CalorieData.CalorieInfo.UID,CalorieData.CalorieInfo.HRID,CalorieData.CalorieInfo.VALUE,CalorieData.CalorieInfo.DATETIME};

        Cursor cr=sq.query(CalorieData.CalorieInfo.TABLE_NAME,columns,null,null,null,null,null);
        return cr;
    }
}
