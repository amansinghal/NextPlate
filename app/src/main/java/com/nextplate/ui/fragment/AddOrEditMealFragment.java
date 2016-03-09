package com.nextplate.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nextplate.R;
import com.nextplate.core.fragment.BaseFragment;
import com.nextplate.core.rest.FirebaseConstants;
import com.nextplate.custom_views.WrapContentLinearLayoutManager;
import com.nextplate.models.ContentListing;
import com.nextplate.models.Meals;
import com.nextplate.ui.adapter_views.ContentListingItemView;

import java.util.ArrayList;
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
    private static final String KEY_TITLE = "key_title";
    private String getKeyPath;
    private Meals meals;
    @Bind(R.id.frag_addoredit_meal_name)
    TextView tvMealName;
    @Bind(R.id.frag_addoredit_meal_description)
    TextView tvMealDescription;
    @Bind(R.id.frag_addoredit_meal_price)
    TextView tvMealPrice;
    @Bind(R.id.frag_addoredit_meal_rv_content_listing)
    RecyclerView rvListing;
    RecyclerMultiAdapter recyclerMultiAdapter;
    List<ContentListing> contentsList = new ArrayList<>();

    public static Fragment getInstance(String path,String title)
    {
        AddOrEditMealFragment addOrEditMealFragment = new AddOrEditMealFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AddOrEditMealFragment.KEY_PATH, path);
        bundle.putString(AddOrEditMealFragment.KEY_TITLE, title);
        addOrEditMealFragment.setArguments(bundle);
        return addOrEditMealFragment;
    }

    @Override
    public void onFragmentReady()
    {
        rvListing.setLayoutManager(new WrapContentLinearLayoutManager(activity));
        recyclerMultiAdapter = SmartAdapter.items(contentsList)
                .map(ContentListing.class, ContentListingItemView.class)
                .listener(this)
                .recyclerAdapter();
        rvListing.setAdapter(recyclerMultiAdapter);
        if(getArguments() != null)
        {
            getKeyPath = getArguments().getString(KEY_PATH, "");
            setTitle(getArguments().getString(KEY_TITLE,"Meal"));
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
        tvMealPrice.setText("" + meals.getRupees());
        tvMealDescription.setText(meals.getDescription());
        contentsList.clear();

        ContentListing contentListing = new ContentListing();
        contentListing.setContents(meals.getContents());
        contentListing.setHeading("Daily meals");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setContents(meals.getSun_option());
        contentListing.setHeading("Sunday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setContents(meals.getMon_option());
        contentListing.setHeading("Monday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setContents(meals.getTue_option());
        contentListing.setHeading("Tuesday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setContents(meals.getWed_option());
        contentListing.setHeading("Wednesday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setContents(meals.getThr_option());
        contentListing.setHeading("Thursday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setContents(meals.getFri_option());
        contentListing.setHeading("Friday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setContents(meals.getSat_option());
        contentListing.setHeading("Saturday options");
        contentsList.add(contentListing);

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
        if(view.getId() == R.id.content_listing_meal_btn_add_new)
        {
            redirectAccordingToPos(i1);
        }
    }

    private void redirectAccordingToPos(int pos)
    {
        String path = getKeyPath;
        switch(pos)
        {
            case 0:
                path += "/" + FirebaseConstants.URL_CONTENTS;
                break;
            case 1:
                path += "/" + FirebaseConstants.URL_SUN_OP;
                break;
            case 2:
                path += "/" + FirebaseConstants.URL_MON_OP;
                break;
            case 3:
                path += "/" + FirebaseConstants.URL_TUE_OP;
                break;
            case 4:
                path += "/" + FirebaseConstants.URL_WED_OP;
                break;
            case 5:
                path += "/" + FirebaseConstants.URL_THR_OP;
                break;
            case 6:
                path += "/" + FirebaseConstants.URL_FRI_OP;
                break;
            case 7:
                path += "/" + FirebaseConstants.URL_SAT_OP;
                break;
        }
        System.out.println(path);
        getFMTransection().replace(R.id.main_activity_container, ContentDetailFragment.getInstance(path), ContentDetailFragment.TAG)
                .addToBackStack(null)
                .commit();
    }
}
