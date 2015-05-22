package org.fedorahosted.freeotp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

public class MainActivity extends Activity {

    private Lock9View lock9View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                lock9View = (Lock9View) stub.findViewById(R.id.lock_9_view);
                lock9View.setCallBack(new Lock9View.CallBack() {
                    @Override
                    public void onFinish(String password) {
                        if(password.equals("357891")){
                            startActivity(new Intent(MainActivity.this,PassListActivity.class));
                        }else
                        Toast.makeText(MainActivity.this, "bad password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
