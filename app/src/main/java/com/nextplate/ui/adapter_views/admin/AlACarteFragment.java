package com.nextplate.ui.adapter_views.admin;

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
import com.nextplate.models.AlACarte;
import com.nextplate.models.Meals;
import com.nextplate.ui.fragment.admin.AddOrEditMealFragment;

import java.util.List;

import butterknife.Bind;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;
/**
 * Created by AmaN on 3/17/2016.
 */
public class AlACarteFragment extends BaseFragment implements ViewEventListener
{

    private RecyclerMultiAdapter recyclerMultiAdapter;
    private Firebase firebase;
    public final String key = "al_a_carte";
    private List<AlACarte> alacarteList;

    public Fragment getInstance()
    {
        return new AlACarteFragment();
    }

    @Bind(R.id.frag_al_a_carte_rv_listing)
    RecyclerView rvListing;

    @Override
    public void onFragmentReady()
    {
        rvListing.setLayoutManager(new LinearLayoutManager(activity));
        recyclerMultiAdapter = SmartAdapter.items(alacarteList)
                .map(AlACarte.class, AlaCarteItemView.class)
                .listener(this)
                .recyclerAdapter();
        rvListing.setAdapter(recyclerMultiAdapter);
        setTitle("Al-a-Carte");
        setHasOptionsMenu(true);
        showProgress();
        firebase = getFireBase().child(key);
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
                    recyclerMultiAdapter.notifyDataSetChanged();
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
        return R.layout.frag_al_a_carte_admin;
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

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewEvent(int i, final Object o, final int i1, View view)
    {
        if(view.getId() == R.id.meal_item_view_ivb_edit)
        {
            new BottomSheet.Builder(getActivity(), R.style.BottomSheet_Dialog).title("Options")
                    .sheet(R.menu.menu_pop_up_edit_content)
                    .listener(new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            switch(which)
                            {
                                case R.id.action_add_edit_content_edit:
                                    getFMTransection().replace(R.id.main_activity_container,
                                                               AddOrEditMealFragment.getInstance(key + "/" + i1, ((AlACarte) o).getName()),
                                                               AddOrEditMealFragment.TAG).addToBackStack(null).commit();
                                    break;

                                case R.id.action_add_edit_content_delete:
                                    deleteMeal(i1);
                                    break;
                            }
                        }
                    })
                    .show();
            return;
        }
        getFMTransection().replace(R.id.main_activity_container, AddOrEditMealFragment.getInstance(key + "/" + i1, ((Meals) o).getName()),
                                   AddOrEditMealFragment.TAG).addToBackStack(null).commit();
    }

    public void deleteMeal(int position)
    {
        alacarteList.remove(position);
        showProgress();
        firebase.setValue(alacarteList, new Firebase.CompletionListener()
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
