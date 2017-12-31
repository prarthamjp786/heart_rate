package com.example.abc.heartratetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class First extends AppCompatActivity {
    TextView textview;
    public final static String MESSAGE_KEY="firsttrialkey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        textview =(TextView) findViewById(R.id.text1);
    }

    public void changeText(View view)
    {

        String buttontext;
        buttontext= ( (Button)view).getText().toString();
        if(buttontext.equals("Login"))
        {
            Intent intent=new Intent(this,Login.class);
            startActivity(intent);
        }
        if (buttontext.equals("Register"))
        {
            Intent intent=new Intent(this,Register.class);
            startActivity(intent);
        }
    }


    public void goHome(View view){
        Intent intent=new Intent(this,HomePage.class);
        String name="User";intent.putExtra(MESSAGE_KEY,name);
        startActivity(intent);
    }
}
