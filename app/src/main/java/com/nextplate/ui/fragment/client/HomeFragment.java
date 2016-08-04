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
import com.nextplate.models.AlACarte;
import com.nextplate.models.Meals;
import com.nextplate.ui.adapter_views.client.AlacarteItemViewClient;
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
    public final String keyMeals = "meals";
    public final String keyAlacarte= "al_a_carte";
    @Bind(R.id.frag_home_client_rv_listing)
    RecyclerView rvListing;
    @Bind(R.id.frag_home_client_rv_listing_al_a_carte)
    RecyclerView rvAlaCarte;
    private RecyclerMultiAdapter recyclerMealsAdapter;
    private RecyclerMultiAdapter recyclerAlaCarteAdapter;
    List<Meals> mealsList = new ArrayList<>();
    List<AlACarte> alacarteList = new ArrayList<>();
    private Firebase firebase;

    @Override
    public void onFragmentReady()
    {
        rvListing.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerMealsAdapter = SmartAdapter.items(mealsList).map(Meals.class, MealsItemViewClient.class).listener(this).recyclerAdapter();
        rvListing.setAdapter(recyclerMealsAdapter);
        rvAlaCarte.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerAlaCarteAdapter= SmartAdapter.items(alacarteList).map(AlACarte.class, AlacarteItemViewClient.class).listener(this).recyclerAdapter();
        rvAlaCarte.setAdapter(recyclerAlaCarteAdapter);

        setTitle("MyPlate");

        setHasOptionsMenu(true);

        showProgress();

        firebase = getFireBase().child(keyMeals);
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
                    recyclerMealsAdapter.notifyDataSetChanged();
                    System.out.println(meals.getRupees() + " - " + meals.getName());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                hideProgress();
            }
        });

        firebase = getFireBase().child(keyAlacarte);
        firebase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                hideProgress();
                alacarteList.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    AlACarte alACarte = postSnapshot.getValue(AlACarte.class);
                    alacarteList.add(alACarte);
                    recyclerAlaCarteAdapter.notifyDataSetChanged();
                    System.out.println(alACarte.getPrice() + " - " + alACarte.getName());
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
