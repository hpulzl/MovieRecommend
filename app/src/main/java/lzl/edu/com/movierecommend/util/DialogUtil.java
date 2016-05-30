package lzl.edu.com.movierecommend.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by admin on 2016/4/9.
 * 提示框的工具类
 */
public class DialogUtil {
    private static DialogUtil dialogUtil;
    public interface OnDialogClickListener{
      void onDialogClick();
    }
    private OnDialogClickListener onDialogClickListener;
    private DialogUtil(){

    }
    public synchronized static DialogUtil DialogUtilInstance(){
        if(dialogUtil == null){
            dialogUtil = new DialogUtil();
        }
        return dialogUtil;
    }
    public void setOnDialogClickListener(OnDialogClickListener listener){
        this.onDialogClickListener = listener;
    }
   public void showDialog(Context cxt,String msg,boolean goHome){
       //创建一个AlertDialog.Builder对象
       AlertDialog.Builder builder = new AlertDialog.Builder(cxt).
               setMessage(msg).setCancelable(false);
       if(goHome){
           builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                 if(onDialogClickListener!=null){
                     onDialogClickListener.onDialogClick();
                 }
                    dialog.dismiss();
               }
           });
           builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
               }
           });
       }else{
           builder.setPositiveButton("确定",null);
       }
       builder.create().show();
   }
    //定义一个显示指定组件的对话框
    public void showDialog(Context cxt,View view){
        new AlertDialog.Builder(cxt).setView(view).setCancelable(false)
                .setPositiveButton("确定",null).create().show();
    }
    private void dimissDialog(){

    }
}
