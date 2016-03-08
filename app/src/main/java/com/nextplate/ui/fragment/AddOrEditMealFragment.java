package com.nextplate.ui.fragment;

import android.support.v4.app.Fragment;

import com.nextplate.R;
import com.nextplate.core.fragment.BaseFragment;
/**
 * Created by AmaN on 3/8/2016.
 */
public class AddOrEditMealFragment extends BaseFragment
{
    public static Fragment getInstance()
    {
        return new AddOrEditMealFragment();
    }

    @Override
    public void onFragmentReady()
    {

    }

    @Override
    public int getFragmentLayout()
    {
        return R.layout.frag_addoredit_meal;
    }
}
