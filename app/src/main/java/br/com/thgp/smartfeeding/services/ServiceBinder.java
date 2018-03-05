package br.com.thgp.smartfeeding.services;

import android.os.Binder;

/**
 * Created by tiago on 05/03/18.
 */

public class ServiceBinder extends Binder {
    private IKnotServiceConnection mBinder;

    public ServiceBinder(IKnotServiceConnection s) {
        mBinder = s;
    }

    public IKnotServiceConnection getService() {
        return mBinder;
    }

}
