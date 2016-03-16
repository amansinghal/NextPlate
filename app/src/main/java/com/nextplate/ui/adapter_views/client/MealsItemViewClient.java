package com.nextplate.ui.adapter_views.client;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nextplate.R;
import com.nextplate.models.Contents;
import com.nextplate.models.Meals;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;
/**
 * Created by AmaN on 3/6/2016.
 */
public class MealsItemViewClient extends BindableLayout<Meals>
{
    @Bind(R.id.meal_item_view_client_tv_meal_name)
    TextView name;
    @Bind(R.id.meal_item_view_client_tv_meal_rupees)
    TextView rupees;
    @Bind(R.id.meal_item_view_client_lv_listing)
    ListView lvShowCase;

    public MealsItemViewClient(Context context)
    {
        super(context);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.meal_item_view_client;
    }

    @Override
    public void bind(final Meals meals)
    {
        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT));
        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemAction(view.getId(), meals, view);
            }
        });

        name.setText(meals.getName());
        rupees.setText(meals.getRupees() + " Rs.");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.simple_list_item_1,
                                                                     getShowCaseItem(meals.getContents()));
        lvShowCase.setAdapter(arrayAdapter);
    }

    private ArrayList<String> getShowCaseItem(Contents[] contentses)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < contentses.length; i++)
        {
            if(i < 3)
            {
                arrayList.add(contentses[i].getName());
            }
            else
            {
                arrayList.add("and More");
                break;
            }
        }
        return arrayList;
    }
}
