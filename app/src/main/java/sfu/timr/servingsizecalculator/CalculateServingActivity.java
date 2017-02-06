package sfu.timr.servingsizecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CalculateServingActivity extends AppCompatActivity {

    // For use with calculating the serving size in a pot.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_pot);

        setupBackButton();
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
}


