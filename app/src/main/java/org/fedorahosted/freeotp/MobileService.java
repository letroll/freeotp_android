package org.fedorahosted.freeotp;

import android.content.Intent;
import android.util.Log;

import com.github.florent37.emmet.Emmet;
import com.github.florent37.emmet.EmmetWearableListenerService;
import com.google.android.gms.wearable.MessageEvent;

import org.fedorahosted.libcommon.SmartphoneProtocol;
import org.fedorahosted.libcommon.WearProtocol;


/**
 * Created by letroll on 24/05/15.
 */
public class MobileService  extends EmmetWearableListenerService implements SmartphoneProtocol {
    private static final String TAG = "MobileService";

    private WearProtocol wearProtocol;

    @Override
    public void onCreate() {
        super.onCreate();

        //initialise la récéption de données
        Emmet.registerReceiver(SmartphoneProtocol.class, this);

        //initialise l'envoie de données vers la montre
        wearProtocol = Emmet.createSender(WearProtocol.class);
    }

    //lorsque le smartphone reçois le message hello (envoyé depuis wear)
//    @Override
//    public void hello() {
//        //Utilise Retrofit pour réaliser un appel REST
//        AndroidService androidService = new RestAdapter.Builder()
//                .setEndpoint(AndroidService.ENDPOINT)
//                .build().create(AndroidService.class);
//
//        //Récupère et deserialise le contenu de mon fichier JSON en objet List<AndroidVersion>
//        androidService.getElements(new Callback<List<AndroidVersion>>() {
//            @Override
//            public void success(List<AndroidVersion> androidVersions, Response response) {
//
//                //envoie cette liste à la montre
//                wearProtocol.onAndroidVersionsReceived(androidVersions);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//            }
//        });
//    }

    @Override
    public void hello() {
        Intent startIntent = new Intent(getBaseContext(), MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);
    }
}
