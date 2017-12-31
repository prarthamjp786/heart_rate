package com.example.abc.heartratetry;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by sadhana on 25-02-2017.
 */
public class SecondFragment extends Fragment {
    View myView;
    ImageButton button1=null;
    ImageButton button2=null;
    static long myvalue=-1;

    public final static String MESSAGE_KEY="firsttrialkey";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myvalue=getArguments().getLong(MESSAGE_KEY);
        myView=inflater.inflate(R.layout.second_layout,container,false);

        ImageButton camBt = (ImageButton)myView.findViewById(R.id.imageButtonHR);
        camBt.setOnClickListener(listener);

        ImageButton camBt2 = (ImageButton)myView.findViewById(R.id.imageButtonC);
        camBt2.setOnClickListener(listener2);


        return myView;
    }


    ImageButton.OnClickListener listener = new ImageButton.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getActivity(), HeartRateHistory.class);
            intent.putExtra(MESSAGE_KEY,myvalue);
            HeartRateHistory.sb=new StringBuilder(" ");
            HeartRateHistory.count=0;
            HeartRateChecker.flagforavg=0;
            HeartRateChecker.flagforupdate=0;
            startActivity(intent);
        }
    };


    ImageButton.OnClickListener listener2 = new ImageButton.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getActivity(), CalorieHistory.class);
            intent.putExtra(MESSAGE_KEY,myvalue);

            HeartRateChecker.flagforavg=0;
            HeartRateChecker.flagforupdate=0;

            startActivity(intent);
        }
    };







    }
