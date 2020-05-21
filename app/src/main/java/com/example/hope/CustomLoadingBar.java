package com.example.hope;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class CustomLoadingBar {

   private Activity myActivity;
   private AlertDialog dialog;

    public CustomLoadingBar(Activity myActivity) {
        this.myActivity = myActivity;
    }


    void StartLoadingDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);

        LayoutInflater inflater = myActivity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_lodingbar,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    void DismissLoadingDialog()
    {
        dialog.dismiss();
    }
}
