package com.nextplate.core.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment
{
    private OnFilterDataChanged sFilterDataChanged;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(this.getFragmentLayout(), container, false);
        ButterKnife.bind(this, v);
        initProgressDialog();
        return v;
    }

    @Override
    public void onViewCreated(View view,
                              @Nullable
                              Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        this.onFragmentReady();
    }

    private void initProgressDialog()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(null);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
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

    public abstract void onFragmentReady();

    public abstract int getFragmentLayout();

    public void setOnFilterDataChanged(OnFilterDataChanged onFilterDataChanged)
    {
        sFilterDataChanged = onFilterDataChanged;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(sFilterDataChanged != null)
        {
            sFilterDataChanged.onFilterDataChanged(requestCode, resultCode, data);
        }
    }

    public interface OnFilterDataChanged
    {
        void onFilterDataChanged(int requestCode, int resultCode, Intent data);
    }
}
