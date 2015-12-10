package hu.ait.android.bananasplit.data;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Nancy on 12/6/15.
 */

@ParseClassName("Expense")
public class Expense extends ParseObject {

    public String expenseName;
    private float itemCost;

    private HashSet<String> buyerNames;
    private HashMap<String, Float> payerNames;
    private ParseUser payer;


    //required blank constructor
    public Expense() {
        buyerNames = new HashSet<String>();
        payerNames = new HashMap<String, Float>();

    }

    public static ParseQuery<Expense> getQuery() {
        return ParseQuery.getQuery(Expense.class);
    }

    public void setExpenseName(String name) {
        put("expenseName", name);
        expenseName = name;
    }

    public void setCost(float cost) {
        put("cost", cost);
        this.itemCost = cost;
    }

    public void setUser(ParseUser user) {
        put("payer", user);
        payer = user;
    }


    public float getCost() {
        return itemCost;
    }



    public List<String> getPayerNames() {
        List<String> payerNamesList = new ArrayList<String>();
        for (String name : payerNames.keySet()) {
            Log.d("payers from expense", name);
            payerNamesList.add(name);
        }
        return payerNamesList;
    }

    public String getExpenseName() {
        return expenseName;
    }

    //gets the cost from the string value of the edit text
    public void setCostFromString(String costFromString) {
        if (costFromString.equals("")) {
            itemCost = 0;
        }
        else {
            this.itemCost = Float.parseFloat(costFromString);

        }

        put("cost", itemCost);

    }

    public void addPayerString(String payerNameString, String payerAmount) {

        payerNames.put(payerNameString, Float.parseFloat(payerAmount));
        put("payers", payerNames);

    }

    public void addPayerFromView(String payerNameString, String payerAmount) {
        payerNames.put(payerNameString, Float.parseFloat(payerAmount));
        put("payers", payerNames);

    }

    public void addPayers(HashMap<String, Float> payers) {
        payerNames.putAll(payers);

    }
}
