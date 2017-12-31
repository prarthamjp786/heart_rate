package com.example.abc.heartratetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MyFiles extends AppCompatActivity {
    static long myvalue=-1;
    ImageButton ib1=null,ib2=null;
    public final static String MESSAGE_KEY="firsttrialkey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_files);

    }
    public void checkUploadedFiles(View v)
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myvalue=extras.getLong(MESSAGE_KEY);
            //The key argument here must match that used in the other activity
        }
        Intent intent = new Intent(getBaseContext(), HeartRateHistory.class);
        intent.putExtra(MESSAGE_KEY,myvalue);
        startActivity(intent);
    }

    public void uploadFiles(View v)
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myvalue=extras.getLong(MESSAGE_KEY);
            //The key argument here must match that used in the other activity
        }
        Intent intent = new Intent(getBaseContext(), uploadfilepage.class);
        intent.putExtra(MESSAGE_KEY,myvalue);
        startActivity(intent);
    }
}
