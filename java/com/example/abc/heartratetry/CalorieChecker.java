package com.example.abc.heartratetry;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CalorieChecker extends AppCompatActivity {
    public final static String MESSAGE_KEY="firsttrialkey";
    public final static String MESSAGE_KEY2="secondkey";
    static long idxy=-1;
    static int finalHRvalue=0;
    Context ctx=this;
    private static TextView displaycalorie = null;
    private static EditText age=null;
    private static EditText weight=null;
    private static EditText time=null;
    RadioButton RB,RB1;
    Button b1,b2;
    static String genderCB;
    static String agevalueS;
    static String weightvalueS;
    static String timevalueS;
    static double agevalue=0;
    static double weightvalue=0;
    static double timevalue=0;
static double finalCalorieValue=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_checker);
        Bundle extras =getIntent().getExtras();
        if (extras != null) {
            idxy=extras.getLong(MESSAGE_KEY);
            finalHRvalue=extras.getInt(MESSAGE_KEY2);

            //The key argument here must match that used in the other activity
        }
        DatabaseOperations dop=new DatabaseOperations(ctx);
        Cursor cr=dop.getInfo(dop);
        cr.moveToFirst();
        performCalculation();
    }
    static void performCalculation()
    {

    }
    public void calculateCalorie(View v)
    {
age=(EditText) findViewById(R.id.age);
        weight=(EditText) findViewById(R.id.weight1);
        time=(EditText) findViewById(R.id.timeduration);
        RB=(RadioButton)findViewById(R.id.radiomale);
        if(RB.isChecked())
        {
            RB1=RB;
            genderCB="male";

        }
        RB=(RadioButton)findViewById(R.id.radiofemale);
        if(RB.isChecked())
        {
            RB1=RB;
            genderCB="female";

        }
agevalueS=age.getText().toString();
        if(!agevalueS.equals("")) {
            agevalue=Double.parseDouble(agevalueS);

        }
        weightvalueS=weight.getText().toString();
        if(!weightvalueS.equals("")) {
            weightvalue=Double.parseDouble(weightvalueS);

        }
        timevalueS=time.getText().toString();
        if(!timevalueS.equals("")) {
            timevalue=Double.parseDouble(timevalueS);

        }
        if(genderCB.equals("male"))
        {
            finalCalorieValue=((-55.0969 + (0.6309*finalHRvalue) + (0.1988*weightvalue) + (0.2017*agevalue))/4.184)*60*timevalue;
        finalCalorieValue=Math.round(finalCalorieValue*100.0)/100.0;
        }
        if(genderCB.equals("female"))
        {
            finalCalorieValue=((-20.4022 + (0.4472*finalHRvalue) - (0.1263*weightvalue) + (0.074*agevalue))/4.184)*60*timevalue;
            finalCalorieValue=Math.round(finalCalorieValue*100.0)/100.0;
        }

        displaycalorie=(TextView) findViewById(R.id.displaycalorie);
        displaycalorie.setVisibility(View.VISIBLE);
        displaycalorie.setText("Calories Burnt: "+finalCalorieValue+" calories");
        b2=(Button) findViewById(R.id.buttonforsaveCB);
   b2.setVisibility(View.VISIBLE);
    }
    public  void saveCB(View v)
    {
        if(idxy==-1) {
            Toast.makeText(CalorieChecker.this, "You need to Login to save record", Toast.LENGTH_LONG).show();
        }
        else
        {
            DbOpforHR DB = new DbOpforHR(ctx);
            long idHR = DB.putInfo(DB, idxy, finalHRvalue, "After Exercise");


            if (idHR != -1) {




                DbOpforCB DB2 = new DbOpforCB(ctx);
                long idHR2 = DB2.putInfo(DB2,idxy,idHR, finalCalorieValue);


                if (idHR2 != -1) {
                    Toast.makeText(CalorieChecker.this, "Record Saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ctx, HomePage.class);
                    intent.putExtra(MESSAGE_KEY, idxy);
                    startActivity(intent);
                }





            }





        }
    }
}
