package sfu.timr.servingsizecalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Calculates the serving of food based on pot weight, food weight and number of servings inputted
 */
public class CalculateServingActivity extends AppCompatActivity implements TextWatcher {

    private String potName;
    private int potWeight;

    // For use with calculating the serving size in a pot.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_pot);

        setTextChangedListeners();
        extractDataFromIntent();
        setupBackButton();
        setupDefaultText();
    }

    private void calculateServings() {
        // The TextViews to be set
        TextView weightOfFood = (TextView) findViewById(R.id.textWeightOfFood);
        TextView finalServingWeight = (TextView) findViewById(R.id.textServingWeight);

        // Get pot and food weight combined
        EditText servingWeight = (EditText) findViewById(R.id.textWeightWithFood);
        String servingWeightString = servingWeight.getText().toString().trim();
        int totalPotWeight;
        try {
            totalPotWeight = Integer.parseInt(servingWeightString);
        } catch (NumberFormatException e) {
            weightOfFood.setText("" + 0);
            finalServingWeight.setText("" + 0);
            return;
        }

        int foodWeight = totalPotWeight - potWeight;

        // Check for negative number
        if(foodWeight <= 0) {
            weightOfFood.setText("" + 0);
            finalServingWeight.setText("" + 0);
            return;
        }

        // Set weight text
        weightOfFood.setText("" + foodWeight);

        // Get number of servings
        EditText numberServingsEditText = (EditText) findViewById(R.id.textNumberServings);
        String numberServingsString = numberServingsEditText.getText().toString().trim();
        int numberServings;
        try {
            numberServings = Integer.parseInt(numberServingsString);
        } catch (NumberFormatException e) {
            finalServingWeight.setText("" + 0);
            return;
        }

        // Check for negative number (this shouldnt be possible but we check anyway)
        if(numberServings <= 0) {
            finalServingWeight.setText("" + 0);
        }

        int calculatedServingWeight = foodWeight * numberServings;

        // Set serving weight text
        finalServingWeight.setText("" + calculatedServingWeight);
    }

    private void setTextChangedListeners() {
        EditText servingWeight = (EditText) findViewById(R.id.textWeightWithFood);
        servingWeight.addTextChangedListener(this);

        EditText servingNumber = (EditText) findViewById(R.id.textNumberServings);
        servingNumber.addTextChangedListener(this);
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        potName = intent.getStringExtra(getString(R.string.pot_name));
        potWeight = intent.getIntExtra(getString(R.string.pot_weight), 0);
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

    public static Intent makeIntent(Context context, Pot returnPot, String returnPotNameString, String returnPotWeightString) {
        Intent intent = new Intent(context, CalculateServingActivity.class);
        intent.putExtra(returnPotNameString, returnPot.getName());
        intent.putExtra(returnPotWeightString, returnPot.getWeightInG());
        return intent;
    }

    private void setupDefaultText() {
        TextView getPotName = (TextView) findViewById(R.id.textPotName);
        getPotName.setText(potName);

        TextView getPotWeight = (TextView) findViewById(R.id.textPotWeight);
        getPotWeight.setText("" + potWeight);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        calculateServings();
    }
}


