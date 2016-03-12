package com.nextplate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nextplate.R;
import com.nextplate.core.fragment.BaseFragment;
import com.nextplate.models.Contents;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;

import java.util.ArrayList;

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
    private String getKeyPath;
    private int count = 0, position = 0;
    private Firebase firebase;
    ArrayList<Contents> contentses = new ArrayList<>();

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
        materialEditText.addValidator(new METValidator("Must not empty")
        {
            @Override
            public boolean isValid(
                    @NonNull
                    CharSequence text, boolean isEmpty)
            {
                return !isEmpty;
            }
        });
        if(getArguments() != null)
        {
            getKeyPath = getArguments().getString(KEY_PATH, "");
            position = getArguments().getInt(KEY_POSITION, -1);
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
                        contentses.add(postSnapshot.getValue(Contents.class));
                        count++;
                    }

                    if(!contentses.isEmpty())
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

    @Override
    public int getFragmentLayout()
    {
        return R.layout.frag_content_detail;
    }

    @OnClick(R.id.frag_content_details_btn_save)
    public void saveContents()
    {
        if(materialEditText.validate())
        {
            showProgress();
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
