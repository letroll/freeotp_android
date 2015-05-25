package org.fedorahosted.freeotp_wear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import org.fedorahosted.freeotp.R;
import org.fedorahosted.libcommon.Constant;
import org.fedorahosted.libcommon.ListItem;

import de.greenrobot.event.EventBus;

public class PassListActivity extends Activity implements Constant,WearableListView.ClickListener {
    private final String tag=this.getClass().getSimpleName();
    private ListItem listItems;
    private WearableListView mListView;
    private ProgressBar mProgress;

    private TeleportClient mTeleportClient;
    private AdvancedListAdapter advAdapter;

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

        log("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
        mTeleportClient = new TeleportClient(this);
        mTeleportClient.connect();
        mTeleportClient.sendMessage("startActivity", null);
        mTeleportClient.sendMessage(GET_TOKEN_LIST, null);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();
        EventBus.getDefault().unregister(this);
    }

    //For DataItem API changes
    public void onEvent(DataMap dataMap) {
        listItems=new ListItem(TeleportClient.byteToParcel(dataMap.getByteArray("byte")));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgress.setVisibility(View.GONE);
                advAdapter=new AdvancedListAdapter(PassListActivity.this, listItems);
                mListView.setAdapter(advAdapter);
                mListView.setVisibility(View.VISIBLE);
            }
        });
    }

    //For Message API receiving
    public void onEvent(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast(path);
                advAdapter.updateViewWithCode(path);
            }
        });
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

    public void getTokenForPosition(int i) {
        mTeleportClient.sendMessage(GET_TOKEN_CODE+i, null);
    }
}
