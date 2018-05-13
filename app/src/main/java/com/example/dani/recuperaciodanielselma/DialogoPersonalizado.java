package com.example.dani.recuperaciodanielselma;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Dani on 13/05/2018.
 */

public class DialogoPersonalizado extends DialogFragment {


    @NonNull

    public Dialog onCreateDialog(Bundle savedInstanceState, String id) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.vistadialogfragment,null)).setTitle("Estas seguro?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        Toast.makeText(getContext(), "Has clicado si", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Has clicado no", Toast.LENGTH_SHORT).show();

            }
        });
        final AlertDialog dialogo = builder.create();
        dialogo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positivo = dialogo.getButton(DialogInterface.BUTTON_POSITIVE);
                positivo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Llamamos al metodo borrar", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });
            }
        });


        return dialogo;
    }


}
