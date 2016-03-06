package com.nextplate.core.fragment;

/**
 * Created by AmaN on 10/4/2015.
 */import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment
{
    OnDateSetListener ondateSet;
    String title;
    private int year, month, day,minDate;

    public void setCallBack(OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
        minDate = args.getInt("minDate",0);
        title = args.getString("title","");
    }

    //5.676e+11

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerFragment = new DatePickerDialog(getActivity(), ondateSet, year, month, day);
        if (!title.isEmpty())
        datePickerFragment.setTitle(title);
        if (minDate != 0)
        datePickerFragment.getDatePicker().setMaxDate(new GregorianCalendar().getTimeInMillis() - (int)minDate);
        return datePickerFragment;
    }
}
