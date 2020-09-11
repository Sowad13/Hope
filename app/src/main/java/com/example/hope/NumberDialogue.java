package com.example.hope;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NumberDialogue extends AppCompatDialogFragment {

    NumberPicker minPicker, secPicker;

    int minVal,secVal;
    private NumberDialogueListener numberDialogueListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();


        View v = inflater.inflate(R.layout.number_dialogue, null);


        minPicker = v.findViewById(R.id.minutePicker);
        secPicker = v.findViewById(R.id.secondPicker);

        minPicker.setMinValue(0);
        minPicker.setMaxValue(59);
        secPicker.setMinValue(0);
        secPicker.setMaxValue(59);

        minPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                minVal = newVal;

            }
        });

        secPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                secVal = newVal;

            }
        });

        builder.setView(v).setTitle("Pick Your Time")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        numberDialogueListener.applyTexts(minVal,secVal);

                    }
                });


        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            numberDialogueListener = (NumberDialogueListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface NumberDialogueListener {
        void applyTexts(int min, int sec);
    }
}
