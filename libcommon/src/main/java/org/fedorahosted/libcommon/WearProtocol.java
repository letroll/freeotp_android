package org.fedorahosted.libcommon;

/**
 * Created by letroll on 08/07/15.
 * Le protocol Smartphone->Wear
 */
public interface WearProtocol {
    void onCodeReceived(String code);
    void onReceived(ListItem listItem);
}
