package com.example.abc.heartratetry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button bCHR;
    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bCHR=(Button) findViewById(R.id.buttonCHR);
bCHR.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, HeartRateChecker.class);
                HeartRateChecker.flagforavg=0;
                startActivity(intent);
            }
        }



);
    }
}
