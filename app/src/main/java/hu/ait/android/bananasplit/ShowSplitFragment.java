package hu.ait.android.bananasplit;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParsePush;

import java.util.HashMap;
import java.util.List;


//@JOSH MAKE THIS PRETTY PLEASE
//right now this class only shows textviews based on who owes what
public class ShowSplitFragment extends DialogFragment {
    public static final String TAG = "SHOW_EXPENSE_FRAGMENT";
    private AddExpenseFragment.AddExpenseInterface addExpenseInterface;
    private LinearLayout allUsersLinearLayout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //the only reason this eneds to implement the AddExpenseInterface is because I wanted the
        //activity to be able to implement getAdventure()
        //this fragment doesn't use the OnExpenseFragmentResult() method of the interface at all (:
        try {
            addExpenseInterface = (AddExpenseFragment.AddExpenseInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("need to be able to call getAdventure() on " + activity.toString());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Add a new expense");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_show_splits, null);
        dialogBuilder.setView(dialogView);

        allUsersLinearLayout = (LinearLayout) dialogView.findViewById(R.id.allUsersLinearLayout);
        listUsers();

        dialogBuilder.setNegativeButton("KAY COOL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        dialogBuilder.setPositiveButton("TEXT ERRYBODY",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ParsePush push = new ParsePush();

                        dialog.dismiss();
                    }
                });

        AlertDialog showSplitDialog = dialogBuilder.create();
        return showSplitDialog;
    }

    private void listUsers() {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        //needed to get a list to be able to loop
        List<String> listOfNames = addExpenseInterface.getAdventure().getListOfAllAdventurers();

        //this is the hashmap that gets updated every time an expense is added
        HashMap<String, Float> howMuchEachPersonOwes = addExpenseInterface.getAdventure().getMapOfAllAdventurers();

        for (String personName : listOfNames) {
            //create a new row out of each person in the hashmap and set the textviews
            View newPayerRow = inflater.inflate(R.layout.person_result_row, null);
            float howMuchOwed = howMuchEachPersonOwes.get(personName);

            TextView tvPersonName = (TextView) newPayerRow.findViewById(R.id.tvUserName);
            tvPersonName.setText(personName);

            TextView owedOrIsOwed = (TextView)newPayerRow.findViewById(R.id.tvOwedOrIsOwed);

            if (howMuchOwed < 0) {
                owedOrIsOwed.setText(" owes ");
                TextView payerAmount = (TextView) newPayerRow.findViewById(R.id.tvOweAmount);
                payerAmount.setTextColor(Color.RED);
                payerAmount.setText("$" + String.valueOf(howMuchOwed));
            }
            else if (howMuchOwed > 0){
                owedOrIsOwed.setText(" gets ");
                TextView payerAmount = (TextView) newPayerRow.findViewById(R.id.tvOweAmount);
                payerAmount.setTextColor(Color.GREEN);
                payerAmount.setText("$" + String.valueOf(howMuchOwed));
            }
            else if (howMuchOwed == 0) {
                owedOrIsOwed.setText("DOES NOT HAVE TO DO ANYTHING WOOO DEBT FREE.");
            }


            allUsersLinearLayout.addView(newPayerRow);

        }
    }

}
