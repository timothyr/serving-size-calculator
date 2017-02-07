package sfu.timr.servingsizecalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADDPOT  = 1000;
    private static final int REQUEST_CODE_EDITPOT = 2000;
    private static final int REQUEST_CODE_SERVING = 3000;

    private android.view.ActionMode actionMode;
    private Pot selectedPot;
    // Pot collection array
    private PotCollection pots = new PotCollection();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadSaveData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupFloatingAddPotButton();
        //setupDeletePotButton();

        //populatePotCollection();

        clearChoicesAndUpdateListView();
        writeSaveData();
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

    private void clearChoicesAndUpdateListView() {
        //TODO destroy action bar
        ArrayAdapter<Pot> adapter = pots.getArrayAdapter(MainActivity.this);
        ListView list = (ListView) findViewById(R.id.potListView);
        list.clearChoices();
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.potListView);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(actionMode != null) {
                    actionMode.finish();
                    selectedPot = null;
                    clearChoicesAndUpdateListView();
                    return;
                }
                clearChoicesAndUpdateListView();

                // Code to launch the calculator activity from clicking.
                // todo: refactor to function later, or clean this one up.
                Intent calculateServingIntent = CalculateServingActivity.makeIntent(MainActivity.this,
                        pots.getPot(i));
                startActivityForResult(calculateServingIntent, REQUEST_CODE_SERVING);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPot = pots.getPot(i);
                // prevent overwrite of actionmode selection
                if(actionMode != null) {
                    actionMode.setTitle(selectedPot.getName());
                    return true;
                }

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
                    writeSaveData();

                }
                break;
            case REQUEST_CODE_EDITPOT:
                if(resultCode == Activity.RESULT_OK) {
                    int potIndex = pots.getPotIndex(selectedPot);
                    if(potIndex == -1) {
                        break;
                    }
                    // Get the pot data
                    String potName = data.getStringExtra("POTNAME");
                    Integer potWeight = data.getIntExtra("POTWEIGHT", 0);

                    // Create a pot based on data
                    Pot pot = new Pot(potName, potWeight);

                    // Change pot in PotCollection
                    pots.changePot(pot, potIndex);
                    writeSaveData();
                }
                break;
        }
        clearChoicesAndUpdateListView();
        if(actionMode != null) {
            actionMode.finish();
        }
    }

    private void editSelectedPot() {
        Intent EditPotIntent = EnterPotActivity.makeIntent(
                MainActivity.this,
                selectedPot);
        startActivityForResult(EditPotIntent, REQUEST_CODE_EDITPOT);
    }

    private void deleteSelectedPot() {
        pots.removePot(selectedPot);
        writeSaveData();
        clearChoicesAndUpdateListView();
        if(actionMode != null) {
            actionMode.finish();
        }
    }

    private class ActionModeCallback implements android.view.ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {
            // Inflate action menu over top of default actionbar
            actionMode.setTitle(selectedPot.getName());
            actionMode.getMenuInflater().inflate(R.menu.action_menu, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem menuItem) {
            // Handle item selection
            switch (menuItem.getItemId()) {
                case R.id.action_edit:
                    editSelectedPot();
                    return true;
                case R.id.action_delete:
                    deleteSelectedPot();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(android.view.ActionMode mode) {
            clearChoicesAndUpdateListView();
            selectedPot = null;
            actionMode = null;
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


    private void loadSaveData() {
        SharedPreferences prefs = getSharedPreferences("Pot Collection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();

        int i = 0;
        while (prefs.getInt("Pot Weight" + i, -1) != -1) {
            Pot pot = new Pot(prefs.getString("Pot Name" + i, ""), prefs.getInt("Pot Weight" + i, 0));
            pots.addPot(pot);
            i++;
        }
    }

    private void writeSaveData() {
        SharedPreferences prefs = getSharedPreferences("Pot Collection", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        for (int i = 0; i < pots.getPots().size(); i++) {
            Pot pot = pots.getPot(i);
            editor.putString("Pot Name" + i, pot.getName());
            editor.putInt("Pot Weight" + i, pot.getWeightInG());
        }
        editor.commit();
    }
}
