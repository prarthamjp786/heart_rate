package com.example.abc.heartratetry;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;

/**
 * Created by sadhana on 30-10-2016.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION=1;
    public String CREATE_QUERY="CREATE TABLE "+ TableData.TableInfo.TABLE_NAME+" ("+TableData.TableInfo.ID+" INTEGER primary key autoincrement,"+ TableData.TableInfo.NAME+" TEXT,"+ TableData.TableInfo.PASSWORD+" TEXT,"+ TableData.TableInfo.EMAIL+" TEXT,"+ TableData.TableInfo.GENDER+" TEXT,"+ TableData.TableInfo.AGE+" INT );";

    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operations","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long putInfo(DatabaseOperations dop,String name,String pass,String email,String gender,int age){
        SQLiteDatabase sq=dop.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableData.TableInfo.NAME,name);
        cv.put(TableData.TableInfo.PASSWORD,pass);
        cv.put(TableData.TableInfo.EMAIL,email);
        cv.put(TableData.TableInfo.GENDER,gender);
        cv.put(TableData.TableInfo.AGE,age);
        long k= sq.insert(TableData.TableInfo.TABLE_NAME,null,cv);
        Log.d("Database Operations","One Row inserted");
    return k;
    }


    public Cursor getInfo(DatabaseOperations dop){
        SQLiteDatabase sq=dop.getReadableDatabase();
        String[] columns={TableData.TableInfo.ID,TableData.TableInfo.NAME, TableData.TableInfo.PASSWORD, TableData.TableInfo.EMAIL,TableData.TableInfo.GENDER, TableData.TableInfo.AGE};
        Cursor cr=sq.query(TableData.TableInfo.TABLE_NAME,columns,null,null,null,null,null,null);
        return cr;
    }

}
