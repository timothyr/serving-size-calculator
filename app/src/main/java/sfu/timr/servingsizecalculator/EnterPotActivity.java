package sfu.timr.servingsizecalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPotActivity extends AppCompatActivity {

    private String initialName;
    private int initialPotWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pot);

        extractDataFromIntent();
        setupCancelPotButton();
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        if(!intent.hasExtra(getString(R.string.pot_name))) {
            setupAddPotButton();
        }
        initialName = intent.getStringExtra(getString(R.string.pot_name));
        initialPotWeight = intent.getIntExtra(getString(R.string.pot_weight), -1);
        setupAddPotButton(initialName, initialPotWeight);
    }

    private void setupAddPotButton(String initialName, int initialPotWeight) {
        Button addButton = (Button) findViewById(R.id.enter_pot_add_button);

        if(initialName != null || initialPotWeight != -1) {
            if(initialName != null) {
                EditText potNameEditText = (EditText) findViewById(R.id.pot_name_entry);
                potNameEditText.setText(initialName);
            }

            if(initialPotWeight != -1) {
                EditText potWeightEditText = (EditText) findViewById(R.id.pot_weight_entry);
                potWeightEditText.setText("" + initialPotWeight);
            }

            addButton.setText("EDIT");
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Extract data from the UI
                EditText potNameEditText = (EditText) findViewById(R.id.pot_name_entry);
                String potName = potNameEditText.getText().toString().trim();

                EditText potWeightEditText = (EditText) findViewById(R.id.pot_weight_entry);
                String potWeightText = potWeightEditText.getText().toString().trim();

                try {
                    if (potNameEditText.getText().length() == 0) {
                        throw new NullPointerException("Invalid Pot Name Size");
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(EnterPotActivity.this, "Pot Name must be at least 1 character", Toast.LENGTH_SHORT).show();
                    return;
                }

                int potWeight;
                try {
                    potWeight = Integer.parseInt(potWeightText);
                } catch (NumberFormatException e) {
                    // TODO message on EditText showing error
                    Toast.makeText(EnterPotActivity.this, "Fill out the form correctly", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Pass data back
                Intent intent = new Intent();
                intent.putExtra(getString(R.string.pot_name), potName);
                intent.putExtra(getString(R.string.pot_weight), potWeight);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setupAddPotButton() {
        setupAddPotButton(null, -1);
    }

    private void setupCancelPotButton() {
        Button cancelButton = (Button) findViewById(R.id.enter_pot_cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, EnterPotActivity.class);
    }

    public static Intent makeIntent(Context context, Pot returnPot, String returnPotNameString, String returnPotWeightString) {
        Intent intent = new Intent(context, EnterPotActivity.class);
        intent.putExtra(returnPotNameString, returnPot.getName());
        intent.putExtra(returnPotWeightString, returnPot.getWeightInG());
        return intent;
    }

}
