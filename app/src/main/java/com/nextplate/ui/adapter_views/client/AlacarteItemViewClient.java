package com.nextplate.ui.adapter_views.client;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nextplate.R;
import com.nextplate.models.AlACarte;
import com.nextplate.models.Contents;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.nlopez.smartadapters.views.BindableLayout;
/**
 * Created by AmaN on 3/6/2016.
 */
public class AlacarteItemViewClient extends BindableLayout<AlACarte>
{
    @Bind(R.id.alacarte_item_view_client_tv_alacarte_name)
    TextView name;
    @Bind(R.id.alacarte_item_view_client_tv_alacarte_rupees)
    TextView rupees;
    @Bind(R.id.alacarte_item_view_client_tv_alacarte_pack_rupees)
    TextView tvPackageDetails;
    @Bind(R.id.alacarte_item_view_client_lv_listing)
    ListView lvShowCase;

    public AlacarteItemViewClient(Context context)
    {
        super(context);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.alacarte_item_view_client;
    }

    @Override
    public void bind(final AlACarte alacartes)
    {
        setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT));
        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemAction(view.getId(), alacartes, view);
            }
        });

        name.setText(alacartes.getName());
        tvPackageDetails.setText(String.format("Order time: %s to %s", alacartes.getFromTime(), alacartes.getToTime()));
        rupees.setText(String.format("%s Rs.", String.valueOf(alacartes.getPrice())));
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
