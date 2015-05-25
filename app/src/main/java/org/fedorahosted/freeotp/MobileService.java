package org.fedorahosted.freeotp;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.mariux.teleport.lib.TeleportService;

import de.greenrobot.event.EventBus;

/**
 * Created by letroll on 24/05/15.
 */
public class MobileService extends TeleportService {
    private static final String TAG = "MobileService";

    @Override
    public void onCreate() {
        super.onCreate();
    }


    //If we want to react to changes in background we need to implement onMessageReceived or onDataChanged plus post() for eventBus
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        //Notify potential other subcribers as you just overtake over message receiving
        EventBus.getDefault().post(messageEvent.getPath());

        //Our custom part
        Log.d(TAG, "onMessageReceived in Service : " + messageEvent);
        if (messageEvent.getPath().equals("startActivity")) {
            Intent startIntent = new Intent(getBaseContext(), MainActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startIntent);
        }
    }
}
