package com.example.abc.heartratetry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sadhana on 28-10-2016.
 */
public class Login extends Activity {
    EditText text_message;
    EditText USERNAME,PASSWORD;
    String username,password;
    Button b1;
    long idvalue;
String username1,useremail;

    Context ctx=this;
    public final static String MESSAGE_KEY="firsttrialkey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        USERNAME=(EditText) findViewById(R.id.username);
        PASSWORD=(EditText) findViewById(R.id.password);
        b1=(Button) findViewById(R.id.login1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=USERNAME.getText().toString();
                password=PASSWORD.getText().toString();
                DatabaseOperations dop=new DatabaseOperations(ctx);
                Cursor cr=dop.getInfo(dop);
                cr.moveToFirst();
                boolean loginstatus=false;
                boolean usernamestatus=false;
                do {
                    if(username.equals(cr.getString(3)))
                    {
                        usernamestatus=true;
                        if(password.equals(cr.getString(2)))
                        {
                            loginstatus=true;

                            break;//login success
                        }

                    }

                }while(cr.moveToNext());



                if(usernamestatus==true && loginstatus==false)
                {
                    Toast.makeText(getApplicationContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();
                }
                if(!usernamestatus)
                {
                    Toast.makeText(getApplicationContext(), "Username does not exist \n \t\t\t\t\t OR \n Registration is not done", Toast.LENGTH_SHORT).show();
                }












                String name="";
                if(loginstatus)
                { //login is done
                    idvalue=cr.getLong(0);

                    Intent intent=new Intent(ctx,HomePage.class);
                    intent.putExtra(MESSAGE_KEY,idvalue);
                    startActivity(intent);
                }

            }
        });


    }


}