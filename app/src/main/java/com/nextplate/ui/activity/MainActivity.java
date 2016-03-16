package com.nextplate.ui.activity;

import android.view.Menu;
import android.view.MenuItem;

import com.nextplate.R;
import com.nextplate.core.activity.BaseActivity;
import com.nextplate.ui.fragment.client.HomeFragment;
/**
 * Created by AmaN on 3/8/2016.
 */
public class MainActivity extends BaseActivity
{

    @Override
    public void onCreateCustom()
    {
        getFMTransectionManager().replace(R.id.main_activity_container, new HomeFragment()).commit();
        //getFMTransectionManager().replace(R.id.main_activity_container, new HomeFragment()).commit();
    }

    @Override
    public int getActivityContentView()
    {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
