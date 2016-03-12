package com.nextplate.ui.fragment;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cocosw.bottomsheet.BottomSheet;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nextplate.R;
import com.nextplate.core.fragment.BaseFragment;
import com.nextplate.models.Meals;
import com.nextplate.ui.adapter_views.MealsItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;
/**
 * Created by AmaN on 3/8/2016.
 */
public class MealsFragment extends BaseFragment implements ViewEventListener
{
    public static final String TAG = "MealsFragment";
    public final String key = "meals";
    @Bind(R.id.frag_meals_rv_listing)
    RecyclerView rvListing;
    List<Meals> mealsList = new ArrayList<>();
    private RecyclerMultiAdapter recyclerMultiAdapter;
    Firebase firebase;

    public static Fragment getInstance()
    {
        Fragment fragment = new MealsFragment();
        return fragment;
    }

    @Override
    public void onFragmentReady()
    {
        rvListing.setLayoutManager(new LinearLayoutManager(activity));
        recyclerMultiAdapter = SmartAdapter.items(mealsList).map(Meals.class, MealsItemView.class).listener(this).recyclerAdapter();
        rvListing.setAdapter(recyclerMultiAdapter);
        setTitle("Meals");
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
        return R.layout.frag_meals;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        menu.findItem(R.id.action_add_meal).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_add_meal)
        {
            getFMTransection().replace(R.id.main_activity_container,
                                       AddOrEditMealFragment.getInstance(key + "/" + mealsList.size(), "New meal"),
                                       AddOrEditMealFragment.TAG).addToBackStack(null).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewEvent(int i, final Object o, final int i1, final View view)
    {
        if(view.getId() == R.id.meal_item_view_ivb_edit)
        {
            new BottomSheet.Builder(getActivity(), R.style.BottomSheet_Dialog).title("Options")
                    // <-- important part
                    .sheet(R.menu.menu_pop_up_edit_content).listener(new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    switch(which)
                    {
                        case R.id.action_add_edit_content_edit:
                            getFMTransection().replace(R.id.main_activity_container,
                                                       AddOrEditMealFragment.getInstance(key + "/" + i1, ((Meals) o).getName()),
                                                       AddOrEditMealFragment.TAG).addToBackStack(null).commit();
                            break;

                        case R.id.action_add_edit_content_delete:
                            deleteMeal(i1);
                            break;
                    }
                }
            }).show();
            return;
        }
        getFMTransection().replace(R.id.main_activity_container, AddOrEditMealFragment.getInstance(key + "/" + i1, ((Meals) o).getName()),
                                   AddOrEditMealFragment.TAG).addToBackStack(null).commit();
    }

    public void deleteMeal(int position)
    {
        mealsList.remove(position);
        showProgress();
        firebase.setValue(mealsList, new Firebase.CompletionListener()
        {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase)
            {
                if(firebaseError == null)
                {
                    Toast("Updated");
                }
            }
        });
    }
}
