package com.nextplate.ui.activity;

import com.nextplate.R;
import com.nextplate.core.activity.BaseActivity;
/**
 * Created by aman on 8/8/16.
 */
public class MyLocationActivity extends BaseActivity
{
    @Override
    public void onCreateCustom()
    {
        setTitle("MyPlate");
    }

    @Override
    public int getActivityContentView()
    {
        return R.layout.activity_my_location;
    }
}
