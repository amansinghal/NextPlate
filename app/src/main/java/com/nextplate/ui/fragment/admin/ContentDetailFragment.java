package com.nextplate.ui.fragment.admin;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nextplate.R;
import com.nextplate.core.fragment.BaseFragment;
import com.nextplate.core.util.Utility;
import com.nextplate.models.AlACarte;
import com.nextplate.models.Contents;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
/**
 * Created by AmaN on 3/9/2016.
 */
public class ContentDetailFragment extends BaseFragment
{
    private static final String KEY_PATH = "key_path";
    private static final String KEY_POSITION = "key_position";
    public static final String TAG = "ContentDetailFragment";
    @Bind(R.id.frag_content_details_et_name)
    MaterialEditText materialEditText;
    @Bind(R.id.frag_content_details_ll_extra_container)
    LinearLayout llExtraContainer;
    @Bind(R.id.frag_content_details_et_price)
    MaterialEditText etPrice;
    @Bind(R.id.frag_content_details_et_from_time)
    MaterialEditText etTimeFrom;
    @Bind(R.id.frag_content_details_et_to_time)
    MaterialEditText etTimeTo;
    private String getKeyPath;
    private int count = 0, position = 0;
    private Firebase firebase;
    ArrayList<Contents> contentses = new ArrayList<>();
    boolean isCallForAlaCarte;
    private List<AlACarte> contentAlacarte = new ArrayList<>();

    public static Fragment getInstance(String path, int position)
    {
        ContentDetailFragment contentDetailFragment = new ContentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ContentDetailFragment.KEY_PATH, path);
        bundle.putInt(ContentDetailFragment.KEY_POSITION, position);
        contentDetailFragment.setArguments(bundle);
        return contentDetailFragment;
    }

    @Override
    public void onFragmentReady()
    {
        setTitle("Details");
        setEmptyValidator(materialEditText);

        if(getArguments() != null)
        {
            getKeyPath = getArguments().getString(KEY_PATH, "");
            position = getArguments().getInt(KEY_POSITION, -1);
        }

        isCallForAlaCarte = getKeyPath.contains(AlACarteFragment.KEY);

        if(isCallForAlaCarte)
        {
            llExtraContainer.setVisibility(View.VISIBLE);
            setEmptyValidator(etPrice);
            setEmptyValidator(etTimeFrom);
            setEmptyValidator(etTimeTo);
        }
        else
        {
            llExtraContainer.setVisibility(View.GONE);
        }

        firebase = getFireBase().child(getKeyPath);
        firebase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                System.out.println(dataSnapshot);
                if(dataSnapshot.getValue() != null)
                {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        if(isCallForAlaCarte)
                        {
                            contentAlacarte.add(postSnapshot.getValue(AlACarte.class));
                        }
                        else
                        {
                            contentses.add(postSnapshot.getValue(Contents.class));
                        }
                        count++;
                    }

                    if(!contentAlacarte.isEmpty() && isCallForAlaCarte)
                    {
                        fillDataForAlacarte();
                    }
                    else
                    {
                        fillData();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                System.out.println(firebaseError);
            }
        });
    }

    private void fillDataForAlacarte()
    {
        if(position == -1)
        {
            return;
        }
        materialEditText.setText(contentAlacarte.get(position).getName());
        etPrice.setText(""+contentAlacarte.get(position).getPrice());
        etTimeFrom.setText(contentAlacarte.get(position).getFromTime());
        etTimeTo.setText(contentAlacarte.get(position).getToTime());
    }

    @Override
    public int getFragmentLayout()
    {
        return R.layout.frag_content_detail;
    }

    @OnClick(R.id.frag_content_details_btn_save)
    public void saveContents()
    {
        if(materialEditText.validate() && etTimeFrom.validate() && etTimeTo.validate() && etPrice.validate())
        {
            showProgress();

            if(!isCallForAlaCarte)
            {
                Contents contents = new Contents();
                contents.setName(materialEditText.getTextCustom());
                contents.setImageUrl("");
                firebase.child("/" + (position == -1 ? count : position)).setValue(contents, new Firebase.CompletionListener()
                {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase)
                    {
                        hideProgress();
                        if(firebaseError == null)
                        {
                            Toast.makeText(activity, "Updated", Toast.LENGTH_LONG).show();
                            getFragmentManager().popBackStack();
                        }
                    }
                });
            }
            else
            {
                AlACarte alACarte = new AlACarte();
                alACarte.setName(materialEditText.getTextCustom());
                alACarte.setImageUrl("");
                alACarte.setPrice(etPrice.getTextAsInt());
                alACarte.setFromTime(etTimeFrom.getTextCustom());
                alACarte.setToTime(etTimeTo.getTextCustom());
                firebase.child("/" + (position == -1 ? count : position)).setValue(alACarte, new Firebase.CompletionListener()
                {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase)
                    {
                        hideProgress();
                        if(firebaseError == null)
                        {
                            Toast.makeText(activity, "Updated", Toast.LENGTH_LONG).show();
                            getFragmentManager().popBackStack();
                        }
                    }
                });
            }
        }
    }

    @OnClick(R.id.frag_content_details_et_from_time)
    public void fromTime()
    {
        Utility.timePicker(activity, new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1)
            {
                etTimeFrom.setText(String.format("%d:%s", i, i1 <10 ? "0"+i1:i1));
            }
        }, "From time");
    }

    @OnClick(R.id.frag_content_details_et_to_time)
    public void toTime()
    {
        Utility.timePicker(activity, new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1)
            {
                etTimeTo.setText(String.format("%d:%s", i, i1 <10 ? "0"+i1:i1));
            }
        }, "To time");
    }

    private void fillData()
    {
        if(position == -1)
        {
            return;
        }
        materialEditText.setText(contentses.get(position).getName());
    }
}
