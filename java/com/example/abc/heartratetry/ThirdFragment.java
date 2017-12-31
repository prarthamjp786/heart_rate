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
public class ThirdFragment extends Fragment {
    View myView;
    static long myvalue=-1;
    ImageButton ib1=null,ib2=null,ib3=null;
    public final static String MESSAGE_KEY="firsttrialkey";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myvalue=getArguments().getLong(MESSAGE_KEY);
        myView=inflater.inflate(R.layout.third_layout,container,false);
        return myView;
    }
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {
        ib1=(ImageButton)getView().findViewById(R.id.myrecordoption);
        ib2=(ImageButton)getView().findViewById(R.id.myfilesoption);
        ib3=(ImageButton)getView().findViewById(R.id.doctornearbyoption);
        ib1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Records.class);
                        intent.putExtra(MESSAGE_KEY,myvalue);


                        getActivity().startActivity(intent);
                    }
                }



        );
        ib2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MyFiles.class);
                        intent.putExtra(MESSAGE_KEY,myvalue);



                        getActivity().startActivity(intent);
                    }
                }



        );
        ib3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), DoctorNearbyChecker.class);
                        intent.putExtra(MESSAGE_KEY,myvalue);


                        getActivity().startActivity(intent);
                    }
                }



        );
    }
}
