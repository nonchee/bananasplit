package hu.ait.android.bananasplit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.bananasplit.R;
import hu.ait.android.bananasplit.data.Expense;

/**
 * Created by Nancy on 12/6/15.
 */
public class ExpenseSheetRecyclerAdapter extends RecyclerView.Adapter<ExpenseSheetRecyclerAdapter.ExpenseViewHolder>{

    private Context context;
    private List<Expense> expenseList;


    public ExpenseSheetRecyclerAdapter(Context context) {
        this.context = context;

        expenseList = new ArrayList<Expense>();
        Expense.getQuery(); //Exp.listAll(Todo.class);

    }




    @Override
    public void onBindViewHolder(ExpenseSheetRecyclerAdapter.ExpenseViewHolder holder, int position) {
        final Expense expense = expenseList.get(position);
        holder.expenseName.setText(expense.getExpenseName());
        holder.itemCost.setText("$" + expense.getCost());

        holder.payerNames.setText(expense.getPayersAsString());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }




    @Override
    public ExpenseSheetRecyclerAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View expenseRow = LayoutInflater.from(
                context).inflate(R.layout.expense, parent, false);
        return new ExpenseSheetRecyclerAdapter.ExpenseViewHolder(expenseRow);
    }

    //add more params in a sec
    public void addExpense(Expense expense) {
        expenseList.add(expense);
        notifyDataSetChanged();

    }


    //setting the fields of the expense row layout
    public class ExpenseViewHolder extends RecyclerView.ViewHolder {

        private final TextView expenseName;
        private final TextView payerNames;
        private final TextView itemCost;
        //private final CheckBox cbPaid;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            expenseName = (TextView) itemView.findViewById(R.id.itemName);
            payerNames = (TextView) itemView.findViewById(R.id.payerName);
            itemCost = (TextView) itemView.findViewById(R.id.itemCost);

        }
    }
}
