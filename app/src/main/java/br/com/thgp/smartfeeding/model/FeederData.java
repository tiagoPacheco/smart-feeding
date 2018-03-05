package br.com.thgp.smartfeeding.model;

import br.org.cesar.knot.lib.model.AbstractThingData;

/**
 * Created by tiago on 03/03/2018.
 */

public class FeederData extends AbstractThingData {

    private String uuid;
    private String currentValue;

    public String getCurrentValue() {
        if(data != null){
            String  value = (String) data.value;
            return value;
        }
        return null;
    }

    public String getSwitchUUID() {
        return source;
    }

    public void setSwitchUUID(String switchUUID) {
        this.uuid = switchUUID;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public void setData(boolean data){
        super.data.value = data;
    }

    public boolean getData(){
        return (Boolean)data.value;
    }

    @Override
    public String toString() {
        String value = super.toString() + " switchUUID = " + getSwitchUUID() +
                " currentValue = " + getCurrentValue();
        return value;
    }
}
