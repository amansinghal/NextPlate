package com.nextplate.ui.adapter_views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nextplate.R;
import com.nextplate.models.Contents;
import com.nextplate.models.Meals;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;
/**
 * Created by AmaN on 3/6/2016.
 */
public class ContentItemView extends BindableLayout<Contents>
{
    @Bind(R.id.content_item_view_tv_name)
    TextView name;
    @Bind(R.id.content_item_view_iv)
    ImageView ivImage;
    @Bind(R.id.content_item_view_ivb_edit)
    ImageButton ivb_edit;

    public ContentItemView(Context context)
    {
        super(context);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.content_item_view;
    }

    @Override
    public void bind(final Contents contents)
    {
        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT));
        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemAction(v.getId(), contents, v);
            }
        });

        ivb_edit.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemAction(view.getId(), contents, view);
            }
        });

        name.setText(contents.getName());
    }
}
