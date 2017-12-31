package com.example.abc.heartratetry;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



public class HeartRateChecker extends AppCompatActivity {

    public final static String MESSAGE_KEY="firsttrialkey";
    public final static String MESSAGE_KEY2="secondkey";
    private Timer timer = new Timer();

    private TimerTask task;
    private static int gx;
    private static int j;
    static  StringBuilder sb11=new StringBuilder(" ");
    private static double flag = 1;
    private Handler handler;
    private String title = "pulse";
    Context ctx= this;
    private Context context;
    private int addX = -1;
    double addY;
    static int flagforupdate=0;
    int[] xv = new int[300];
    int[] yv = new int[300];
    int[] hua=new int[]{9,10,11,12,13,14,13,12,11,10,9,8,7,6,7,8,9,10,11,10,10};
    static int[] value1=new int[10];
    static int[] value1count=new int[11];
 static int flagforprogressimage=0;
    static int flagforavg=0;
    static int finalHRvalue=0;
    static int flagforalert=0;
    static int redcompsum=0;
    static int redcompcount=0;
    static  int bluecompsum=0;
    static int bluecompcount=0;
    static double meanred=0d;
    static double meanblue=0d;
    static double stddevred=0d;
    static double stddevblue=0d;
    static Vector<Integer> vred=new Vector<Integer>();
    static Vector<Integer> vblue=new Vector<Integer>();

    private static final AtomicBoolean processing = new AtomicBoolean(false);

    private static SurfaceView preview = null;

    private static SurfaceHolder previewHolder = null;

    private static Camera camera = null;
    //private static View image = null;
    private static TextView mTV_Heart_Rate = null;
    private static TextView mTV_Avg_Pixel_Values = null;
    private static TextView mTV_pulse = null;
    private static WakeLock wakeLock = null;
    private static TextView heartrate_value = null;
    private static TextView heartrate_valuepr = null;
    private static TextView processingtext = null;
    private static TextView beforesave = null;
    private static TextView checkaccuracy= null;
    private static Button buttonforSave=null;
    private static RadioGroup radiogroupforStatus=null;
    private static ImageView progressimage=null;
    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];
    static long idxy=-1;
    static int flagsame=0;
    /**
     *
     */
    public static enum TYPE {
        GREEN, RED
    };

    private static TYPE currentType = TYPE.GREEN;

    public static TYPE getCurrent() {
        return currentType;
    }

    private static int beatsIndex = 0;

    private static final int beatsArraySize = 3;

    private static final int[] beatsArray = new int[beatsArraySize];

    private static double beats = 0;

    private static long startTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_checker);
        makeThemZero(value1);
        initConfig();



    }


    private static void makeThemZero(int varray[]){
        for(int i1=0;i1<varray.length;i1++)
        {
            varray[i1]=0;
        }
    }
    private void initConfig() {

        context = getApplicationContext();
        int color = Color.GREEN;


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {


                if(flagforupdate==0) {
                    updateChart();
                    super.handleMessage(msg);
                }
            }
        };

        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        timer.schedule(task, 1,20);

        preview = (SurfaceView) findViewById(R.id.id_preview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mTV_Heart_Rate = (TextView) findViewById(R.id.id_tv_heart_rate);
        mTV_Avg_Pixel_Values = (TextView) findViewById(R.id.id_tv_Avg_Pixel_Values);
        mTV_pulse = (TextView) findViewById(R.id.id_tv_pulse);
        heartrate_value = (TextView) findViewById(R.id.heartrate_value);
        heartrate_valuepr = (TextView) findViewById(R.id.heartrate_valuepr);
        radiogroupforStatus=(RadioGroup)findViewById(R.id.radiogroupforStatus);
        buttonforSave=(Button)findViewById(R.id.buttonforSave);
        processingtext = (TextView) findViewById(R.id.processingtext);
        beforesave = (TextView) findViewById(R.id.beforesave);
        checkaccuracy=(TextView) findViewById(R.id.checkaccuracy);
progressimage=(ImageView) findViewById(R.id.id_imageprogress);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
    }


    @Override
    public void onDestroy() {

        timer.cancel();
        super.onDestroy();
    };


    private void updateChart() {

        if(flag == 1) {
            addY = 10;
        }
        else {
            flag = 1;
            if(gx < 180){
                if(hua[20] > 1){
                    Toast.makeText(HeartRateChecker.this, "Please cover your camera with your fingertips!", Toast.LENGTH_SHORT).show();
                    hua[20] = 0;
                }
                hua[20]++;
                return;
            }
            else {
                hua[20] = 10;
            }
            j = 0;
        }
        if(j < 20){
            addY=hua[j];
            j++;
        }


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        camera = Camera.open();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }



    private static PreviewCallback previewCallback = new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera cam) {
            if (data == null) {
                throw new NullPointerException();
            }
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null) {
                throw new NullPointerException();
            }
            if (!processing.compareAndSet(false, true)) {
                return;
            }
            int width = size.width;
            int height = size.height;


            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(),height,width);
            gx = imgAvg;
            int imgAvgB = ImageProcessing.decodeYUV420SPtoBlueAvg(data.clone(),height,width);
    /*        mTV_Avg_Pixel_Values.setText("\n" +  "The average pixel value is" + String.valueOf(imgAvg));*/

            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false);
                return;
            }
            if (imgAvgB == 0 || imgAvgB == 255) {
                processing.set(false);
                return;
            }
vred.add(imgAvg);
            vblue.add(imgAvgB);
            redcompsum=redcompsum+imgAvg;
            redcompcount++;
            bluecompsum=bluecompsum+imgAvgB;
            bluecompcount++;

            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }


            int rollingAverage = (averageArrayCnt > 0)?(averageArrayAvg/averageArrayCnt):0;
            TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    flag=0;
             /*       mTV_pulse.setText("\n" + "The number of pulses is" + String.valueOf(beats));*/
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if(averageIndex == averageArraySize) {
                averageIndex = 0;
            }
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            if (newType != currentType) {
                currentType = newType;
            }


            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if (totalTimeInSecs >= 2) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180|| imgAvg < 180) {

                    startTime = System.currentTimeMillis();
                    //beats
                    beats = 0;
                    processing.set(false);
                    return;
                }

                if(beatsIndex == beatsArraySize) {
                    beatsIndex = 0;
                }
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;

                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {
                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                int beatsAvg = (beatsArrayAvg / beatsArrayCnt);

                //additional
                progressimage.setVisibility(View.VISIBLE);
                if(flagforprogressimage==0) {
                    progressimage.setImageResource(R.drawable.progressrep20p);
                    flagforprogressimage=1;
                } calculationPart(beatsAvg);

                //additionalend
/*

                mTV_Heart_Rate.setText("\n" +"Your heart rate is"+String.valueOf(beatsAvg) +
                        " \n" +"value:" + String.valueOf(beatsArray.length) +
                        "    " + String.valueOf(beatsIndex) +
                        "    " + String.valueOf(beatsArrayAvg) +
                        "    " + String.valueOf(beatsArrayCnt));
             ?ms?*/
                startTime = System.currentTimeMillis();
                beats = 0;
            }
            processing.set(false);
        }
    };


    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (Throwable t) {
                Log.e("PreviewsurfaceCallback","Exception in setPreviewDisplay()", t);
            }
        }


        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    /**

     */
    private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                }
                else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea) {
                        result = size;
                    }
                }
            }
        }
        return result;
    }

    //additional function

    private  static void  calculationPart(int x)
    {
        int i2=0;

        for(int i1=0;i1<9;i1++)
        {
            value1[i1]=value1[i1+1];
        }
        value1[9]=x;
        int sum=0,countofzero=0;
        int avgofsum=90;/*
        if(flagforprogressimage==0) {
            progressimage.setImageResource(R.drawable.progressrep30p);
        }*/
        for (int i1=0;i1<10;i1++)
        {
            if( value1[i1]==0){
                countofzero++;
            }
           sum+=value1[i1];
        }
      avgofsum=sum/10;
        if(countofzero<=8 && countofzero>=7)
        {
            if(flagforprogressimage==1) {
                progressimage.setImageResource(R.drawable.progressrep30p);
            }
        }
        if(countofzero==6)
        {
            if(flagforprogressimage==1) {
                progressimage.setImageResource(R.drawable.progressrep40p);
            }
        }

        if(countofzero<=5 && countofzero >=4 )
        {
            if(flagforprogressimage==1) {
                progressimage.setImageResource(R.drawable.progressrep50p);
            }
        }

        if(countofzero==3)
        {
            if(flagforprogressimage==1) {
                progressimage.setImageResource(R.drawable.progressrep60p);
            }
        }

        if(countofzero==2)
        {
            if(flagforprogressimage==1) {
                progressimage.setImageResource(R.drawable.progressrep70p);
            }
        }

        if(countofzero==1)
        {
            if(flagforprogressimage==1) {
                progressimage.setImageResource(R.drawable.progressrep80p);
            }
        }


        /*
        if(flagforprogressimage==0) {
            progressimage.setImageResource(R.drawable.progressrep40p);
        }*/
        if(countofzero==0 && flagforavg==0)
        {

            if(flagforprogressimage==1) {
                progressimage.setImageResource(R.drawable.progressrep90p);
            }
            for(int val=0;val<10;val++)
            {
                          sb11.append(""+value1[val]+",");
            }


            /*
            if(flagforprogressimage==0) {
                progressimage.setImageResource(R.drawable.progressrep50p);
            }*/
            int index1=similarHrate(avgofsum);


            //

//
/*
            if(avgofsum>125 && avgofsum<150)
            {
                Random r=new Random();
                 i2 = r.nextInt(125 - 100) + 100;
                avgofsum=i2;
            }
             if(avgofsum>=150)
            { Random r=new Random();
                i2 = r.nextInt(135 - 120) + 120;
                avgofsum=i2;
            }*/
            finalHRvalue=index1;
            //
meanred=(double)redcompsum/redcompcount;
            meanblue=(double)bluecompsum/bluecompcount;
            calculateStandardDev();
            int spovalue=(int)(100-5*((stddevred/meanred)/(stddevblue/meanblue)));
            processingtext.setVisibility(View.INVISIBLE);
            if(flagforprogressimage==1) {
                progressimage.setImageResource(R.drawable.progressrep100p);
                flagforprogressimage=2;
            }
            heartrate_valuepr.setVisibility(View.VISIBLE);
            heartrate_value.setText("Heart Rate: "+index1+" bpm"+"\n Spo2 :  "+spovalue+ " %");
            heartrate_value.setVisibility(View.VISIBLE);
            beforesave.setVisibility(View.VISIBLE);
            radiogroupforStatus.setVisibility(View.VISIBLE);
            buttonforSave.setVisibility(View.VISIBLE);
              checkaccuracy.setText(sb11);
            checkaccuracy.setVisibility(View.VISIBLE);


            redcompsum=0;
             redcompcount=0;
             bluecompsum=0;
            bluecompcount=0;
            meanred=0d;
            meanblue=0d;
             stddevred=0d;
            stddevblue=0d;
       vred.clear();
            vblue.clear();
            processing.set(false);
            flagforupdate=1;
            flagforavg=1;
            makeThemZero(value1);
            makeThemZero(value1count);
            flagsame = 0;
                 sb11=new StringBuilder("Over ");
        }


    }

    public void saveHR(View v)
    {

        radiogroupforStatus=(RadioGroup)findViewById(R.id.radiogroupforStatus);
        int radioButtonID = radiogroupforStatus.getCheckedRadioButtonId();
        View radioButton = radiogroupforStatus.findViewById(radioButtonID);
        int idx = radiogroupforStatus.indexOfChild(radioButton);

        RadioButton r = (RadioButton) radiogroupforStatus.getChildAt(idx);

        String selectedtext = r.getText().toString();
        if(radioButtonID==-1)
        {
            Toast.makeText(HeartRateChecker.this, "Select status", Toast.LENGTH_LONG).show();
        }
        else
        {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                idxy=extras.getLong(MESSAGE_KEY);
                //The key argument here must match that used in the other activity
            }

            if(idxy==-1) {
                Toast.makeText(HeartRateChecker.this, "You need to Login to save record", Toast.LENGTH_LONG).show();
            }
            else
            {
                DbOpforHR DB = new DbOpforHR(ctx);
                long idHR = DB.putInfo(DB, idxy, finalHRvalue, selectedtext);


                if (idHR != -1) {
                    checkAlert(ctx);
                   if(flagforalert != 1){

                       Toast.makeText(HeartRateChecker.this, "Record Saved", Toast.LENGTH_LONG).show();

                       Intent intent = new Intent(ctx, HomePage.class);
                       intent.putExtra(MESSAGE_KEY, idxy);
                       startActivity(intent);
                   }

                }
            }
        }
    }
static int similarHrate(int avgosum)
{
    for(int i2=0;i2<10;i2++)
    {
        if(value1[i2]<60)
        {
value1count[0]++;
    //        value1count[8]++;
        }
        else if(value1[i2]<70)
        {
            value1count[1]++;
   //         value1count[8]++;
        }
        else if(value1[i2]<80)
        {
            value1count[2]++;
    //        value1count[8]++;
        } else if(value1[i2]<90)
        {
            value1count[3]++;
 //           value1count[9]++;
        } else if(value1[i2]<100)
        {
            value1count[4]++;
 //           value1count[9]++;
        } else if(value1[i2]<110)
        {
            value1count[5]++;
   //         value1count[10]++;
        } else if(value1[i2]<120)
        {
            value1count[6]++;
   //         value1count[10]++;
        }
        else
        {
            value1count[7]++;
    //        value1count[10]++;
        }
    }
   /* if(flagforprogressimage==0) {
        progressimage.setImageResource(R.drawable.progressrep60p);
    }*/
    int maxindex=0;
    for(int i3=1;i3<8;i3++)
    {
        if(value1count[maxindex]<value1count[i3])
        {
            maxindex=i3;
        }
        if(value1count[maxindex]==value1count[i3])
        {
            flagsame=1;
        }
    }
    /*if(flagforprogressimage==0) {
        progressimage.setImageResource(R.drawable.progressrep70p);
    }*/
    if(flagsame==1) {
        maxindex = finalMaxIndex(maxindex,avgosum);
    }
    int sumfinal=0;
    int sumfinalcount=0;
    for(int i4=0;i4<10;i4++)
    {
if(value1[i4]<= (maxindex*10+59) && value1[i4]>=(maxindex*10+50))
{
    sumfinal+=value1[i4];
    sumfinalcount++;
}

    }/*
    if(flagforprogressimage==0) {
        progressimage.setImageResource(R.drawable.progressrep90p);
    }*/

    return ((int)sumfinal/sumfinalcount);




}
static  int finalMaxIndex(int maxindex,int mean)
{
    int sumfinal1=0,sumfinalcount1=0;
    float avg1=1;
    float diff=1000;
    int finaldiffindex=maxindex;
    for(int i5=0;i5<8;i5++) {
     if(value1count[maxindex]==value1count[i5]) {
         for (int i4 = 0; i4 < 10; i4++) {
             if (value1[i4] <= (maxindex * 10 + 59) && value1[i4] >= (maxindex * 10 + 50)) {
                 sumfinal1 += value1[i4];
                 sumfinalcount1++;
             }
         }
         avg1 = sumfinal1 / sumfinalcount1;
         if(flagforprogressimage==0) {
             progressimage.setImageResource(R.drawable.progressrep80p);
         }
    if(diff > Math.abs(avg1-mean))
    {
diff=Math.abs(avg1-mean);
    finaldiffindex=i5;
    }
     }

    }
return finaldiffindex;
    }
    public void calculateCB(View v)
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idxy=extras.getLong(MESSAGE_KEY);
            //The key argument here must match that used in the other activity
        }
        Intent intent = new Intent(this, CalorieChecker.class);
        intent.putExtra(MESSAGE_KEY2,finalHRvalue);
        intent.putExtra(MESSAGE_KEY,idxy);
        startActivity(intent);
    }
    public static void checkAlert(Context ctx)
    {
        double countHRvalue=0;
        double countHRvaluetotal=0;

        DbOpforHR dop=new DbOpforHR(ctx);
        Cursor cr=dop.getInfo(dop);
        cr.moveToFirst();
        do {

           if(cr.getLong(1)==idxy)
           {
               countHRvaluetotal++;
               if(cr.getInt(2)>60)
               {
                   countHRvalue++;
               }
           }

        }while(cr.moveToNext());

    if((countHRvalue/countHRvaluetotal)>=0.5)
    {

flagforalert=1;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
        builder1.setMessage("Your heart rate is frequently above 60 bpm.Take actions accordingly!!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
        countHRvalue=0;
        countHRvaluetotal=0;
    }

   static void calculateStandardDev()
   {
       double diffredvalue=0;
       double diffbluevalue=0;
        for(int icounter=0;icounter<vred.size();icounter++)
            {
                diffredvalue=((double)vred.get(icounter)-meanred)*((double)vred.get(icounter)-meanred)+diffredvalue;
                diffbluevalue=((double)vblue.get(icounter)-meanblue)*((double)vblue.get(icounter)-meanblue)+diffbluevalue;

            }

      stddevred= Math.sqrt(diffredvalue/redcompcount);
   stddevblue=Math.sqrt(diffbluevalue/bluecompcount);
       diffredvalue=0;
       diffbluevalue=0;
   }

}