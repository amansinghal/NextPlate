package com.nextplate.ui.adapter_views.admin;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nextplate.R;
import com.nextplate.models.Meals;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;
/**
 * Created by AmaN on 3/6/2016.
 */
public class MealsItemView extends BindableLayout<Meals>
{
    @Bind(R.id.meal_item_view_name)
    TextView name;
    @Bind(R.id.meal_item_view_rupees)
    TextView rupees;
    @Bind(R.id.meal_item_view_desc)
    TextView desc;
    @Bind(R.id.meal_item_view_ivb_edit)
    ImageButton ivbEdit;

    public MealsItemView(Context context)
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
    public void bind(final Meals meals)
    {
        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemAction(view.getId(), meals, view);
            }
        });

        ivbEdit.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemAction(view.getId(), meals, view);
            }
        });

        name.setText(meals.getName());
        rupees.setText(meals.getRupees() + "Rs.");
        desc.setText(meals.getDescription());
    }
}
