package com.nextplate.core.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.nextplate.core.activity.BaseActivity;
import com.nextplate.core.fragment.DatePickerFragment;
import com.nextplate.core.fragment.TimePickerFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by AmaN on 10/4/2015.
 */
public class Utility
{

    /**
     * Hides a spinner's drop down.
     */
    public static void hideSpinnerDropDown(Spinner spinner)
    {
        try
        {
            Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
            method.setAccessible(true);
            method.invoke(spinner);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void showDialog(Context context, String message, String positiveText, DialogInterface.OnClickListener posClick,
                                  String negativeText, DialogInterface.OnClickListener negClick)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(positiveText, posClick);
        builder1.setNegativeButton(negativeText, negClick);
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static void showDialog(Context context, String message)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

   /* public static void showDialogWithListSelection(Context context, String title, CharSequence[] array, final CustomDialogSingleSelectionInterface listener) {
        final Dialog dialog = new Dialog(context);
        //dialog.setSingleChoiceItems(array, 0, listener);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final List<String> mEntriesString = new ArrayList<>();
        for(CharSequence ch: array){
            mEntriesString.add((String) ch);
        }
        View view = View.inflate(context, R.layout.custom_dialog_single_choice, null);
        dialog.setContentView(view);
        MaterialEditText materialEditText = (MaterialEditText)view.findViewById(R.id.custom_dialog_single_choice_search);
        final ListView listView= (ListView)view.findViewById(R.id.custom_dialog_single_choice_recycler);
        listView.setTextFilterEnabled(true);
        TextView textView = (TextView)view.findViewById(R.id.custom_dialog_single_choice_title);
        textView.setText(title);
        final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,mEntriesString);
        listView.setAdapter(stringArrayAdapter);
        materialEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                //listView.setFilterText(s.toString());
                stringArrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                listener.onItemClickListenerCustom(dialog,((TextView)view).getText().toString(),mEntriesString.indexOf(((TextView)view).getText().toString()));
            }
        });

        dialog.show();
    }
*/

    public static MaterialEditText showDialogForEmail(Context context, String hint, String title,
                                                      DialogInterface.OnClickListener onClickListener, MaterialEditText materialEditText)
    {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                     LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(10, 10, 10, 10);
        materialEditText.setLayoutParams(lp);
        dialog.setView(materialEditText);
        dialog.setPositiveButton("Send", onClickListener);
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
        return materialEditText;
    }

    public static void timePicker(Context context, TimePickerDialog.OnTimeSetListener ondate, String title)
    {
        TimePickerFragment timePickerFragment = new TimePickerFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hour", calender.get(Calendar.HOUR));
        args.putInt("minute", calender.get(Calendar.MINUTE));
        args.putString("title", title);
        timePickerFragment.setArguments(args);

        timePickerFragment.setCallBack(ondate);
        timePickerFragment.show(((BaseActivity) context).getSupportFragmentManager(), "Time Picker");
    }

    public static void datePicker(Context context, DatePickerDialog.OnDateSetListener ondate, int minDate)
    {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR) - 18);
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        args.putInt("minDate", minDate);
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(((BaseActivity) context).getSupportFragmentManager(), "Date Picker");
    }

    public static String getFormatedDateNumeric(int number)
    {
        return number < 10 ? "0" + number : "" + number;
    }

    public static void datePicker(Context context, DatePickerDialog.OnDateSetListener ondate)
    {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(((BaseActivity) context).getSupportFragmentManager(), "Date Picker");
    }

    public static void datePicker(Context context, DatePickerDialog.OnDateSetListener ondate, String title)
    {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        args.putString("title", title);
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(((BaseActivity) context).getSupportFragmentManager(), title);
    }

    public static Date getStringToDate(String dd_mm_yyyy)
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            Date date = format.parse(dd_mm_yyyy);
            System.out.println(date);
            return date;
        }
        catch(ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isWithinRange(Date testDate, Date startDate, Date endDate)
    {
        return testDate.getTime() >= startDate.getTime() && testDate.getTime() <= endDate.getTime();
    }

    /**
     * returning image / video
     *
     * @param IMAGE_DIRECTORY
     */
    public static File getOutputMediaFile(String IMAGE_DIRECTORY)
    {

        // External sdcard location
        File mediaStorageDir = new File(IMAGE_DIRECTORY);

        // Create the storage directory if it does not exist
        if(!mediaStorageDir.exists())
        {
            if(!mediaStorageDir.mkdirs())
            {
                Log.d(IMAGE_DIRECTORY, "Oops! Failed create " + IMAGE_DIRECTORY + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    /**
     * Checking device has camera hardware or not
     */
    private boolean isDeviceSupportCamera(Context context)
    {
        if(context.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            // this device has a camera
            return true;
        }
        else
        {
            // no camera on this device
            return false;
        }
    }
}
