package org.fedorahosted.freeotp;

import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
        MyItemView itemView = (MyItemView) viewHolder.itemView;


        TextView txtView = (TextView) itemView.findViewById(R.id.text);
        txtView.setText(listItems.get(i).getText());
        final CircledImageView imgView = (CircledImageView) itemView.findViewById(R.id.image);
//        DaVinci.with(context).load(listItems.get(i).getIcon()).into(new DaVinci.Callback() {
//            @Override
//            public void onBitmapLoaded(String s, Bitmap bitmap) {
//                Drawable d = new BitmapDrawable(context.getResources(),bitmap);
//                imgView.setImageDrawable(d);
//            }
//        });

        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                EventBus.getDefault().postRemote(GET_TOKEN_LIST, context);
                Toast.makeText(context,"toto",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }
} 