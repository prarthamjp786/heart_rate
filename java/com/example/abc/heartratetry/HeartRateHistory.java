package com.example.abc.heartratetry;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HeartRateHistory extends AppCompatActivity {
    static long idxy=-1;
    static TextView tx1=null;
    Context ctx=this;

    static int count=0;
    static StringBuilder sb=new StringBuilder(" ");
    public final static String MESSAGE_KEY="firsttrialkey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_history);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idxy=extras.getLong(MESSAGE_KEY);
            //The key argument here must match that used in the other activity
        }

    callDB(idxy);


    }

    void callDB(long idxy)
    {
        tx1=(TextView) findViewById(R.id.text33);
DbOpforHR dbop=new DbOpforHR(ctx);
        Cursor cr=dbop.getInfo(dbop);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        while(cr.moveToNext())
        {
if(cr.getInt(1)==idxy) {
    count++;

    String dateString = formatter.format(new Date(cr.getLong(4)));
    sb.append("Record No: "+count+"\n\n User id: " + cr.getLong(1) + "\n \n Date Time:" + dateString + "\n\n  Value: " + cr.getInt(2) + " bpm \n\n Status:" + cr.getString(3) + "\n\n\n\n");
}
        }
     //   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
       // String dateString = formatter.format(new Date(cr.getLong(4)));


if(count==0)
{
tx1.setText("No Records");
}
        else
{
    tx1.setText(sb);

}


       // tx1.setText("User id: "+cr.getLong(1) +"\n \n Date Time:"+dateString+"\n\n  Value: "+cr.getInt(2)+" bpm \n\n Status:" +cr.getString(3));

    }
    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }
}
