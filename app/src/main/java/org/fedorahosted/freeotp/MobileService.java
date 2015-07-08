package org.fedorahosted.freeotp;

import com.github.florent37.emmet.Emmet;
import com.github.florent37.emmet.EmmetWearableListenerService;

import org.fedorahosted.libcommon.Item;
import org.fedorahosted.libcommon.ListItem;
import org.fedorahosted.libcommon.SmartphoneProtocol;
import org.fedorahosted.libcommon.Token;
import org.fedorahosted.libcommon.TokenCode;
import org.fedorahosted.libcommon.WearProtocol;

/**
 * Created by letroll on 24/05/15.
 */
public class MobileService extends EmmetWearableListenerService implements SmartphoneProtocol {
  private static final String TAG = "MobileService";

  private WearProtocol wearProtocol;
  private TokenPersistence mTokenPersistence;

  @Override
  public void onCreate() {
    super.onCreate();

    //initialise la récéption de données
    Emmet.registerReceiver(SmartphoneProtocol.class, this);

    //initialise l'envoie de données vers la montre
    wearProtocol = Emmet.createSender(WearProtocol.class);

    mTokenPersistence = new TokenPersistence(getBaseContext());
  }

  @Override
  public void hello() {
    //Intent startIntent = new Intent(getBaseContext(), MainActivity.class);
    //startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //startActivity(startIntent);
    wearProtocol.onReceived(getItems());
  }

  @Override
  public void getTokenForPosition(int i) {
    String code = getCodeOnWear(i);
    wearProtocol.onCodeReceived(code);
  }

  private ListItem getItems() {
    ListItem itemsName = new ListItem();
    int len = mTokenPersistence.length();
    String id, uri;
    for (int i = 0; i < len; i++) {
      id = mTokenPersistence.get(i).getID();
      uri = mTokenPersistence.get(i).getImage() == null ? null
          : mTokenPersistence.get(i).getImage().toString();
      itemsName.add(new Item(uri, id));
    }
    return itemsName;
  }

  public String getCodeOnWear(int position) {
    Token token = mTokenPersistence.get(position);
    TokenCode codes = token.generateCodes();
    mTokenPersistence.save(token);
    return codes.getCurrentCode();
  }
}
