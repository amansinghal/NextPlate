package com.nextplate.ui.adapter_views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextplate.R;
import com.nextplate.models.ContentListing;
import com.nextplate.models.Contents;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;
import io.nlopez.smartadapters.views.BindableLayout;
/**
 * Created by AmaN on 3/6/2016.
 */
public class ContentListingItemView extends BindableLayout<ContentListing>
{
    @Bind(R.id.content_listing_tv_heading)
    TextView tvHeading;
    @Bind(R.id.content_listing_meal_btn_add_new)
    Button btnAddNew;
    @Bind(R.id.content_listing_meal_rv_meal_listing)
    RecyclerView rvListing;

    public ContentListingItemView(Context context)
    {
        super(context);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.content_listing_item_view;
    }

    @Override
    public void bind(final ContentListing contents)
    {
        tvHeading.setText(contents.getHeading());
        rvListing.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        if(contents.getContents() != null)
        {
            SmartAdapter.items(Arrays.asList(contents.getContents())).map(Contents.class, ContentItemView.class)
                    .listener(new ViewEventListener()
                    {
                        @Override
                        public void onViewEvent(int i, Object o, int i1, View view)
                        {

                        }
                    }).into(rvListing);
        }
    }
}
