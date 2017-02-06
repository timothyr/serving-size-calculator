package sfu.timr.servingsizecalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalculateServingActivity extends AppCompatActivity implements TextWatcher {

    private static final String POT_NAME = "Pot Name";
    private static final String POT_WEIGHT = "Pot Weight";
    private String potName;
    private int potWeight;

    // For use with calculating the serving size in a pot.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_pot);

        EditText servingWeight = (EditText) findViewById(R.id.textWeightWithFood);
        servingWeight.addTextChangedListener(this);

        extractDataFromIntent();
        setupBackButton();
        setupDefaultText();
        setupServingSize(2); //temporaily 22
        setupFoodWeight();
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        potName = intent.getStringExtra(POT_NAME);
        potWeight = intent.getIntExtra(POT_WEIGHT, 0);
    }

    private void setupBackButton() {
        Button backButton = (Button) findViewById(R.id.calculate_pot_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context, Pot returnPot) {
        Intent intent = new Intent(context, CalculateServingActivity.class);
        intent.putExtra(POT_NAME, returnPot.getName());
        intent.putExtra(POT_WEIGHT, returnPot.getWeightInG());
        return intent;
    }

    private void setupDefaultText() {
        TextView getPotName = (TextView) findViewById(R.id.textPotName);
        getPotName.setText(potName);

        TextView getPotWeight = (TextView) findViewById(R.id.textPotWeight);
        getPotWeight.setText("" + potWeight);
    }

    private void setupServingSize(int servingSize) {
        if (servingSize == 0) {
            return;
        }

        TextView getServingSize = (TextView) findViewById(R.id.textServingWeight);
        getServingSize.setText("" + servingSize);
    }

    private void setupFoodWeight() {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        EditText servingWeight = (EditText) findViewById(R.id.textWeightWithFood);
        int foodSize = Integer.parseInt(servingWeight.getText().toString().trim());
        TextView weightOfFood = (TextView) findViewById(R.id.textWeightOfFood);
        weightOfFood.setText("" + foodSize);
    }
}


