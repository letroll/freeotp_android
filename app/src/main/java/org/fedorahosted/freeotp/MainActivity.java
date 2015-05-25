/*
 * FreeOTP
 *
 * Authors: Nathaniel McCallum <npmccallum@redhat.com>
 *
 * Copyright (C) 2013  Nathaniel McCallum, Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Portions Copyright 2009 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fedorahosted.freeotp;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import org.fedorahosted.freeotp.add.AddActivity;
import org.fedorahosted.freeotp.add.ScanActivity;
import org.fedorahosted.libcommon.Constant;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity implements Constant,OnMenuItemClickListener {
    private final String tag=this.getClass().getSimpleName();
    private TokenAdapter mTokenAdapter;
    private DataSetObserver mDataSetObserver;

    private TeleportClient mTeleportClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        setContentView(R.layout.main);

        mTokenAdapter = new TokenAdapter(this);
        ((GridView) findViewById(R.id.grid)).setAdapter(mTokenAdapter);

        // Don't permit screenshots since these might contain OTP codes.
        getWindow().setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE);

        mDataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (mTokenAdapter.getCount() == 0)
                    findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                else
                    findViewById(android.R.id.empty).setVisibility(View.GONE);
            }
        };
        mTokenAdapter.registerDataSetObserver(mDataSetObserver);

        mTeleportClient = new TeleportClient(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTeleportClient.connect();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();
        EventBus.getDefault().unregister(this);
    }


    private void log(String txt){
        Log.e(tag, txt);
    }


    //For DataItem API changes
    public void onEvent(DataMap dataMap) {
        final String s = dataMap.getString("string");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "DataItem - " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //For Message API receiving
    public void onEvent(final String text) {
        log("onEvent:"+text);
        if(text.equals(GET_TOKEN_LIST))
            mTeleportClient.syncObject("byte",mTokenAdapter.getItems());

        if(text.startsWith(GET_TOKEN_CODE)){
            int pos=Integer.parseInt(text.substring(GET_TOKEN_CODE.length()));
            mTeleportClient.sendMessage(mTokenAdapter.getCodeOnWear(pos),null);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTokenAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTokenAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTokenAdapter.unregisterDataSetObserver(mDataSetObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_scan).setVisible(ScanActivity.haveCamera());
        menu.findItem(R.id.action_scan).setOnMenuItemClickListener(this);
        menu.findItem(R.id.action_add).setOnMenuItemClickListener(this);
        menu.findItem(R.id.action_about).setOnMenuItemClickListener(this);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_scan:
            startActivity(new Intent(this, ScanActivity.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            return true;

        case R.id.action_add:
            startActivity(new Intent(this, AddActivity.class));
            return true;

        case R.id.action_about:
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }

        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Uri uri = intent.getData();
        if (uri != null)
            TokenPersistence.addWithToast(this, uri.toString());
    }
}
