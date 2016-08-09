package com.nextplate.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.nextplate.BuildConfig;
import com.nextplate.R;
import com.nextplate.core.activity.BaseActivity;
import com.nextplate.core.preference.Prefrences;
import com.nextplate.ui.activity.client.HomeActivity;
import com.nextplate.ui.fragment.admin.HomeFragment;
/**
 * Created by AmaN on 3/8/2016.
 */
public class MainActivity extends BaseActivity
{

    @Override
    public void onCreateCustom()
    {
        if(BuildConfig.FLAVOR.equals("clientFla"))
        {
            if(TextUtils.isEmpty(Prefrences.getReadInstance(this).getString(Prefrences.Key.KEY_CITY, "")))
            {
                startActivity(new Intent(this, MyLocationActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(this, HomeActivity.class));
            }
        }
        else
        {
            getFMTransectionManager().replace(R.id.main_activity_container, new HomeFragment()).commit();
        }
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
