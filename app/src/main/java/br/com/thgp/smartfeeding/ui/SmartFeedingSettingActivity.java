package br.com.thgp.smartfeeding.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.thgp.smartfeeding.R;
import br.com.thgp.smartfeeding.util.PreferenceUtil;
import br.com.thgp.smartfeeding.util.TypePreferenceEnum;

public class SmartFeedingSettingActivity extends AppCompatActivity {

    private EditText mAmountText;
    private EditText mPeriodText;
    private FloatingActionButton mbtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_feeding_setting);

        mAmountText = findViewById(R.id.text_amount);
        mPeriodText = findViewById(R.id.text_period);
        mbtnRegister = findViewById(R.id.btn_register);

        Float amount = (Float) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Amount_Automatic, TypePreferenceEnum.Float);
        mAmountText.setText(amount.toString());

        Integer period = (Integer) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Period, TypePreferenceEnum.Int);
        mPeriodText.setText(period.toString());

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAmountText.getText().toString().isEmpty() || mPeriodText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.error_all_field_required, Toast.LENGTH_LONG).show();
                    return;
                }

                PreferenceUtil.setPreferenceValue(PreferenceUtil.Preference_Amount_Automatic,
                        mAmountText.getText().toString(), TypePreferenceEnum.Float);

                PreferenceUtil.setPreferenceValue(PreferenceUtil.Preference_Period,
                        Integer.parseInt(mPeriodText.getText().toString()), TypePreferenceEnum.Int);
                finish();
            }
        });
    }
}
