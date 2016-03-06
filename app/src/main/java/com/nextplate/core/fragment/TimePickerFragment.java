package com.nextplate.core.fragment;

/**
 * Created by AmaN on 10/4/2015.
 */

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TimePickerFragment extends DialogFragment
{
    TimePickerDialog.OnTimeSetListener ondateSet;
    String title;
    private int hour, minute, day,minDate;

    public void setCallBack(TimePickerDialog.OnTimeSetListener ondate) {
        ondateSet = ondate;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hour = args.getInt("hour");
        minute = args.getInt("minute");
        title = args.getString("title","");
    }

    //5.676e+11

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TimePickerDialog datePickerFragment = new TimePickerDialog(getActivity(), ondateSet, hour, minute,false);
        if (!title.isEmpty())
        datePickerFragment.setTitle(title);
        return datePickerFragment;
    }
}
