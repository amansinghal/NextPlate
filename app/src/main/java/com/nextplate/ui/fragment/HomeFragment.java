package com.nextplate.ui.fragment;

import com.nextplate.R;
import com.nextplate.core.fragment.BaseFragment;

import butterknife.OnClick;
/**
 * Created by AmaN on 3/8/2016.
 */
public class HomeFragment extends BaseFragment
{
    @Override
    public void onFragmentReady()
    {
        setTitle("Home");
    }

    @Override
    public int getFragmentLayout()
    {
        return R.layout.frag_home;
    }

    @OnClick(R.id.frag_home_meals)
    protected void onClickMeals()
    {
        getFMTransection().replace(R.id.main_activity_container, MealsFragment.getInstance(), MealsFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.frag_home_orders)
    protected void onClickOrders()
    {

    }
}
