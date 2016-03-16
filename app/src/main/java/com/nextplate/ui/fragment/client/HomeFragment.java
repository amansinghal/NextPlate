package com.nextplate.ui.fragment.client;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nextplate.R;
import com.nextplate.core.fragment.BaseFragment;
import com.nextplate.models.Meals;
import com.nextplate.ui.adapter_views.admin.MealsItemView;
import com.nextplate.ui.adapter_views.client.MealsItemViewClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;
/**
 * Created by AmaN on 3/13/2016.
 */
public class HomeFragment extends BaseFragment implements ViewEventListener
{
    public static final String TAG = "HomeFragment";
    public final String key = "meals";
    @Bind(R.id.frag_home_client_rv_listing)
    RecyclerView rvListing;
    private RecyclerMultiAdapter recyclerMultiAdapter;
    List<Meals> mealsList = new ArrayList<>();
    private Firebase firebase;

    @Override
    public void onFragmentReady()
    {
        rvListing.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerMultiAdapter = SmartAdapter.items(mealsList).map(Meals.class, MealsItemViewClient.class).listener(this).recyclerAdapter();
        rvListing.setAdapter(recyclerMultiAdapter);
        setTitle("NextPlate");
        setHasOptionsMenu(true);
        showProgress();
        firebase = getFireBase().child(key);
        firebase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                hideProgress();
                mealsList.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Meals meals = postSnapshot.getValue(Meals.class);
                    mealsList.add(meals);
                    recyclerMultiAdapter.notifyDataSetChanged();
                    System.out.println(meals.getRupees() + " - " + meals.getName());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                hideProgress();
            }
        });
    }

    @Override
    public int getFragmentLayout()
    {
        return R.layout.frag_home_client;
    }

    @Override
    public void onViewEvent(int i, Object o, int i1, View view)
    {

    }
}
