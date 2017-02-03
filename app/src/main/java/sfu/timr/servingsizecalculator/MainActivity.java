package sfu.timr.servingsizecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

// Pot collection array


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupFloatingAddPotButton(R.id.floatingAddPotButton);

        populateListView();
        registerClickCallback();
    }

    private void setupFloatingAddPotButton(int buttonId) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(buttonId);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the Add Pot activity
                Intent addPotMenu = new Intent(MainActivity.this, EnterPotActivity.class);
                startActivity(addPotMenu);
            }
        });
    }

    private void populateListView() {
        // Create a dynamic list of pots
        PotCollection pots = new PotCollection();

        for(int i = 0; i < 3; i++){
            Pot pot = new Pot("garbage", i);
            pots.addPot(pot);
        }

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.pot_item,
                pots.getPotDescriptions());

        //Configure the list view.
        ListView list = (ListView) findViewById(R.id.potListView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.potListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                String message = "You tapped on " + textView.getText() + ". Why would you do that?";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Default boilerplate that might come handy later
    // -----------------------------

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
