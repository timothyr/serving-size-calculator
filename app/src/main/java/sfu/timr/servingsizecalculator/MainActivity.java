package sfu.timr.servingsizecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupFloatingAddPotButton(R.id.floatingAddPotButton);

        populateListView();
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

    //todo: listview of pots
    private void populateListView() {
        // Create a dynamic list of pots
        //todo:ArrayList<Pot>[] pots = new ArrayList<Pot>[];

        // Build adapter
        //todo: ArrayAdapter<Pot> potAdapter = new ArrayAdapter<Pot>(this, R.layout.potcollection, pots);

        //Configure the list view.
        //todo: ListView list = (ListView) findViewById(R.id.listViewMain);
        //todo: list.setAdapter(potAdapter);
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
