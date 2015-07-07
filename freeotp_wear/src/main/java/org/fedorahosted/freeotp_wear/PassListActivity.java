package org.fedorahosted.freeotp_wear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.florent37.emmet.Emmet;

import org.fedorahosted.freeotp.R;
import org.fedorahosted.libcommon.Constant;
import org.fedorahosted.libcommon.ListItem;
import org.fedorahosted.libcommon.SmartphoneProtocol;
import org.fedorahosted.libcommon.WearProtocol;

public class PassListActivity extends Activity implements Constant,WearableListView.ClickListener,WearProtocol {
    private final String tag=this.getClass().getSimpleName();
    private ListItem listItems;
    private WearableListView mListView;
    private ProgressBar mProgress;

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

        //initialise Emmet
        Emmet.onCreate(this);

        Emmet.registerReceiver(WearProtocol.class, this);
        SmartphoneProtocol smartphoneProtocol = Emmet.createSender(SmartphoneProtocol.class);

        smartphoneProtocol.hello(); //envoie le message hello smartphone
    }

//        mTeleportClient.sendMessage(GET_TOKEN_LIST, null);
//
//
//
//
//    //For DataItem API changes
//    public void onEvent(DataMap dataMap) {
//        listItems=new ListItem(TeleportClient.byteToParcel(dataMap.getByteArray("byte")));
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mProgress.setVisibility(View.GONE);
//                advAdapter=new AdvancedListAdapter(PassListActivity.this, listItems);
//                mListView.setAdapter(advAdapter);
//                mListView.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    //For Message API receiving
//    public void onEvent(final String path) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                toast(path);
//                advAdapter.updateViewWithCode(path);
//            }
//        });
//    }


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

    @Override
    public void onCodeReceived(String code) {

    }

    @Override
    public void onReceived(ListItem listItem) {

    }

//    public void getTokenForPosition(int i) {
//        mTeleportClient.sendMessage(GET_TOKEN_CODE+i, null);
//    }
}
