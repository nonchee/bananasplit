package hu.ait.android.bananasplit;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
import hu.ait.android.bananasplit.data.Adventure;
import hu.ait.android.bananasplit.data.Expense;

/**
 * Created by Nancy on 12/6/15.
 */
public class AddExpenseFragment extends SupportBlurDialogFragment {

    public static final String TAG = "ADD_EXPENSE_FRAGMENT";

    HashMap<AutoCompleteTextView, EditText> allPayers;
    HashMap<AutoCompleteTextView, CheckBox> allBuyers;

    public interface AddExpenseInterface {
        void onAddExpenseFragmentResult(Expense expense);
        Adventure getAdventure();
    }

    private AddExpenseInterface addExpenseInterface;
    private EditText expenseName;
    private EditText expenseCost;

    private LinearLayout addPayerRows;
    private AutoCompleteTextView expensePayer;
    private EditText payerAmount;
    private CheckBox userUsedThing;
    private Button addPayer;

    ArrayAdapter<String> userListAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //catches the interface of thing
        try {
            addExpenseInterface = (AddExpenseInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddExpenseInterface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Add a new expense");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_add_expense, null);
        dialogBuilder.setView(dialogView);

        expenseName = (EditText) dialogView.findViewById(R.id.newExpenseName);
        expenseCost = (EditText) dialogView.findViewById(R.id.newExpenseCost);
        userUsedThing = (CheckBox) dialogView.findViewById(R.id.cbUserUsed);


        allPayers = new HashMap<AutoCompleteTextView, EditText>();
        allBuyers = new HashMap<AutoCompleteTextView, CheckBox>();
        expensePayer = (AutoCompleteTextView) dialogView.findViewById(R.id.etExpensePayer);
        payerAmount = (EditText) dialogView.findViewById(R.id.etUserAmount);
        AutoCompleteTextView acThisUser = (AutoCompleteTextView) dialogView.findViewById(R.id.secretThisUserEditText);
        acThisUser.setText(ParseUser.getCurrentUser().getUsername());
        CheckBox thisUserUsed = (CheckBox) dialogView.findViewById(R.id.cbThisUserUsed);

        allPayers.put(acThisUser, (EditText) dialogView.findViewById(R.id.newExpenseCost));
        allPayers.put(expensePayer, payerAmount);
        allBuyers.put(expensePayer, userUsedThing);
        allBuyers.put(acThisUser, thisUserUsed);

        //this might need to be elsewhere
        //taking this out temporarily because it was crashing the app
        setUpAutoComplete(allPayers);

        addPayerRows = (LinearLayout) dialogView.findViewById(R.id.addPayerRows);

        addPayer = (Button) dialogView.findViewById(R.id.addPayer);
        addPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPayerRow();
            }
        });


        dialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        dialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!anyFieldsBlank()) {

                            //create a new expense
                            Expense expense = new Expense();
                            expense.setExpenseName(expenseName.getText().toString());
                            expense.setCostFromString(expenseCost.getText().toString());

                            //update map of payers
                            for (Map.Entry<AutoCompleteTextView, EditText> name : allPayers.entrySet()) {

                                expense.addPayerFromView(name.getKey().getText().toString(),
                                        name.getValue().getText().toString());
                            }

                            //update set of consumers from the buyers that were added
                            for (Map.Entry<AutoCompleteTextView, CheckBox> name : allBuyers.entrySet()) {
                                if (name.getValue().isChecked()) {
                                    expense.addConsumerFromView(name.getKey().getText().toString());
                                }
                            }

                            //go to ExpenseSheetActivity to see how the activity's adventure (one per activity) gets updated
                            addExpenseInterface.onAddExpenseFragmentResult(expense);

                            dialog.dismiss();
                        }

                    }
                });

        AlertDialog addExpenseDialog = dialogBuilder.create();
        return addExpenseDialog;
    }

    //check for when a user doesn't have anything in one of the fields
    private boolean anyFieldsBlank() {
        return "".equals(expenseName.getText().toString())
                || "".equals(expenseCost.getText().toString())
                || "".equals(expensePayer.getText().toString());
    }

    public void setUpAutoComplete(HashMap<AutoCompleteTextView, EditText> allPayers) {
        //set up autocomplete to include users that have already been entered before
        ArrayList<String> adventurerList = addExpenseInterface.getAdventure().getListOfAllAdventurers();

        userListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, adventurerList);

        expensePayer.setAdapter(userListAdapter);
    }

    public void addNewPayerRow() {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View newPayerRow = inflater.inflate(R.layout.person_expense_row, null);

        AutoCompleteTextView payerName = (AutoCompleteTextView) newPayerRow.findViewById(R.id.etExpensePayer);
        EditText payerAmount = (EditText) newPayerRow.findViewById(R.id.etUserAmount);
        CheckBox usedThing = (CheckBox) newPayerRow.findViewById(R.id.cbUserUsed);

        allPayers.put(payerName, payerAmount);
        allBuyers.put(payerName, usedThing);

        addPayerRows.addView(newPayerRow);
    }



}
