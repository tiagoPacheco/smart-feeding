package br.com.thgp.smartfeeding.ui;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.thgp.smartfeeding.R;
import br.com.thgp.smartfeeding.util.PreferenceUtil;
import br.com.thgp.smartfeeding.util.TypePreferenceEnum;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeederFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView mTextPetName;
    private TextView mTextWeight;
    private TextView mTextQtyFood;

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

        mTextPetName = view.findViewById(R.id.text_pet_name);
        mTextWeight = view.findViewById(R.id.text_weight);
        mTextQtyFood = view.findViewById(R.id.text_quantity_food);

        String name = (String) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Name, TypePreferenceEnum.String);
        if(!name.isEmpty()){
            mTextPetName.setText(name);
        }

        Float weight = (Float) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Weight, TypePreferenceEnum.Float);
        mTextWeight.setText(weight.toString());

        Float amount = (Float) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Amount_Automatic, TypePreferenceEnum.Float);
        mTextQtyFood.setText(amount.toString());

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
