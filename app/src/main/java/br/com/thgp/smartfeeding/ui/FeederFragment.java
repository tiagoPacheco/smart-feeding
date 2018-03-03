package br.com.thgp.smartfeeding.ui;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.thgp.smartfeeding.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeederFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private FloatingActionButton mbtnFeedPet;
    private FloatingActionButton mbtnPetRegister;
    private FloatingActionButton mbtnSmartFeedingSettings;
    private FloatingActionButton mbtnStockSettings;

    public FeederFragment() {
        // Required empty public constructor
    }

    public static FeederFragment newInstance() {
        FeederFragment fragment = new FeederFragment();
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
        View view = inflater.inflate(R.layout.fragment_feeder, container, false);

        mbtnPetRegister = view.findViewById(R.id.btn_pet_register);
        mbtnSmartFeedingSettings = view.findViewById(R.id.btn_smartfeeding_settings);
        mbtnStockSettings = view.findViewById(R.id.btn_stock_settings);

        mbtnPetRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PetRegisterActivity.class);
                startActivity(intent);
            }
        });

        mbtnSmartFeedingSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SmartFeedingSettingActivity.class);
                startActivity(intent);
            }
        });

        mbtnStockSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StockSettingActivity.class);
                startActivity(intent);
            }
        });
        return view;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
