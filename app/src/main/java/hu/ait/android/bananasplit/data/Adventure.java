package hu.ait.android.bananasplit.data;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 * Created by Nancy on 12/8/15.
 */

@ParseClassName("Adventure")
public class Adventure extends ParseObject implements Serializable {

    String adventureName;
    //names of all the users
    HashMap<String, Float> adventurerNames;
    ArrayList<Expense> expensesList;

    //required blank constructor
    public Adventure() {
        expensesList = new ArrayList<Expense>();
        adventurerNames = new HashMap<String, Float>();
        adventurerNames.put(ParseUser.getCurrentUser().getUsername(), 0.0f);
    }

    public String getAdventureName() {
        return getString("name");
    }

    public void setAdventureName(String adventureName) {
        put("name", adventureName);
        this.adventureName = adventureName;
    }

    public static ParseQuery<Adventure> getQuery() {
        return ParseQuery.getQuery(Adventure.class);
    }

    public void updatePayersFromStringUsernameList(HashMap<String, Float> payernames) {

        for (String payerName : payernames.keySet()) {
            if (!adventurerNames.keySet().contains(payerName)) {
                adventurerNames.put(payerName, 0.0f);
            }

            adventurerNames.put(payerName, adventurerNames.get(payerName) + payernames.get(payerName));
            //Log.d("updateadventure!", payerName + " " + adventurerNames.get(payerName));
        }
    }

    public void updateConsumersFromStringUsernameList(HashSet<String> consumerNames, float oweAmount) {

        for (String consumerName : consumerNames) {
            if (!adventurerNames.keySet().contains(consumerName)) {
                adventurerNames.put(consumerName, 0.0f);
            }

            adventurerNames.put(consumerName, adventurerNames.get(consumerName) - oweAmount);
            //Log.d("updateadventure with " + oweAmount, consumerName + " " + adventurerNames.get(consumerName));
        }
    }

    public ArrayList<String> getListOfAllAdventurers() {
        ArrayList<String> adventurerList = new ArrayList<String>();

        for (String personName : adventurerNames.keySet()) {
            adventurerList.add(personName);
        }

        return adventurerList;
    }

    public HashMap<String, Float> getMapOfAllAdventurers() {
        return adventurerNames;
    }

    public void addExpense(Expense expense) {
        expensesList.add(expense);
    }


    public ArrayList<Expense> getExpenses() {
        return expensesList;
    }
}
