package sfu.timr.servingsizecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage a collection of pots.
 */
public class PotCollection {
    private List<Pot> pots = new ArrayList<>();

    public void addPot(Pot pot) {
        pots.add(0, pot);
    }

    public void changePot(Pot pot, int indexOfPotEditing) {
        validateIndexWithException(indexOfPotEditing);
        pots.remove(indexOfPotEditing);
        pots.add(indexOfPotEditing, pot);
    }

    public int countPots() {
        return pots.size();
    }
    public Pot getPot(int index) {
        validateIndexWithException(index);
        return pots.get(index);
    }

    public List<Pot> getPots() {
        return this.pots;
    }

    // Useful for integrating with an ArrayAdapter
    public String[] getPotDescriptions() {
        String[] descriptions = new String[countPots()];
        for (int i = 0; i < countPots(); i++) {
            Pot pot = getPot(i);
            descriptions[i] = pot.getName() + " - " + pot.getWeightInG() + "g";
        }
        return descriptions;
    }

    private void validateIndexWithException(int index) {
        if (index < 0 || index >= countPots()) {
            throw new IllegalArgumentException();
        }

    }

    public ArrayAdapter<Pot> getArrayAdapter(Context context) {
        PotListAdapter adapter = new PotListAdapter(context);
        return adapter;
    }

    private class PotListAdapter extends ArrayAdapter<Pot> {

        // Must pass in context e.g. MainActivity.this
        PotListAdapter(Context context) {
            super(context, R.layout.pot_item, pots);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            // Ensure we have a view (could have been passed a null)
            View itemView = convertView;
            if(itemView == null) {
                itemView = LayoutInflater.from(getContext()).inflate(R.layout.pot_item, parent, false);
            }

            // Get the current pot
            Pot currentPot = pots.get(position);

            // Fill the TextView
            TextView description = (TextView) itemView.findViewById(R.id.item_description);
            description.setText(currentPot.getName() + " - " + currentPot.getWeightInG() + "g");

            return itemView;
        }
    }
}