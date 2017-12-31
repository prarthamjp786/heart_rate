package com.example.abc.heartratetry;

import android.provider.BaseColumns;

/**
 * Created by sadhana on 23-03-2017.
 */

public class CalorieData {
    public static abstract class CalorieInfo implements BaseColumns {
        public final static String CID="cid";
        public final static String UID="uid";

        public final static String HRID="hrid";


        public final static String DATETIME="datetime";
        public final static String VALUE="value";
        public final static String DATABASE_NAME="calories";
        public final static String TABLE_NAME="caloriesrecord";




    }
}
