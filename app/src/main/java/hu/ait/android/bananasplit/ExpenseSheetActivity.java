package hu.ait.android.bananasplit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Map;

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

    @Bind(R.id.adventureName)
    TextView adventureName;

    @Bind(R.id.btnSeeCurrentUsers)
    Button seeUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_sheet);
        Intent intent = getIntent();
        Adventure adventurePassed = (Adventure) getIntent().getSerializableExtra("ADVENTURE");


        ButterKnife.bind(this);

        thisAdventure = adventurePassed;
        adventureName.setText(thisAdventure.getAdventureName());
        adapter = new ExpenseSheetRecyclerAdapter(this);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);


        for (Expense expense : thisAdventure.getExpenses()) {
            adapter.addExpense(expense);
        }
        rv.setAdapter(adapter);



    }

    @OnClick(R.id.addExpense)
    public void startAddExpenseFragment() {
        new AddExpenseFragment().show(getSupportFragmentManager(), AddExpenseFragment.TAG);
    }

    @Override
    public void onAddExpenseFragmentResult(final Expense expense) {

        expense.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    //update everyone based on how much they paid
                    thisAdventure.updatePayersFromStringUsernameList(expense.getPayerNames());

                    //update everyone based on how much they owe
                    thisAdventure.updateConsumersFromStringUsernameList(expense.getConsumerNames(), expense.getCostPerConsumer());

                    //add expense to list in this adventure
                    thisAdventure.addExpense(expense);

                    //add the expense to adapter to be viewed
                    adapter.addExpense(expense);

                } else {
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
    public void startShowSplitFragment() {
        new ShowSplitFragment().show(getSupportFragmentManager(), ShowSplitFragment.TAG);
    }
}
