package com.vac.vmusic.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.vac.vmusic.R;

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
                        listener.onBtnOk("");
                    }
                }).create();
        alertDialog.show();
    }


    public static void showEditTextDialog(Context context,String title,String hint,final OnDialogBtnClickListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit,null,false);
        final TextInputEditText editText = (TextInputEditText) view.findViewById(R.id.dialog_edit_text);
        editText.setTextColor(HomeColorManager.getHomeColorManager().getCurrentColor());
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);

        editText.setHint(hint);
        editText.setSingleLine();
        editText.setMaxEms(20);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title).setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        listener.onBtnCancel();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (editText.getText().toString().trim().isEmpty()){
                            return;
                        }
                        dialogInterface.dismiss();
                        listener.onBtnOk(editText.getText().toString().trim());
                    }
                }).create();
        alertDialog.show();
    }


    public interface OnDialogBtnClickListener{
        void onBtnOk(String message);
        void onBtnCancel();
    }


}
