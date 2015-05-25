package org.fedorahosted.freeotp_wear;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.fedorahosted.freeotp.R;

import java.util.ArrayList;

/**
 * Created by letroll on 24/05/15.
 */
public final class SimpleListAdapter  extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    private ArrayList<String> listItems;
    public SimpleListAdapter(Context context, ArrayList<String> listItems) {
        mInflater = LayoutInflater.from(context);
        this.listItems=listItems;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(
                mInflater.inflate(R.layout.row_simple_item_layout, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        TextView view = (TextView) holder.itemView.findViewById(R.id.textView);
        view.setText(listItems.get(position).toString());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}