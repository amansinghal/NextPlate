package com.nextplate.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nextplate.R;
import com.nextplate.core.fragment.BaseFragment;
import com.nextplate.models.Contents;
import com.nextplate.models.Meals;
import com.nextplate.ui.adapter_views.ContentItemView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;
/**
 * Created by AmaN on 3/8/2016.
 */
public class AddOrEditMealFragment extends BaseFragment implements ViewEventListener
{
    private static final String KEY_PATH = "key_path";
    public static final String TAG = "AddOrEditMealFragment";
    private String getKeyPath;
    private Meals meals;
    @Bind(R.id.frag_addoredit_meal_name)
    TextView tvMealName;
    @Bind(R.id.frag_addoredit_meal_description)
    TextView tvMealDescription;
    @Bind(R.id.frag_addoredit_meal_price)
    TextView tvMealPrice;
    @Bind(R.id.frag_addoredit_meal_daily_meal_rv_meal_listing)
    RecyclerView rvListing;
    RecyclerMultiAdapter recyclerMultiAdapter;
    List<Contents> contentsList = new ArrayList<>();

    public static Fragment getInstance(String path)
    {
        AddOrEditMealFragment addOrEditMealFragment = new AddOrEditMealFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AddOrEditMealFragment.KEY_PATH, path);
        addOrEditMealFragment.setArguments(bundle);
        return addOrEditMealFragment;
    }

    @Override
    public void onFragmentReady()
    {
        rvListing.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerMultiAdapter = SmartAdapter.items(contentsList).map(Contents.class, ContentItemView.class).listener(this).recyclerAdapter();
        rvListing.setAdapter(recyclerMultiAdapter);
        if(getArguments() != null)
        {
            getKeyPath = getArguments().getString(KEY_PATH, "");
        }
        final Firebase firebase = getFireBase().child(getKeyPath);
        showProgress();
        firebase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                hideProgress();
                meals = dataSnapshot.getValue(Meals.class);
                if(meals != null)
                {
                    fillData();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                hideProgress();
                System.out.println(firebaseError);
            }
        });
    }

    private void fillData()
    {
        tvMealName.setText(meals.getName());
        tvMealPrice.setText(meals.getRupees() + " Rs.");
        tvMealDescription.setText(meals.getDescription());
        contentsList.clear();
        contentsList.addAll(Arrays.asList(meals.getContents()));
        recyclerMultiAdapter.notifyDataSetChanged();
    }

    @Override
    public int getFragmentLayout()
    {
        return R.layout.frag_addoredit_meal;
    }

    @Override
    public void onViewEvent(int i, Object o, int i1, View view)
    {

    }
}
