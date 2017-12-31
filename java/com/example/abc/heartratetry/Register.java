package com.example.abc.heartratetry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by sadhana on 28-10-2016.
 */
public class Register extends Activity{
    EditText text_message,NAME,PASS,EMAIL,AGE,CONPASS;
    RadioButton RB,RB1;
    String name,pass,email,gender,conpass,agec;
    int age;
    Button REG;
    Context ctx= this;
    long idvalue;

    public final static String MESSAGE_KEY="firsttrialkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        NAME= (EditText) findViewById(R.id.name);
        PASS= (EditText) findViewById(R.id.password);
        EMAIL= (EditText) findViewById(R.id.email);




        RB=(RadioButton)findViewById(R.id.radioButtonmale);
        if(RB.isChecked())
        {
            RB1=RB;
            gender="male";

        }
        RB=(RadioButton)findViewById(R.id.radioButtonfemale);
        if(RB.isChecked())
        {
            RB1=RB;
            gender="female";

        }
        AGE= (EditText) findViewById(R.id.editTextage);
        CONPASS= (EditText) findViewById(R.id.confirmpassword);




        REG=(Button) findViewById(R.id.buttonregister);
        REG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean age2=false;
                boolean statustext=true;
                boolean radiostatus=false;
                name = NAME.getText().toString();
                pass = PASS.getText().toString();
                email = EMAIL.getText().toString();
                conpass = CONPASS.getText().toString();
                agec=AGE.getText().toString();
                if(!agec.equals("")) {
                    age = Integer.parseInt(agec);
                    age2=true;
                    statustext=true;
                }
                else
                {
                    statustext=false;
                }
////////


                if(name.equals(""))
                {
                    statustext=false;
                }


                if(pass.equals(""))
                {
                    statustext=false;
                }



                if(email.equals(""))
                {
                    statustext=false;
                }



                if(conpass.equals(""))
                {
                    statustext=false;
                }




                RB=(RadioButton)findViewById(R.id.radioButtonmale);
                if(RB.isChecked())
                {


                    radiostatus=true;
                }

                RB=(RadioButton)findViewById(R.id.radioButtonfemale);
                if(RB.isChecked())
                {
                    radiostatus=true;
                }
                if(!radiostatus)
                {
                    statustext=false;
                }









                boolean agecon=false;
                if(age2) {
                    if (age > 150 || age < 1) {
                        statustext = false;
                        agecon = true;
                        Toast.makeText(getApplicationContext(), "Please enter valid age", Toast.LENGTH_SHORT).show();
                    }

                }
                ////////

                if(statustext)
                {
                    if (!(pass.equals(conpass))) {
                        Toast.makeText(getBaseContext(), "Passwords do not match", Toast.LENGTH_LONG).show();

                    } else {
                        DatabaseOperations DB = new DatabaseOperations(ctx);
                        idvalue=DB.putInfo(DB, name, pass, email, gender, age);

                        Toast.makeText(getBaseContext(), "Registration successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ctx, HomePage.class);
                        intent.putExtra(MESSAGE_KEY, idvalue);
                        startActivity(intent);


                    }

                }
                else
                {

                    if(!agecon) {
                        Toast.makeText(getApplicationContext(), "Please fill all the details", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });




    }

    public void doRegister(View view)
    {

    }
}
