package com.nextplate.ui.fragment.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nextplate.R;
import com.nextplate.core.fragment.BaseFragment;
import com.nextplate.core.rest.FirebaseConstants;
import com.nextplate.custom_views.WrapContentLinearLayoutManager;
import com.nextplate.models.ContentListing;
import com.nextplate.models.Contents;
import com.nextplate.models.Meals;
import com.nextplate.ui.adapter_views.admin.ContentListingItemView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    private static final String KEY_TITLE = "key_title";
    private String getKeyPath;
    private Meals meals;
    @Bind(R.id.frag_addoredit_meal_name)
    MaterialEditText etMealName;
    @Bind(R.id.frag_addoredit_meal_description)
    MaterialEditText etMealDescription;
    @Bind(R.id.frag_addoredit_meal_price)
    MaterialEditText etMealPrice;
    @Bind(R.id.frag_addoredit_meal_price_package)
    MaterialEditText etMealPricePackage;
    @Bind(R.id.frag_addoredit_meal_days_package)
    MaterialEditText etDaysPackage;
    @Bind(R.id.frag_addoredit_meal_rv_content_listing)
    RecyclerView rvListing;
    RecyclerMultiAdapter recyclerMultiAdapter;
    List<ContentListing> contentsList = new ArrayList<>();
    private Firebase firebase;
    private int currentClickedPosition = -1;

    public static Fragment getInstance(String path, String title)
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
        setEmptyValidator(etMealDescription);
        setEmptyValidator(etMealName);
        setEmptyValidator(etMealPrice);
        setEmptyValidator(etDaysPackage);
        setEmptyValidator(etMealPricePackage);
        setHasOptionsMenu(true);
        //activity.registerForContextMenu(rvListing);
        rvListing.setLayoutManager(new WrapContentLinearLayoutManager(activity));
        rvListing.setNestedScrollingEnabled(false);
        rvListing.setHasFixedSize(true);
        recyclerMultiAdapter = SmartAdapter.items(contentsList)
                .map(ContentListing.class, ContentListingItemView.class)
                .listener(this)
                .recyclerAdapter();
        rvListing.setAdapter(recyclerMultiAdapter);
        if(getArguments() != null)
        {
            getKeyPath = getArguments().getString(KEY_PATH, "");
            setTitle(getArguments().getString(KEY_TITLE, "Meal"));
        }
        firebase = getFireBase().child(getKeyPath);
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
                else
                {
                    fillBankDictionary();
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

    private void fillBankDictionary()
    {
        ContentListing contentListing = new ContentListing();
        contentListing.setHeading("Daily meals");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setHeading("Sunday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setHeading("Monday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setHeading("Tuesday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setHeading("Wednesday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setHeading("Thursday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setHeading("Friday options");
        contentsList.add(contentListing);

        contentListing = new ContentListing();
        contentListing.setHeading("Saturday options");
        contentsList.add(contentListing);

        recyclerMultiAdapter.notifyDataSetChanged();
    }

    private void fillData()
    {
        setTitle(meals.getName());
        etMealName.setText(meals.getName());
        etMealPrice.setText("" + meals.getRupees());
        etMealDescription.setText(meals.getDescription());
        etMealPricePackage.setText(""+meals.getPackagePrice());
        etDaysPackage.setText(""+meals.getPackageDays());
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
    public void onViewEvent(int i, Object o, final int i1, final View view)
    {
        currentClickedPosition = i1;
        if(view.getId() == R.id.content_listing_meal_btn_add_new)
        {
            redirectAccordingToPos(i1, -1);
        }

        if(view.getId() == R.id.content_item_view_root)
        {
            redirectAccordingToPos(i1, (int) view.getTag());
        }

        if(view.getId() == R.id.content_item_view_ivb_edit)
        {
            System.out.println(view.getTag());
           /* PopupMenu popup = new PopupMenu(activity, view);
            popup.inflate(R.menu.menu_pop_up_edit_content);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    if(item.getItemId() == R.id.action_add_edit_content_edit)
                    {

                    }
                    return true;
                }
            });
            setForceShowIcon(popup);
            popup.show();*/

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
                            redirectAccordingToPos(i1, (int) view.getTag());
                            break;

                        case R.id.action_add_edit_content_delete:
                            deleteAccordingToPos(i1, (int) view.getTag());
                            break;
                    }
                }
            }).show();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        menu.findItem(R.id.action_add_save).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_add_save)
        {
            saveMeals(false);
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveMeals(final boolean isForRedirectCall)
    {
        if(!etMealName.validate() || !etMealDescription.validate() || !etMealPrice.validate() || !etDaysPackage.validate() || !etMealPricePackage
                .validate())
        {
            return;
        }

        int rupees = Integer.parseInt(etMealPrice.getTextCustom());

        if(rupees <= 0)
        {
            etMealPrice.setError("Rupees must be greater than 0.");
            return;
        }

        showProgress();

        if(meals == null)
        {
            meals = new Meals();
        }

        meals.setId(meals.getContents() == null ? 0 : meals.getContents().length);

        meals.setName(etMealName.getTextCustom());

        meals.setDescription(etMealDescription.getTextCustom());

        meals.setPackageDays(Integer.parseInt(etDaysPackage.getTextCustom()));

        meals.setPackagePrice(Integer.parseInt(etMealPricePackage.getTextCustom()));

        meals.setRupees(rupees);

        firebase.setValue(meals, new Firebase.CompletionListener()
        {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase)
            {
                hideProgress();
                if(firebaseError == null)
                {
                    Toast.makeText(activity, "Updated", Toast.LENGTH_LONG).show();
                    if(isForRedirectCall)
                    {
                        redirectAccordingToPos(currentClickedPosition, -1);
                    }
                }
            }
        });
    }

    public static void setForceShowIcon(PopupMenu popupMenu)
    {
        try
        {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for(Field field : fields)
            {
                if("mPopup".equals(field.getName()))
                {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
    }

    private void redirectAccordingToPos(int pos, int subpos)
    {
        if(meals == null)
        {
            saveMeals(true);
            return;
        }
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

        //path = subpos == -1 ? path : path + "/" + subpos;

        System.out.println(path);

        getFMTransection().replace(R.id.main_activity_container, ContentDetailFragment.getInstance(path, subpos), ContentDetailFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private void deleteAccordingToPos(int pos, int subpos)
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

        //path = subpos == -1 ? path : path + "/" + subpos;

        System.out.println(path);

        ArrayList<Contents> contentses = new ArrayList<>(Arrays.asList(contentsList.get(pos).getContents()));

        contentses.remove(subpos);

        Contents[] contentses1 = contentses.toArray(new Contents[contentses.size()]);

        contentsList.get(pos).setContents(contentses1);

        showProgress();

        getFireBase().child(path).setValue(contentsList.get(pos).getContents(), new Firebase.CompletionListener()
        {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase)
            {
                hideProgress();
                if(firebaseError == null)
                {
                    Toast.makeText(activity, "Updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
