package br.com.thgp.smartfeeding.services;

import java.util.List;

import br.com.thgp.smartfeeding.model.FeederData;

/**
 * Created by tiago on 05/03/18.
 */

public interface OnDataChangedListener {

    public void onDataChanged (List<FeederData> deviceData);
}
