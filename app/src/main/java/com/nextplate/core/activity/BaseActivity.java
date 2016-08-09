package com.nextplate.core.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.nextplate.R;
import com.nextplate.core.preference.Prefrences;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;

import butterknife.ButterKnife;
/**
 * Created by gspl on 9/25/2015.
 */
public abstract class BaseActivity extends AppCompatActivity
{

    public ActionBar actionBar;
    private ProgressDialog progressDialog;
    private String[] action;
    private BroadcastListener broadcastListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(this.getActivityContentView());
        ButterKnife.bind(this);
        initActionBar();
        this.onCreateCustom();
        initProgressDialog();
    }

    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId > 0)
        {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void listenBroadcast(final String[] action, final BroadcastListener broadcastListener)
    {
        this.action = action;
        this.broadcastListener = broadcastListener;
        IntentFilter intentFilter = null;
        if(action.length > 0)
        {
            intentFilter = new IntentFilter();
            for(String actionStr : action)
            {
                intentFilter.addAction(actionStr);
            }
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    public void unlistenBroadcast()
    {
        try
        {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
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
         //   if(intent.getAction().equalsIgnoreCase(action))
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
        return Prefrences.readClass(this, classType);
    }

    public void deleteClass(Class classType)
    {
        Prefrences.deleteClass(this, classType);
    }

    public void writeClass(Object object)
    {
        Prefrences.writeClass(this, object);
    }


    private void initActionBar()
    {
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        //actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    private void initProgressDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    public Firebase getFireBase()
    {
        return new Firebase(getString(R.string.baseUrl));
    }

    public FragmentTransaction getFMTransectionManager()
    {
        return setFragmentTransitionAnimation(getSupportFragmentManager().beginTransaction());
    }

    public FragmentTransaction setFragmentTransitionAnimation(FragmentTransaction ft)
    {
        ft.setCustomAnimations(R.anim.frag_right_in, R.anim.frag_left_out, R.anim.frag_left_in, R.anim.frag_right_out);
        return ft;
    }

    public void setEmptyValidator(MaterialEditText emptyValidator)
    {
        emptyValidator.addValidator(new METValidator(getString(R.string.empty_error_msg))
        {
            @Override
            public boolean isValid(
                    @NonNull
                    CharSequence text, boolean isEmpty)
            {
                return !isEmpty;
            }
        });
    }

    abstract public void onCreateCustom();

    abstract public int getActivityContentView();

    public void setTitle(String title)
    {
        actionBar.setTitle(title);
    }

    public String getTitleCustom()
    {
        return actionBar.getTitle().toString();
    }

    public void makeActionBarVisible(boolean makeVisible)
    {
        if(makeVisible)
        {
            actionBar.show();
        }
        else
        {
            actionBar.hide();
        }
    }

    public void showProgress()
    {
        if(!progressDialog.isShowing())
        {
            progressDialog.show();
        }
    }

    public void showProgress(String message)
    {
        if(!progressDialog.isShowing())
        {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    public void hideProgress()
    {
        if(progressDialog.isShowing())
        {
            progressDialog.hide();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                if(checkPopBack())
                {
                    this.finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPopBack()
    {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if(fm.getBackStackEntryCount() > 0)
        {
            fm.popBackStack();
            return false;
        }
        else
        {
            super.onBackPressed();
            return true;
        }
    }

    public void showDialog(String message, String positiveText, DialogInterface.OnClickListener posClick, String negativeText,
                           DialogInterface.OnClickListener negClick, boolean isCancelable)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(isCancelable);
        builder1.setPositiveButton(positiveText, posClick);
        builder1.setNegativeButton(negativeText, negClick);
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void showDialog(String message)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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

    public void showDialog(String message, DialogInterface.OnClickListener onClickListener)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok", onClickListener);
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
