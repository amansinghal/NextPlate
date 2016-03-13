package com.nextplate.core.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.nextplate.core.activity.BaseActivity;
import com.nextplate.core.preference.Prefrences;
import com.nextplate.core.rest.FirebaseConstants;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment
{
    private ProgressDialog progressDialog;
    public Activity activity;
    private View view;

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

    public View getView()
    {
        return this.view;
    }

    public Firebase getFireBase()
    {
        return new Firebase(FirebaseConstants.FIREBASE_URL);
    }

    private void initProgressDialog()
    {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(null);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
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
            progressDialog.hide();
        }
    }

    public void setEmptyValidator(MaterialEditText emptyValidator)
    {
        emptyValidator.addValidator(new METValidator("Must not Empty.")
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

    public boolean isClient()
    {
        return Prefrences.getReadInstance(activity).getBoolean(Prefrences.IS_CLIENT,false);
    }

    public void Toast(String text)
    {
        Toast.makeText(activity,text,Toast.LENGTH_LONG).show();
    }
}
