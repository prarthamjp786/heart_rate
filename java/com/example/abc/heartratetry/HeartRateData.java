package com.example.abc.heartratetry;

import android.provider.BaseColumns;

/**
 * Created by sadhana on 28-02-2017.
 */
public class HeartRateData {


    public static abstract class HeartRateInfo implements BaseColumns {
        public final static String HRID="hrid";
        public final static String UID="uid";

public final static String STATUS="status";
        public final static String DATETIME="datetime";
        public final static String VALUE="value";
        public final static String SPVALUE="spvalue";
        public final static String DATABASE_NAME="heartrate";
        public final static String TABLE_NAME="heartraterecord";




    }
}
