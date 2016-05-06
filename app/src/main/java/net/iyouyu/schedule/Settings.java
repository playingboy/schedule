package net.iyouyu.schedule;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

//继承PreferenceActivity，并实现OnPreferenceChangeListener和OnPreferenceClickListener监听接口
public class Settings extends PreferenceActivity implements OnPreferenceClickListener,
        OnPreferenceChangeListener{
    //定义相关变量
    MySQLiteOpenHelper myOpenHelper;
    SQLiteDatabase  mysql ; // 实例数据库
    String updateFrequencyKey;
    String strclassno;
    String strclassweekStart;
    String strclassweekEnd;
    String strclassdetail;
    String strclassname;
    String strclassaddress;

    int day=1,no=1,startweek=1,endweek=1;

    ListPreference updateFrequencyListPref;
    ListPreference classno;
    ListPreference classweekStart;
    ListPreference classweekEnd;
    PreferenceCategory classdetail;
    EditTextPreference classname;
    EditTextPreference classaddress;
    //EditTextPreference classweekStart;
    //EditTextPreference classweekEnd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myOpenHelper = new MySQLiteOpenHelper(this);

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            mysql = SQLiteDatabase.openOrCreateDatabase(FileDB.f, null);
        else
            mysql = myOpenHelper.getWritableDatabase();
        myOpenHelper.onCreate(mysql);
        //从xml文件中添加Preference项
        addPreferencesFromResource(R.xml.preferences);
        //获取各个Preference
        updateFrequencyKey = getResources().getString(R.string.auto_update_frequency_key);
        strclassno =  getResources().getString(R.string.class_no_key);
        strclassweekStart = getResources().getString(R.string.class_start_week_key);
        strclassweekEnd = getResources().getString(R.string.class_end_week_key);
        strclassdetail = getResources().getString(R.string.classdetail_key);
        strclassname = getResources().getString(R.string.class_name);
        strclassaddress = getResources().getString(R.string.class_address);


        updateFrequencyListPref = (ListPreference)findPreference(updateFrequencyKey);
        classno =(ListPreference)findPreference(strclassno);
        classweekStart=(ListPreference)findPreference(strclassweekStart);
        classweekEnd=(ListPreference)findPreference(strclassweekEnd);
        classdetail=(PreferenceCategory)findPreference(strclassdetail);
        classname=(EditTextPreference)findPreference(strclassname);
        classaddress=(EditTextPreference)findPreference(strclassaddress);
        //为各个Preference注册监听接口    
        updateFrequencyListPref.setOnPreferenceClickListener(this);
        updateFrequencyListPref.setOnPreferenceChangeListener(this);
        classno.setOnPreferenceChangeListener(this);
        classno.setOnPreferenceClickListener(this);
        classname.setOnPreferenceChangeListener(this);
        classname.setOnPreferenceClickListener(this);
        classaddress.setOnPreferenceChangeListener(this);
        classaddress.setOnPreferenceClickListener(this);
        classweekStart.setOnPreferenceChangeListener(this);
        classweekStart.setOnPreferenceClickListener(this);
        classweekEnd.setOnPreferenceChangeListener(this);
        classweekEnd.setOnPreferenceClickListener(this);
    }
    @Override
    public boolean onPreferenceClick(Preference preference) {
        // TODO Auto-generated method stub
        Log.v("Key_SystemSetting", preference.getKey());
        //判断是哪个Preference被点击了
        if(preference.getKey().equals(updateFrequencyKey))
        {
            Log.v("SystemSetting", "list preference is clicked");
        }
        else
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // TODO Auto-generated method stub
        Log.v("Key_SystemSetting", preference.getKey());
        //判断是哪个Preference改变了
        if(preference.getKey().equals(updateFrequencyKey))
        {
            if(((String) newValue).trim().equals("星期一"))day=1;
            else if(((String) newValue).trim().equals("星期二"))day=2;
            else if(((String) newValue).trim().equals("星期三"))day=3;
            else if(((String) newValue).trim().equals("星期四"))day=4;
            else if(((String) newValue).trim().equals("星期五"))day=5;
            else if(((String) newValue).trim().equals("星期六"))day=6;
            else if(((String) newValue).trim().equals("星期日"))day=0;


            preference.setSummary(((String) newValue).trim());
            if(classno.isEnabled()){
                //Cursor cur = mysql.rawQuery("SELECT * FROM classes WHERE day="+day+" AND no="+no+";",null);
                String temp[] = myOpenHelper.onSelect(mysql,day,no,startweek,endweek);
                //if (cur != null) {
                //    String temp = "";
                //    String temp1="";
                //    String week[]= null;
                //    while (cur.moveToNext()) {//直到返回false说明表中到了数据末尾
                //        temp = cur.getString(2); // 参数0 指的是列的下标,这里的0指的是id列
               //         temp1 = cur.getString(3);
                //    }
                if(temp != null)
                {
                    classname.setSummary(temp[0]);
                    classname.setText(temp[0]);
                    classaddress.setSummary(temp[1]);
                    classaddress.setText(temp[1]);
                }

            }
            classno.setEnabled(true);


        }
        else  if(preference.getKey().equals(strclassno))
        {

            if(((String) newValue).trim().equals("1-2"))no=1;
            else if(((String) newValue).trim().equals("3-4"))no=2;
            else if(((String) newValue).trim().equals("5-6"))no=3;
            else if(((String) newValue).trim().equals("7-8"))no=4;
            else if(((String) newValue).trim().equals("9-10"))no=5;
            else if(((String) newValue).trim().equals("11-12"))no=6;

            preference.setSummary(((String) newValue).trim());
            //   sqlhelper.onSelect(mysql, day, no);
            if(classweekStart.isEnabled()) {
                String temp[] = myOpenHelper.onSelect(mysql, day, no, startweek, endweek);
                //Cursor cur = mysql.rawQuery("SELECT * FROM classes WHERE day="+day+" AND no="+no+";",null);
            /*if (cur != null) {
                String temp = "";
                String temp1="";
                while (cur.moveToNext()) {//直到返回false说明表中到了数据末尾
                    temp = cur.getString(2); // 参数0 指的是列的下标,这里的0指的是id列
                    temp1 = cur.getString(3);
                }*/
                if (temp != null) {
                    classname.setSummary(temp[0]);
                    classname.setText(temp[0]);
                    classaddress.setSummary(temp[1]);
                    classaddress.setText(temp[1]);
                }
                //cur.close();

            }
            classweekStart.setEnabled(true);

        }
        else  if(preference.getKey().equals(strclassweekStart))
        {

            System.out.println(((String) newValue).trim());
            if(((String) newValue).trim().equals("第一周"))startweek=1;
            else if(((String) newValue).trim().equals("第二周"))startweek=2;
            else if(((String) newValue).trim().equals("第三周"))startweek=3;
            else if(((String) newValue).trim().equals("第四周"))startweek=4;
            else if(((String) newValue).trim().equals("第五周"))startweek=5;
            else if(((String) newValue).trim().equals("第六周"))startweek=6;
            else if(((String) newValue).trim().equals("第七周"))startweek=7;
            else if(((String) newValue).trim().equals("第八周"))startweek=8;
            else if(((String) newValue).trim().equals("第九周"))startweek=9;
            else if(((String) newValue).trim().equals("第十周"))startweek=10;
            else if(((String) newValue).trim().equals("第十一周"))startweek=11;
            else if(((String) newValue).trim().equals("第十二周"))startweek=12;
            else if(((String) newValue).trim().equals("第十三周"))startweek=13;
            else if(((String) newValue).trim().equals("第十四周"))startweek=14;
            else if(((String) newValue).trim().equals("第十五周"))startweek=15;
            else if(((String) newValue).trim().equals("第十六周"))startweek=16;
            else if(((String) newValue).trim().equals("第十七周"))startweek=17;
            else if(((String) newValue).trim().equals("第十八周"))startweek=18;
            else if(((String) newValue).trim().equals("第十九周"))startweek=19;
            else if(((String) newValue).trim().equals("第二十周"))startweek=20;
            startweek = Integer.parseInt(((String) newValue).trim());
            System.out.println(startweek);
            preference.setSummary(((String) newValue).trim());
            //   sqlhelper.onSelect(mysql, day, no);
            /*Cursor cur = mysql.rawQuery("SELECT * FROM classes WHERE day="+day+" AND no="+no+";",null);
            if (cur != null) {
                String temp = "";
                String temp1="";
                int temp2=1;
                int temp3=1;
                String week[]= null;
                while (cur.moveToNext()) {//直到返回false说明表中到了数据末尾
                    temp = cur.getString(2); // 参数0 指的是列的下标,这里的0指的是id列
                    temp1 = cur.getString(3);
                    temp2 = cur.getInt(4);
                    temp3 = cur.getInt(5);
                }*/
            if(classweekEnd.isEnabled())
            {
                String temp[] = myOpenHelper.onSelect(mysql, day, no, startweek, endweek);
                if(temp != null)
                {
                    classname.setSummary(temp[0]);
                    classname.setText(temp[0]);
                    classaddress.setSummary(temp[1]);
                    classaddress.setText(temp[1]);
                }
            }
               /* classname.setSummary(temp);
                classname.setText(temp);
                classaddress.setSummary(temp1);
                classaddress.setText(temp1);
                classweekStart.setSummary(String.valueOf(temp2));
                classweekStart.setText(String.valueOf(temp2));
                classweekEnd.setSummary(String.valueOf(temp3));
                classweekEnd.setText(String.valueOf(temp3));
            }*/
            //cur.close();
            classweekEnd.setEnabled(true);


        }
        else  if(preference.getKey().equals(strclassweekEnd))
        {

            if(((String) newValue).trim().equals("第一周"))endweek=1;
            else if(((String) newValue).trim().equals("第二周"))endweek=2;
            else if(((String) newValue).trim().equals("第三周"))endweek=3;
            else if(((String) newValue).trim().equals("第四周"))endweek=4;
            else if(((String) newValue).trim().equals("第五周"))endweek=5;
            else if(((String) newValue).trim().equals("第六周"))endweek=6;
            else if(((String) newValue).trim().equals("第七周"))endweek=7;
            else if(((String) newValue).trim().equals("第八周"))endweek=8;
            else if(((String) newValue).trim().equals("第九周"))endweek=9;
            else if(((String) newValue).trim().equals("第十周"))endweek=10;
            else if(((String) newValue).trim().equals("第十一周"))endweek=11;
            else if(((String) newValue).trim().equals("第十二周"))endweek=12;
            else if(((String) newValue).trim().equals("第十三周"))endweek=13;
            else if(((String) newValue).trim().equals("第十四周"))endweek=14;
            else if(((String) newValue).trim().equals("第十五周"))endweek=15;
            else if(((String) newValue).trim().equals("第十六周"))endweek=16;
            else if(((String) newValue).trim().equals("第十七周"))endweek=17;
            else if(((String) newValue).trim().equals("第十八周"))endweek=18;
            else if(((String) newValue).trim().equals("第十九周"))endweek=19;
            else if(((String) newValue).trim().equals("第二十周"))endweek=20;

            endweek = Integer.parseInt(((String) newValue).trim());
            System.out.println(endweek);

            preference.setSummary(((String) newValue).trim());
            //   sqlhelper.onSelect(mysql, day, no);
            /*Cursor cur = mysql.rawQuery("SELECT * FROM classes WHERE day="+day+" AND no="+no+";",null);
            if (cur != null) {
                String temp = "";
                String temp1="";
                int temp2=1;
                int temp3=1;
                String week[]= null;
                while (cur.moveToNext()) {//直到返回false说明表中到了数据末尾
                    temp = cur.getString(2); // 参数0 指的是列的下标,这里的0指的是id列
                    temp1 = cur.getString(3);
                    temp2 = cur.getInt(4);
                    temp3 = cur.getInt(5);
                }*/
            //if(classweekEnd.isEnabled())
            {
                String temp[] = myOpenHelper.onSelect(mysql, day, no, startweek, endweek);
                if(temp != null)
                {
                    classname.setSummary(temp[0]);
                    classname.setText(temp[0]);
                    classaddress.setSummary(temp[1]);
                    classaddress.setText(temp[1]);
                }
            }
               /* classname.setSummary(temp);
                classname.setText(temp);
                classaddress.setSummary(temp1);
                classaddress.setText(temp1);
                classweekStart.setSummary(String.valueOf(temp2));
                classweekStart.setText(String.valueOf(temp2));
                classweekEnd.setSummary(String.valueOf(temp3));
                classweekEnd.setText(String.valueOf(temp3));
            }*/
            //cur.close();
            classdetail.setEnabled(true);


        }
        else  if(preference.getKey().equals(strclassname))
        {



            preference.setSummary(newValue.toString());

            String temp[] = myOpenHelper.onSelect(mysql, day, no, startweek, endweek);
            //System.out.println(temp[4]);
            if (temp != null) {
                mysql.execSQL("UPDATE classes SET name='"
                        + newValue.toString() + "' WHERE day="
                        + day + " AND no="
                        + no + " AND weekstart="
                        + startweek + " AND weekend=" + endweek + ";");
                Toast.makeText(Settings.this, "课程名修改成功", Toast.LENGTH_LONG).show();
            }
            else {
                myOpenHelper.onInsert(mysql, day, no, newValue.toString(), " ", startweek, endweek, " ");
                Toast.makeText(Settings.this, "课程名修改成功", Toast.LENGTH_LONG).show();
            }
        }
        else  if(preference.getKey().equals(strclassaddress))
        {

            preference.setSummary(newValue.toString());
            String temp[] = myOpenHelper.onSelect(mysql, day, no, startweek, endweek);
            if (temp != null) {
                //mysql.execSQL("UPDATE classes SET address='"+newValue.toString()+"' WHERE day="+day+" AND no="+no+";");

                mysql.execSQL("UPDATE classes SET address='"
                        + newValue.toString() + "' WHERE day="
                        + day + " AND no="
                        + no + " AND weekstart="
                        + startweek + " AND weekend=" + endweek + ";");
                Toast.makeText(Settings.this, "地点修改成功", Toast.LENGTH_LONG).show();
            }
            else{
                myOpenHelper.onInsert(mysql, day, no, " ", newValue.toString(), startweek, endweek," ");
                Toast.makeText(Settings.this, "地点修改成功", Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            //如果返回false表示不允许被改变
            return false;
        }
        //返回true表示允许改变

        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            // do something on back.
            if(mysql.isOpen())
                mysql.close();
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        if(mysql.isOpen())
            mysql.close();
        super.onPause();
    }

}
