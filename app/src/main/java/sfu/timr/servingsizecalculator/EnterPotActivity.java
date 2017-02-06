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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pot);

        setupAddPotButton();
        setupCancelPotButton();
    }

    private void setupAddPotButton() {
        Button addButton = (Button) findViewById(R.id.enter_pot_add_button);

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
                intent.putExtra("POTNAME", potName);
                intent.putExtra("POTWEIGHT", potWeight);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
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

}
