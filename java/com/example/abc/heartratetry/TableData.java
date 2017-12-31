package com.example.abc.heartratetry;

import android.provider.BaseColumns;

/**
 * Created by sadhana on 27-02-2017.
 */
public class TableData {

    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns {
        public final static String ID="uid";
        public final static String NAME="name";
        public final static String PASSWORD="password";
        public final static String EMAIL="email";
        public final static String GENDER="gender";
        public final static String AGE="age";
        public final static String DATABASE_NAME="user";
        public final static String TABLE_NAME="user_info";




    }


}
