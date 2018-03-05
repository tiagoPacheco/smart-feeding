
package br.com.thgp.smartfeeding.services;

/**
 * Created by tiago on 05/03/18.
 */

public interface IKnotServiceConnection {
    void subscribe(String deviceUUID, OnDataChangedListener listener);
    void unsubscribe();
}
