package com.example.abc.heartratetry;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by sadhana on 25-02-2017.
 */
public class FirstFragment extends Fragment {
    View myView;
    Button button=null;
    static long myvalue=-1;
    public final static String MESSAGE_KEY="firsttrialkey";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         myvalue=getArguments().getLong(MESSAGE_KEY);
        myView=inflater.inflate(R.layout.first_layout,container,false);




        return myView;


    }
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        button=(Button)getView().findViewById(R.id.buttonCHR1);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), HeartRateChecker.class);
                        intent.putExtra(MESSAGE_KEY,myvalue);

                        HeartRateChecker.flagforavg=0;
                        HeartRateChecker.flagforupdate=0;
                        HeartRateChecker.flagforprogressimage=0;

                        getActivity().startActivity(intent);
                    }
                }



        );
    }
}
