package sfu.timr.servingsizecalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADDPOT = 1000;
    private android.view.ActionMode actionMode;
    // Pot collection array
    private PotCollection pots = new PotCollection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupFloatingAddPotButton();

        populatePotCollection();

        populateListView();
        registerClickCallback();
    }

    private void setupFloatingAddPotButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingAddPotButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch the Add Pot activity
                Intent addPotIntent = EnterPotActivity.makeIntent(MainActivity.this);
                startActivityForResult(addPotIntent, REQUEST_CODE_ADDPOT);
            }
        });
    }

    private void populatePotCollection() {
        for(int i = 0; i < 3; i++){
            Pot pot = new Pot("garbage", (i+1) * 1000);
            pots.addPot(pot);
        }
    }

    private void populateListView() {
        // Get adapter from PotCollection
        ArrayAdapter<Pot> adapter = pots.getArrayAdapter(MainActivity.this);

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
                view.setSelected(false);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                String message = "LONG PRESS ON " + textView.getText() + "!";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                view.setSelected(true);

                showActionBar();

                return true;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ADDPOT:
                if(resultCode == Activity.RESULT_OK) {
                    // Get the pot data
                    String potName = data.getStringExtra("POTNAME");
                    Integer potWeight = data.getIntExtra("POTWEIGHT", 0);

                    // Create a pot based on data
                    Pot pot = new Pot(potName, potWeight);

                    // Add pot to PotCollection
                    pots.addPot(pot);
                    populateListView();
                }
        }
    }

    private class ActionModeCallback implements android.view.ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {
            // Inflate action menu over top of default actionbar
            actionMode.getMenuInflater().inflate(R.menu.action_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(android.view.ActionMode actionMode) {

        }
    }

    private void showActionBar() {
        actionMode = startActionMode(new ActionModeCallback());
    }

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

        /*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

}
