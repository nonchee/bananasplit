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
import java.util.Map;

/**
 * Created by Nancy on 12/6/15.
 */

@ParseClassName("Expense")
public class Expense extends ParseObject {

    public String expenseName;
    private float itemCost;

    private HashSet<String> consumerNames;
    private HashMap<String, Float> payerNames;
    private ParseUser payer;



    //required blank constructor
    public Expense() {
        consumerNames = new HashSet<String>();
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



    public HashMap<String, Float> getPayerNames() {
        return payerNames;
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

    public void addConsumerFromView(String consumerName) {
        consumerNames.add(consumerName);
    }

    public HashSet<String> getConsumerNames() {
        return consumerNames;
    }

    public float getCostPerConsumer() {

        float numConsumers = consumerNames.size();
        return getTotalCost()/numConsumers;
    }

    //adds up all the people who paid
    public float getTotalCost() {
        float totalCost = 0;
        for (Map.Entry<String, Float> payerEntry : payerNames.entrySet()) {
            totalCost += payerEntry.getValue();
        }
        return totalCost;
    }
    public String getPayersAsString() {

        StringBuilder sb = new StringBuilder();
        for (String payerName : payerNames.keySet()) {
            sb.append(payerName  + " ");
        }
        return sb.toString();
    }
}
