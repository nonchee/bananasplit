package hu.ait.android.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.bananasplit.AddExpenseFragment.AddExpenseInterface;
import hu.ait.android.bananasplit.adapter.ExpenseSheetRecyclerAdapter;
import hu.ait.android.bananasplit.data.Adventure;
import hu.ait.android.bananasplit.data.Expense;

/**
 * Created by Nancy on 12/6/15.
 */
public class ExpenseSheetActivity extends AppCompatActivity implements AddExpenseInterface {

    private ExpenseSheetRecyclerAdapter adapter;
    private Adventure thisAdventure;

    @Bind(R.id.addExpense)
    FloatingActionButton addExpense;

    @Bind(R.id.btnSeeCurrentUsers)
    Button seeUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_sheet);

        ButterKnife.bind(this);




        thisAdventure = new Adventure();

        adapter = new ExpenseSheetRecyclerAdapter(this);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);

    }

    @OnClick(R.id.addExpense)
    public void addNewExpense() {
        new AddExpenseFragment().show(getSupportFragmentManager(), AddExpenseFragment.TAG);
    }


    //gets called once you've clicked "OK" on the AddExpenseFragment
    @Override
    public void onAddExpenseFragmentResult(final Expense expense) {

        Log.d("add new users!", "was expense null "  + String.valueOf(expense == null));
        Log.d("add new users!", "expense size: "  + String.valueOf(expense.getPayerNames().size())); // == null));

        expense.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //(1) get the payer names from the expense
                    thisAdventure.addAnyNewUsersFromStringUsernameList(expense.getPayerNames());

                    //(2) add the expense to adapter to be viewed
                    adapter.addExpense(expense);
                } else {

                    Log.d("add new users!", "oh merp lol");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Adventure getAdventure() {
        return thisAdventure;

    }

    @OnClick(R.id.btnSeeCurrentUsers)
    public void calculateSplit() {


        for (String string : thisAdventure.getUserStrings()) {
            Toast.makeText(this, "errybody owes " + string + "a hunnid dollars", Toast.LENGTH_SHORT).show();
        }

        //pseudocode here
    }
}
