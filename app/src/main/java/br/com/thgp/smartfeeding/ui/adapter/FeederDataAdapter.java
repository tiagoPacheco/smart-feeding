package br.com.thgp.smartfeeding.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.thgp.smartfeeding.model.FeederData;

/**
 * Created by tiago on 03/03/2018.
 */

public class FeederDataAdapter  extends BaseAdapter {

    private Context mContext;
    private List<FeederData> mDevicesData;

    public FeederDataAdapter(Context c, List<FeederData> devices){
        mContext = c;
        mDevicesData = devices;

    }

    @Override
    public int getCount() {
        if (mDevicesData != null && mDevicesData.size() > 0) {
            return mDevicesData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDevicesData != null && mDevicesData.size() > 0) {
            return mDevicesData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mDevicesData != null && mDevicesData.size() > 0 && mDevicesData.get(position) != null)  {

            //return Long.parseLong(mDevices.get(position).getToken());
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        TextView tv = new TextView(mContext);

        tv.setText("Sensor ID "+ (Integer.toString(mDevicesData.get(position).data.sensor_id)));

        ViewGroup.LayoutParams param = tv.getLayoutParams();
        if (param != null )param.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

        tv.setTextSize(25);

        return tv;
    }
}
