package com.example.abc.heartratetry;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalorieHistory extends AppCompatActivity {
    static long idxy=-1;
    static TextView textnew=null;
    Context ctx=this;

    static int countc=0;
    static StringBuilder sbc=new StringBuilder(" ");
    public final static String MESSAGE_KEY="firsttrialkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_history);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idxy=extras.getLong(MESSAGE_KEY);
            //The key argument here must match that used in the other activity
        }
        callDB(idxy);

    }
    void callDB(long idxy)
    {
textnew=(TextView) findViewById(R.id.textshowhistory);
        DbOpforCB dbop=new DbOpforCB(ctx);
        Cursor cr=dbop.getInfo(dbop);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        while(cr.moveToNext())
        {
            if(cr.getInt(1)==idxy) {
                countc++;

                String dateString = formatter.format(new Date(cr.getLong(4)));
                sbc.append("Record No: "+countc+"\n\n User id: " + cr.getLong(1) + "\n \n Date Time:" + dateString + "\n\n  Value: " + cr.getDouble(3) + " calories \n\n " + "\n\n\n\n");
            }
        }
        //   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        // String dateString = formatter.format(new Date(cr.getLong(4)));


        if(countc==0)
        {
            textnew.setText("No Records");
        }
        else
        {
            textnew.setText(sbc);

        }


        // tx1.setText("User id: "+cr.getLong(1) +"\n \n Date Time:"+dateString+"\n\n  Value: "+cr.getInt(2)+" bpm \n\n Status:" +cr.getString(3));

    }
    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }
}
