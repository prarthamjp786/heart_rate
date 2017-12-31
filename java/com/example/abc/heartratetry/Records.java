package com.example.abc.heartratetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Records extends AppCompatActivity {
    ImageButton hrhistorybutton=null;
    ImageButton calhistorybutton=null;
    static long myvalue=-1;

    public final static String MESSAGE_KEY="firsttrialkey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        hrhistorybutton=(ImageButton) findViewById(R.id.imageButtonHR);
        calhistorybutton=(ImageButton) findViewById(R.id.imageButtonC);

    }

    public void checkHRHistory(View v)
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myvalue=extras.getLong(MESSAGE_KEY);
            //The key argument here must match that used in the other activity
        }
        Intent intent = new Intent(getBaseContext(), HeartRateHistory.class);
        intent.putExtra(MESSAGE_KEY,myvalue);
        HeartRateHistory.sb=new StringBuilder(" ");
        HeartRateHistory.count=0;
        HeartRateChecker.flagforavg=0;
        HeartRateChecker.flagforupdate=0;
        startActivity(intent);
    }

    public void checkCHistory(View v)
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myvalue=extras.getLong(MESSAGE_KEY);
            //The key argument here must match that used in the other activity
        }
        Intent intent = new Intent(getBaseContext(), CalorieHistory.class);
        intent.putExtra(MESSAGE_KEY,myvalue);
        CalorieHistory.sbc=new StringBuilder(" ");
        CalorieHistory.countc=0;
        HeartRateChecker.flagforavg=0;
        HeartRateChecker.flagforupdate=0;
        startActivity(intent);
    }
}
