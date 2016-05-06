package net.iyouyu.schedule;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by jesse on 2015/4/17.
 */
public class remind extends Activity {
    //private Context context = null;
    private Button btn=null;
    private AlarmManager alarmManager1=null;
    private AlarmManager alarmManager2=null;
    private AlarmManager alarmManager3=null;
    private AlarmManager alarmManager4=null;
    private AlarmManager alarmManager5=null;
    private AlarmManager alarmManager6=null;

    Calendar cal= Calendar.getInstance();
    final int DIALOG_TIME = 0; //设置对话框id



    //private AlarmManager am = (AlarmManager) Context.getSystemService(Context.ALARM_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.clock);

        alarmManager1=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager2=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager3=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager4=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager5=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager6=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Calendar c=Calendar.getInstance();//获取日期对象
        c.setTimeInMillis(System.currentTimeMillis()); //设置Calendar对象
        c.set(Calendar.HOUR, 11); //设置闹钟小时数
        c.set(Calendar.MINUTE, 01); //设置闹钟的分钟数
        c.set(Calendar.SECOND, 0); //设置闹钟的秒数
        c.set(Calendar.MILLISECOND, 0); //设置闹钟的毫秒数
        //btn=(Button)findViewById(R.id.btn);
        Intent intent = new Intent(remind.this, AlarmReceiver.class); //创建Intent对象
        PendingIntent pi = PendingIntent.getBroadcast(remind.this, 0, intent, 0); //创建PendingIntent
        alarmManager1.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi); //设置闹钟

        //alarmManager1.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi); //设置闹钟，当前时间就唤醒

        /*btn.setOnClickListener(new android.view.View.OnClickListener()
        {
            @Override
            public void onClick(android.view.View v) {
            //showDialog(DIALOG_TIME);//显示时间选择对话框
        }
        });*/
        finish();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog=null;
        switch (id) {
            case DIALOG_TIME:
                dialog=new TimePickerDialog(
                        this,
                        new TimePickerDialog.OnTimeSetListener(){
                            public void onTimeSet(TimePicker timePicker, int hourOfDay,int minute) {
                                Calendar c=Calendar.getInstance();//获取日期对象
                                c.setTimeInMillis(System.currentTimeMillis()); //设置Calendar对象
                                c.set(Calendar.HOUR, hourOfDay); //设置闹钟小时数
                                c.set(Calendar.MINUTE, minute); //设置闹钟的分钟数
                                c.set(Calendar.SECOND, 0); //设置闹钟的秒数
                                c.set(Calendar.MILLISECOND, 0); //设置闹钟的毫秒数
                                Intent intent = new Intent(remind.this, AlarmReceiver.class); //创建Intent对象
                                PendingIntent pi = PendingIntent.getBroadcast(remind.this, 0, intent, 0); //创建PendingIntent
                                alarmManager1.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi); //设置闹钟
                                //alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi); //设置闹钟，当前时间就唤醒
                                Toast.makeText(remind.this, "闹钟设置成功", Toast.LENGTH_LONG).show();//提示用户
                            }
                        },
                         cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        false);

                break;
        }
        return dialog;
    }



}
