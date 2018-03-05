package br.com.thgp.smartfeeding.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import br.com.thgp.smartfeeding.R;
import br.com.thgp.smartfeeding.model.FeederDevice;
import br.com.thgp.smartfeeding.ui.adapter.FeederDeviceAdapter;
import br.com.thgp.smartfeeding.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevicesFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private OnFragmentInteractionListener mListener;
    private ListView mListView;

    public DevicesFragment() {
        // Required empty public constructor
    }

    public static DevicesFragment newInstance() {
        DevicesFragment fragment = new DevicesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initListView();
        return mListView;
    }

    private void initListView(){
        mListView = new ListView(getContext());

        mListView.setAdapter(new FeederDeviceAdapter(getActivity(), Util.DevicesList));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Util.CurrentFeederDevice  = (FeederDevice) parent.getItemAtPosition(position);
            if(Util.CurrentFeederDevice != null) {
                Toast.makeText(getContext(), R.string.device_setted, Toast.LENGTH_LONG).show();
            }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
