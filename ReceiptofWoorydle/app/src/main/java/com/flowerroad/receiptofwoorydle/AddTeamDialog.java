package com.flowerroad.receiptofwoorydle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by HYUNJI on 2018-01-25.
 */

public class AddTeamDialog extends AppCompatDialogFragment {
   private EditText editTextTeamname;
   private AddTeamDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Set Team Name")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String teamname = editTextTeamname.getText().toString();
                        listener.applyTexts(teamname);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        editTextTeamname=view.findViewById(R.id.edit_teamname);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (AddTeamDialogListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddTeamDialogListener");
        }
    }

    public interface AddTeamDialogListener {
        void applyTexts(String teamname);
    }
}
