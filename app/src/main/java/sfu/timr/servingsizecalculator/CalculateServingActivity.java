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

import java.text.DecimalFormat;
import java.text.NumberFormat;

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



//        TextWatcher watcher = new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//                EditText numberServings = (EditText) findViewById(R.id.textNumberServings);
//                TextView weightServings = (TextView) findViewById(R.id.textServingWeight);
//                NumberFormat format = new DecimalFormat("0.#");
//                TextView getServingSize = (TextView) findViewById(R.id.textServingWeight);
//                if (servingSize.getText().toString() == "0" || numberServings.getText().length() == 0) {
//                    //getServingSize.setText("0");
//                    return;
//                }
//                //getServingSize.setText(servingSize.getText().toString().trim());
//            }
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//        };

        EditText servingSize = (EditText) findViewById(R.id.textNumberServings);
        servingSize.addTextChangedListener(this); // change to watcher

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
        getPotWeight.setText(Integer.toString(potWeight));
    }

    private void setupServingSize(int servingSize) {

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
        TextView weightOfFood = (TextView) findViewById(R.id.textWeightOfFood);
        NumberFormat format = new DecimalFormat("0.#");
        if (servingWeight.getText().length() == 0) {
            weightOfFood.setText("" + format.format(potWeight));
            return;
        }

        double foodSize = Double.parseDouble(servingWeight.getText().toString().trim());
        foodSize += potWeight;

        weightOfFood.setText("" + format.format(foodSize));
    }
}


