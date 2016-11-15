package com.vac.vmusic.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by vac on 16/11/15.
 *
 */
public class DialogUtils {
    public static void showDialog(Context context, String message, final OnDialogBtnClickListener listener){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("提示").setMessage(message)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        listener.onBtnCancel();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        listener.onBtnOk();
                    }
                }).create();
        alertDialog.show();
    }

    public interface OnDialogBtnClickListener{
        void onBtnOk();
        void onBtnCancel();
    }
}
