package hu.ait.android.bananasplit;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
import hu.ait.android.bananasplit.data.Adventure;


/**
 * Created by joshuapitkofsky on 12/11/15.
 */
public class AddAdventureFragment extends SupportBlurDialogFragment {

    public static final String TAG = "ADD_ADVENTURE_FRAGMENT";


    public interface AddAdventureInterface {
        void onAddAdventureFragmentResult(Adventure adventure);
    }

    private AddAdventureInterface addAdventureInterface;
    private EditText adventureName;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //catches the interface of thing
        try {
            addAdventureInterface = (AddAdventureInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddAdventureInterface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Add a new adventure");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_add_adventure, null);
        dialogBuilder.setView(dialogView);

        adventureName = (EditText) dialogView.findViewById(R.id.newAdventureName);


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

                            //create a new adventure
                            Adventure adventure = new Adventure();
                            adventure.setAdventureName(adventureName.getText().toString());

                            //go to AdventureSheetActivity to see how the activity's adventures are updated
                            addAdventureInterface.onAddAdventureFragmentResult(adventure);

                            dialog.dismiss();
                        }

                    }
                });

        AlertDialog addAdventureDialog = dialogBuilder.create();
        return addAdventureDialog;
    }

    //check for when a user doesn't have anything in one of the fields
    private boolean anyFieldsBlank() {

        return "".equals(adventureName.getText().toString());

    }

}
