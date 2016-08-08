package com.nextplate.core.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.nextplate.R;

import butterknife.ButterKnife;

/**
 * Created by gspl on 9/25/2015.
 */
public abstract class BaseActivity extends AppCompatActivity
{

    private ActionBar actionBar;
    private ProgressDialog progressDialog;

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

    public Firebase getFireBase()
    {
        return new Firebase(getString(R.string.baseUrl));
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

    private void initActionBar()
    {
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);
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

    public FragmentTransaction getFMTransectionManager()
    {
        return getSupportFragmentManager().beginTransaction();
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
}
