package org.fedorahosted.freeotp;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.fedorahosted.libcommon.Constant;
import org.fedorahosted.libcommon.ListItem;

import pl.tajchert.buswear.EventBus;

public class PassListActivity extends Activity implements Constant,WearableListView.ClickListener {
    private final String tag=this.getClass().getSimpleName();
    private ListItem listItems;
    private WearableListView mListView;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mProgress = (ProgressBar) stub.findViewById(R.id.pgrb_token_list);
                mListView = (WearableListView) stub.findViewById(R.id.listView1);
                mListView.setClickListener(PassListActivity.this);
            }
        });

        EventBus.getDefault().postRemote(GET_TOKEN_LIST, PassListActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void log(String txt){
        Log.e(tag, txt);
    }

    private void toast(String txt){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {

    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    public void onEvent(ListItem itemsName){
        listItems=itemsName;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
                mListView.setAdapter(new AdvancedListAdapter(PassListActivity.this, listItems));
                mListView.setVisibility(View.VISIBLE);
            }
        });
    }
}
