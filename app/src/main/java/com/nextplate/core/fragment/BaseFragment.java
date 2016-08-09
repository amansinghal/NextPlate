package com.nextplate.core.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.nextplate.R;
import com.nextplate.core.activity.BaseActivity;
import com.nextplate.core.preference.Prefrences;
import com.nextplate.core.rest.FirebaseConstants;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment
{
    private ProgressDialog progressDialog;
    public Activity activity;
    private View view;
    private String action;
    private BroadcastListener broadcastListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(this.getFragmentLayout(), container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        this.onFragmentReady();
        return view;
    }

    public void listenBroadcast(final String action, final BroadcastListener broadcastListener)
    {
        this.action = action;
        this.broadcastListener = broadcastListener;
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext())
                .registerReceiver(broadcastReceiver, new IntentFilter(action));
    }

    public void unlistenBroadcast()
    {
        try
        {
            LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(broadcastReceiver);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent.getAction().equalsIgnoreCase(action))
            {
                broadcastListener.onReceive(intent.getExtras(), intent.getAction());
            }
        }
    };

    public interface BroadcastListener
    {
        void onReceive(Bundle bundle, String action);
    }

    public <T> T readClass(Class<T> classType)
    {
        return Prefrences.readClass(activity, classType);
    }

    public void deleteClass(Class classType)
    {
        Prefrences.deleteClass(getActivity(), classType);
    }


    public void writeClass(Object object)
    {
        Prefrences.writeClass(activity, object);
    }


    public View getView()
    {
        return this.view;
    }

    public Firebase getFireBase()
    {
        return new Firebase(getString(R.string.baseUrl));
    }

    private void initProgressDialog()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(null);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
    }

    public FragmentTransaction getFMTransection()
    {
        return ((BaseActivity) activity).getFMTransectionManager();
    }

    public void setTitle(String title)
    {
        if(title == null || getActivity() == null)
        {
            return;
        }
        ((BaseActivity) getActivity()).setTitle(title);
    }

  /*  public void setBackArrowActionBar()
    {
        ((DashBoardActivity) getActivity()).setBackArrowActionBar();
    }

    public void setDrawerActionBar()
    {
        if(getActivity() instanceof DashBoardActivity)
        {
            ((DashBoardActivity) getActivity()).setDrawerActionBar();
        }
    }
*/
    public ActionBar getSupportAction()
    {
        return ((BaseActivity) getActivity()).getSupportActionBar();
    }

    public BaseActivity getBaseContext()
    {
        return ((BaseActivity) getActivity());
    }

    abstract public void onFragmentReady();

    abstract public int getFragmentLayout();

    public void showProgress()
    {
        initProgressDialog();
        progressDialog.show();
    }

    public void hideProgress()
    {
        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

    public void setEmptyValidator(MaterialEditText emptyValidator)
    {
        ((BaseActivity) getActivity()).setEmptyValidator(emptyValidator);
    }

    public boolean isClient()
    {
        return Prefrences.getReadInstance(activity).getBoolean(Prefrences.IS_CLIENT, false);
    }

    public void Toast(String text)
    {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }

    public void noInternet()
    {
        showDialog("No internet access please check your internet settings.");
    }

    public void showDialog(String message, String positiveText, DialogInterface.OnClickListener posClick, String negativeText,
                           DialogInterface.OnClickListener negClick, boolean isCancelable)
    {
        ((BaseActivity) getActivity()).showDialog(message, positiveText, posClick, negativeText, negClick, isCancelable);
    }

    public void showDialog(String message)
    {
        ((BaseActivity) getActivity()).showDialog(message);
    }

    public void showDialog(String message, DialogInterface.OnClickListener onClickListener)
    {
        ((BaseActivity) getActivity()).showDialog(message, onClickListener);
    }

   /* public void showNumberPicker(String title, final View.OnClickListener numberPicker1)
    {
        final Dialog d = new Dialog(getActivity());
        d.setTitle(title);
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(10); // max value 100
        np.setMinValue(1);   // min value 0
        np.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                d.dismiss();
                v.setTag(np.getValue());
                numberPicker1.onClick(v);
            }
        });
        d.show();
    }*/
}
