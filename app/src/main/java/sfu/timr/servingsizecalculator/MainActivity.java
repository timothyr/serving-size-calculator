package sfu.timr.servingsizecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPot();
            }
        });

        // Wiring the Add a pot button.
        Button addPot = (Button) findViewById(R.id.addpot);
        addPot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Launch the Add Pot activity
                Intent addPotMenu = new Intent(MainActivity.this, EnterPot.class);
                startActivity(addPotMenu);
            }
        });

        populateListView();

    }

    private void addPot() {

    }

    // Default boilerplate that might come handy later
    // -----------------------------

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
}
