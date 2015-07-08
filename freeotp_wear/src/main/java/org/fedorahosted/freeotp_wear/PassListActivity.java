package org.fedorahosted.freeotp_wear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.emmet.Emmet;

import org.fedorahosted.freeotp.R;
import org.fedorahosted.libcommon.ListItem;
import org.fedorahosted.libcommon.SmartphoneProtocol;
import org.fedorahosted.libcommon.WearProtocol;

public class PassListActivity extends Activity
    implements WearableListView.ClickListener, WearProtocol {
  private final String tag = this.getClass().getSimpleName();
  private ListItem listItems;
  private WearableListView mListView;
  private ProgressBar mProgress;

  private AdvancedListAdapter advAdapter;
  private SmartphoneProtocol smartphoneProtocol;
  private View mItemView;

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
    smartphoneProtocol = Emmet.createSender(SmartphoneProtocol.class);

    smartphoneProtocol.hello(); //envoie le message hello smartphone
  }

  private void log(String txt) {
    Log.e(tag, txt);
  }

  private void toast(final String txt) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(PassListActivity.this, txt, Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onClick(WearableListView.ViewHolder viewHolder) {
    getTokenForPosition(viewHolder.getAdapterPosition());
    mItemView = viewHolder.itemView;
  }

  @Override
  public void onTopEmptyRegionClick() {

  }

  @Override
  public void onCodeReceived(final String code) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        ((TextView) mItemView.findViewById(R.id.text)).setText(code);
      }
    });
  }

  @Override
  public void onReceived(ListItem mListItems) {
    listItems = mListItems;
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        mProgress.setVisibility(View.GONE);
        advAdapter = new AdvancedListAdapter(PassListActivity.this, listItems);
        mListView.setAdapter(advAdapter);
        mListView.setVisibility(View.VISIBLE);
      }
    });
  }

  public void getTokenForPosition(int i) {
    smartphoneProtocol.getTokenForPosition(i);
  }
}
