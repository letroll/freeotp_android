package org.fedorahosted.freeotp_wear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.davinci.DaVinci;

import org.fedorahosted.freeotp.R;
import org.fedorahosted.libcommon.ListItem;

/**
 * Created by letroll on 24/05/15.
 */
public class AdvancedListAdapter extends WearableListView.Adapter {


    private final ListItem listItems;
    private Context context;

    public AdvancedListAdapter(Context context,ListItem listItems){
        this.context=context;
        this.listItems=listItems;
    }
    
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new WearableListView.ViewHolder(new MyItemView(context));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, final int i) {
        MyItemView itemView = (MyItemView) viewHolder.itemView;

        final TextView txtView = (TextView) itemView.findViewById(R.id.text);
        String tmp=listItems.get(i).getText();
        tmp=tmp.substring(0,tmp.indexOf(":"));
        txtView.setText(tmp);
        final CircledImageView imgView = (CircledImageView) itemView.findViewById(R.id.image);
        DaVinci.with(context).load(listItems.get(i).getIcon()).into(new DaVinci.Callback() {
            @Override
            public void onBitmapLoaded(String s, Bitmap bitmap) {
                Drawable d = new BitmapDrawable(context.getResources(),bitmap);
                imgView.setImageDrawable(d);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

} 