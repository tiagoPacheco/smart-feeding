package br.com.thgp.smartfeeding.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import br.com.thgp.smartfeeding.R;
import br.com.thgp.smartfeeding.util.PreferenceUtil;
import br.com.thgp.smartfeeding.util.TypePreferenceEnum;

public class PetRegisterActivity extends AppCompatActivity {

    private EditText mNameText;
    private EditText mWeightText;
    private EditText mBreedText;
    private EditText mBirthDateText;
    private RadioButton mMaleRadioButton;
    private RadioButton mFemaleRadioButton;
    private FloatingActionButton mbtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_register);

        mNameText = findViewById(R.id.text_name_pet);
        mWeightText = findViewById(R.id.text_weight);
        mBreedText = findViewById(R.id.text_breed);
        mBirthDateText = findViewById(R.id.text_birth_date);
        mbtnRegister = findViewById(R.id.btn_register);
        mMaleRadioButton = findViewById(R.id.radio_male);
        mFemaleRadioButton = findViewById(R.id.radio_female);

        String name = (String) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Name, TypePreferenceEnum.String);
        if(!name.isEmpty()){
            mNameText.setText(name);
        }

        Float weight = (Float) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Weight, TypePreferenceEnum.Float);
        mWeightText.setText(weight.toString());

        Boolean isMale = (Boolean) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Genre_Masc, TypePreferenceEnum.Bool);
        if(isMale){
            mMaleRadioButton.setChecked(true);
            mFemaleRadioButton.setChecked(false);
        }
        else{
            mMaleRadioButton.setChecked(false);
            mFemaleRadioButton.setChecked(true);
        }

        String breed = (String) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Breed, TypePreferenceEnum.String);
        if(!breed.isEmpty()){
            mBreedText.setText(breed);
        }

        String birthDate = (String) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_BirthDate, TypePreferenceEnum.String);
        if(!birthDate.isEmpty()){
            mBirthDateText.setText(birthDate);
        }

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mNameText.getText().toString().isEmpty() || mWeightText.getText().toString().isEmpty()
                    || mBreedText.getText().toString().isEmpty() || mBirthDateText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.error_all_field_required, Toast.LENGTH_LONG).show();
                    return;
                }

                PreferenceUtil.setPreferenceValue(PreferenceUtil.Preference_Name,
                        mNameText.getText().toString(), TypePreferenceEnum.String);
                PreferenceUtil.setPreferenceValue(PreferenceUtil.Preference_Weight,
                        mWeightText.getText().toString(), TypePreferenceEnum.Float);
                PreferenceUtil.setPreferenceValue(PreferenceUtil.Preference_Genre_Masc,
                        mMaleRadioButton.isChecked(), TypePreferenceEnum.Bool);
                PreferenceUtil.setPreferenceValue(PreferenceUtil.Preference_Breed,
                        mBreedText.getText().toString(), TypePreferenceEnum.String);
                PreferenceUtil.setPreferenceValue(PreferenceUtil.Preference_BirthDate,
                        mBirthDateText.getText().toString(), TypePreferenceEnum.String);
                finish();
            }
        });
    }
}
