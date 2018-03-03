package br.com.thgp.smartfeeding.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.thgp.smartfeeding.model.FeederDevice;

/**
 * Created by tiago on 03/03/2018.
 */

public class FeederDeviceAdapter extends BaseAdapter {

    private Context mContext;
    private List<FeederDevice> mDevices;

    public FeederDeviceAdapter(Context c, List<FeederDevice> devices){
        mContext = c;
        mDevices = devices;

    }

    @Override
    public int getCount() {
        if (mDevices != null && mDevices.size() > 0) {
            return mDevices.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDevices != null && mDevices.size() > 0) {
            return mDevices.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mDevices != null && mDevices.size() > 0 && mDevices.get(position) != null)  {

            //return Long.parseLong(mDevices.get(position).getToken());
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(mContext);

        tv.setText(mDevices.get(position).getUuid());

        ViewGroup.LayoutParams param = tv.getLayoutParams();
        if (param != null )param.height = (ViewGroup.LayoutParams.WRAP_CONTENT);

        tv.setTextSize(25);



        return tv;
    }
}
