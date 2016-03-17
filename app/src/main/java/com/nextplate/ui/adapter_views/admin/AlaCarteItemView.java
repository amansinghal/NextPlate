package com.nextplate.ui.adapter_views.admin;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nextplate.R;
import com.nextplate.models.AlACarte;
import com.nextplate.models.Meals;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;
/**
 * Created by AmaN on 3/6/2016.
 */
public class AlaCarteItemView extends BindableLayout<AlACarte>
{
    @Bind(R.id.meal_item_view_name)
    TextView name;
    @Bind(R.id.meal_item_view_rupees)
    TextView rupees;
    @Bind(R.id.meal_item_view_desc)
    TextView desc;
    @Bind(R.id.meal_item_view_ivb_edit)
    ImageButton ivbEdit;

    public AlaCarteItemView(Context context)
    {
        super(context);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.meal_item_view;
    }

    @Override
    public void bind(final AlACarte alACarte)
    {
        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemAction(view.getId(), alACarte, view);
            }
        });

        ivbEdit.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemAction(view.getId(), alACarte, view);
            }
        });

        name.setText(alACarte.getName());
        rupees.setText(alACarte.getPrice() + " Rs.");
    }
}
