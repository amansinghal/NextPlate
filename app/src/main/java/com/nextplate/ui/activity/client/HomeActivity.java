package com.nextplate.ui.activity.client;

import com.nextplate.R;
import com.nextplate.core.activity.BaseActivity;
/**
 * Created by aman on 9/8/16.
 */
public class HomeActivity extends BaseActivity
{
    @Override
    public void onCreateCustom()
    {
        getFMTransectionManager().replace(R.id.activity_home_client_container, new com.nextplate.ui.fragment.client.HomeFragment())
                                 .commit();
    }

    @Override
    public int getActivityContentView()
    {
        return R.layout.activity_home_client;
    }
}
