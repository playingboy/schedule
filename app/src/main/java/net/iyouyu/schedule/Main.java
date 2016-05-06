package net.iyouyu.schedule;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

public class Main extends Activity {
    private MySQLiteOpenHelper myOpenHelper;
    private SQLiteDatabase mysql;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6,tvview,tv1_2, tv2_2, tv3_2, tv4_2, tv5_2, tv6_2;
    private Button butleft,butright;
    int dayOfWeek;
    long weekth;
    private String name = "` ~", address = "` ~";
    //public final static String TABLE_NAME = "classes";// 表名
    //public final static String TABLE_NAME2 = "term";// 表名2
    //public static final String DATABASE_NAME = "schedule.db";

    //抽屉效果
    private GridView gv;
    private SlidingDrawer sd;
    private ImageView im;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("进入onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        startActivityForResult(new Intent(this, remind.class), 0);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.item0:
                startActivityForResult(new Intent(this, LeadingIn.class), 0);

                //Intent intent1 = new Intent();
                //intent1.setClass(Main.this, LeadingIn.class);
                //Main.this.startActivity(intent1);
                break;
            case R.id.item1:
                startActivityForResult(new Intent(this, Settings.class), 0);
                break;
            case R.id.item2:
                startActivityForResult(new Intent(this, SystemSet.class), 0);
                break;
            case R.id.item3:
                Intent intent = new Intent();
                intent.setClass(Main.this, About.class);
                Main.this.startActivity(intent);
                break;
            case R.id.item4:
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setIcon(android.R.drawable.btn_star)
                        .setTitle("退出")
                        .setMessage("您确认要退出程序吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Main.this.finish(); // 关闭程序的核心方法
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub

        super.onStop();
    }
    /*获得开学日期到现在为止的天数*/
    public static long getGapCount(SQLiteDatabase mysql, Date todate) {
        long weekth = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Cursor termstart = mysql.rawQuery("SELECT * FROM term WHERE id="+1+";",null);
        if (termstart != null) {
            String tempdate = null;
            if (termstart.moveToNext()) {//直到返回false说明表中到了数据末尾
                tempdate = termstart.getString(1); // 参数0 指的是列的下标,这里的0指的是id列
            }
            if(tempdate!=null){
                String str[] =tempdate.split("-");
                year=Integer.parseInt(str[0]);
                month=Integer.parseInt(str[1])-1;
                day=Integer.parseInt(str[2]);
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
            try {
                java.util.Date date;
                if(tempdate!=null){
                    date=sdf.parse(tempdate);
                }
                else{
                    month++;
                    date=sdf.parse(year+"-"+month+"-"+day);
                    month--;
                }

                calendar.setTime(date);
                int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);//获得这个星期的第几天
                weekth   =	((todate.getTime()-date.getTime())/86400000+day_of_week)/7+1;
                //tv.setText("已经开学"+day+"天");
               // System.out.println(day+"asdasdasd");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return weekth;
    }

    public void insertView(int tmpdayOfWeek){
        name = "` ~"; address = "` ~";
        butleft =(Button) findViewById(R.id.imageButton_left);
        butright =(Button) findViewById(R.id.imageButton_right);
        tv1 = (TextView) findViewById(R.id.textView1);tv1_2 = (TextView) findViewById(R.id.textView1_2);
        tv2 = (TextView) findViewById(R.id.textView2);tv2_2 = (TextView) findViewById(R.id.textView2_2);
        tv3 = (TextView) findViewById(R.id.textView3);tv3_2 = (TextView) findViewById(R.id.textView3_2);
        tv4 = (TextView) findViewById(R.id.textView4);tv4_2 = (TextView) findViewById(R.id.textView4_2);
        ImageView view= (ImageView) findViewById(R.id.imageView1);
        view.setBackgroundResource(android.R.drawable.divider_horizontal_dim_dark);
        view.setMinimumHeight(2);
        view.setMinimumWidth(600);
        ImageView view2= (ImageView) findViewById(R.id.imageView2);
        view2.setBackgroundResource(android.R.drawable.divider_horizontal_dim_dark);
        view2.setMinimumHeight(2);
        view2.setMinimumWidth(600);

        tv5 = (TextView) findViewById(R.id.textView5);tv5_2 = (TextView) findViewById(R.id.textView5_2);
        tv6 = (TextView) findViewById(R.id.textView6);tv6_2 = (TextView) findViewById(R.id.textView6_2);
        tvview =(TextView) findViewById(R.id.textviewday);


        //FileDB.create();
        myOpenHelper = new MySQLiteOpenHelper(this);
       // myOpenHelper.onCreate(DATABASE_NAME);
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
            mysql = SQLiteDatabase.openOrCreateDatabase(FileDB.f, null);

        }
        else
            mysql = myOpenHelper.getReadableDatabase();
        //myOpenHelper.onCreate(mysql);
        tvview.setText(tmpdayOfWeek==0?"星期日":tmpdayOfWeek==1?"星期一":tmpdayOfWeek==2?"星期二":tmpdayOfWeek==3?"星期三":tmpdayOfWeek==4?"星期四":tmpdayOfWeek==5?"星期五":"星期六");

        Cursor all = mysql.rawQuery("SELECT * FROM classes;", null);
        System.out.println("数据库中的总数据数：" + all.getCount());

        Cursor cur = mysql.rawQuery("SELECT * FROM classes WHERE day = "
                + tmpdayOfWeek + ";", null);
        tv1.setText(" ");tv1_2.setText(" ");
        tv2.setText(" ");tv2_2.setText(" ");
        tv3.setText(" ");tv3_2.setText(" ");
        tv4.setText(" ");tv4_2.setText(" ");
        tv5.setText(" ");tv5_2.setText(" ");
        tv6.setText(" ");tv6_2.setText(" ");
        /*获得当前日期*/
        weekth = getGapCount(mysql,new Date(System.currentTimeMillis()));
        if (cur != null) {
            //int flag = 1;
            //if (flag == 1);
            while (cur.moveToNext()) {
                switch(cur.getInt(1))
                {
                    case 1:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv1.setText(cur.getString(2) + "\n" + tv1.getText());
                            tv1_2.setText(cur.getString(3)+ "\n" + tv1_2.getText());
                        }
                        break;
                    case 2:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv2.setText(cur.getString(2)+ "\n" + tv2.getText());
                            tv2_2.setText(cur.getString(3)+ "\n" + tv2_2.getText());
                        }
                        break;
                    case 3:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv3.setText(cur.getString(2)+ "\n" + tv3.getText());
                            tv3_2.setText(cur.getString(3)+ "\n" + tv3_2.getText());
                        }
                        break;
                    case 4:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv4.setText(cur.getString(2)+ "\n" + tv4.getText());
                            tv4_2.setText(cur.getString(3)+ "\n" + tv4_2.getText());
                        }
                        break;
                    case 5:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv5.setText(cur.getString(2)+ "\n" + tv5.getText());
                            tv5_2.setText(cur.getString(3)+ "\n" + tv5_2.getText());
                        }
                        break;
                    case 6:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv6.setText(cur.getString(2)+ "\n" + tv6.getText());
                            tv6_2.setText(cur.getString(3)+ "\n" + tv6_2.getText());
                        }
                        break;
                    default:
                        break;
                }
                //System.out.println(cur.getInt(0));
                //System.out.println(cur.getInt(1));
                //name += cur.getString(2) + "` ~";
                //address += cur.getString(3) + "` ~";
            }
        }

        //String[] namearray = name.split("` ~");
        //String[] addressarray = address.split("` ~");

       /* tv1.setText(namearray[1]);tv1_2.setText(addressarray[1]);
        tv2.setText(namearray[2]);tv2_2.setText(addressarray[2]);
        tv3.setText(namearray[3]);tv3_2.setText(addressarray[3]);
        tv4.setText(namearray[4]);tv4_2.setText(addressarray[4]);
        tv5.setText(namearray[5]);tv5_2.setText(addressarray[5]);
        tv6.setText(namearray[6]);tv6_2.setText(addressarray[6]);*/

        cur.close();
        if (mysql.isOpen())
            mysql.close();

        butleft.setOnClickListener(new leftListener());
        butright.setOnClickListener(new rightListener());

    }


    class leftListener implements OnClickListener{

        public void onClick(View v){
            //	finish();
            Main.this.insertView((dayOfWeek+=6)%7);


        }
    }
    class rightListener implements OnClickListener{

        public void onClick(View v){
            //	finish();

            Main.this.insertView((++dayOfWeek)%7);

        }
    }


    protected void onResume() {
        // TODO Auto-generated method stub
        //实现返回即刷新

        super.onResume();
        LinearLayout locallinear =(LinearLayout)getLayoutInflater().inflate(R.layout.main, null);
        setContentView(this.deallayoiut(locallinear));



        butleft =(Button) findViewById(R.id.imageButton_left);
        butright =(Button) findViewById(R.id.imageButton_right);
        tv1 = (TextView) findViewById(R.id.textView1);tv1_2 = (TextView) findViewById(R.id.textView1_2);
        tv2 = (TextView) findViewById(R.id.textView2);tv2_2 = (TextView) findViewById(R.id.textView2_2);
        tv3 = (TextView) findViewById(R.id.textView3);tv3_2 = (TextView) findViewById(R.id.textView3_2);
        tv4 = (TextView) findViewById(R.id.textView4);tv4_2 = (TextView) findViewById(R.id.textView4_2);
        ImageView view= (ImageView) findViewById(R.id.imageView1);
        view.setBackgroundResource(android.R.drawable.divider_horizontal_dim_dark);
        view.setMinimumHeight(2);
        view.setMinimumWidth(600);
        ImageView view2= (ImageView) findViewById(R.id.imageView2);
        view2.setBackgroundResource(android.R.drawable.divider_horizontal_dim_dark);
        view2.setMinimumHeight(2);
        view2.setMinimumWidth(600);

        tv5 = (TextView) findViewById(R.id.textView5);tv5_2 = (TextView) findViewById(R.id.textView5_2);
        tv6 = (TextView) findViewById(R.id.textView6);tv6_2 = (TextView) findViewById(R.id.textView6_2);
        tvview =(TextView) findViewById(R.id.textviewday);


        //*判断是否存在SD卡，如果存在就创建一个数据库文件
        myOpenHelper = new MySQLiteOpenHelper(this);
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            if(FileDB.f.exists())
                mysql = SQLiteDatabase.openOrCreateDatabase(FileDB.f, null);               //无论数据库在哪里创建都需要首先声明这句
            else {
                FileDB.create();
                if(FileDB.fileflag){
                    mysql = SQLiteDatabase.openOrCreateDatabase(FileDB.f, null);          //在sd卡创建数据库的语句1
                    myOpenHelper.onCreate(mysql);                                       //在sd卡创建数据库的语句2
                }
                else
                    mysql = myOpenHelper.getReadableDatabase();
            }
        else
            mysql = myOpenHelper.getReadableDatabase();



        Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        dayOfWeek = dayOfWeek < 0 || dayOfWeek > 6 ? 0 : dayOfWeek;
        tvview.setText(dayOfWeek==0?"星期日":dayOfWeek==1?"星期一":dayOfWeek==2?"星期二":dayOfWeek==3?"星期三":dayOfWeek==4?"星期四":dayOfWeek==5?"星期五":"星期六");
        Cursor cur = mysql.rawQuery("SELECT * FROM classes WHERE day = "
                + dayOfWeek + ";", null);
        //String name = "` ~", address = "` ~";

        tv1.setText(" ");tv1_2.setText(" ");
        tv2.setText(" ");tv2_2.setText(" ");
        tv3.setText(" ");tv3_2.setText(" ");
        tv4.setText(" ");tv4_2.setText(" ");
        tv5.setText(" ");tv5_2.setText(" ");
        tv6.setText(" ");tv6_2.setText(" ");

        if (cur != null) {
            while (cur.moveToNext()) {
                switch(cur.getInt(1))
                {
                    case 1:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv1.setText(cur.getString(2));
                            tv1_2.setText(cur.getString(3));
                        }
                        break;
                    case 2:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv2.setText(cur.getString(2));
                            tv2_2.setText(cur.getString(3));
                        }
                        break;
                    case 3:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv3.setText(cur.getString(2));
                            tv3_2.setText(cur.getString(3));
                        }
                        break;
                    case 4:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv4.setText(cur.getString(2));
                            tv4_2.setText(cur.getString(3));
                        }
                        break;
                    case 5:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv5.setText(cur.getString(2));
                            tv5_2.setText(cur.getString(3));
                        }
                        break;
                    case 6:
                        if(cur.getInt(4) <= weekth && cur.getInt(5) >= weekth){
                            tv6.setText(cur.getString(2));
                            tv6_2.setText(cur.getString(3));
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        cur.close();
        if (mysql.isOpen())
            mysql.close();
        butleft.setOnClickListener(new leftListener());
        butright.setOnClickListener(new rightListener());


        //加抽屉效果---------
			    /* 加载 Layout在 MyGridViewAdapter类中定义  */
			    /* 初始化对象 */
        gv = (GridView)findViewById(R.id.myContent1);
        sd = (SlidingDrawer)findViewById(R.id.drawer1);
        im=(ImageView)findViewById(R.id.myImage1);
        gv.setBackgroundResource(R.drawable.openbg);
			    /* 使用定义的MyGridViewAdapter设置GridView里面的item内容 */
//			    MyGridViewAdapter adapter=new MyGridViewAdapter(this,items,icons);
//			    gv.setAdapter(adapter);
			    
			    /* 设定SlidingDrawer被打开的事件处理 */
        sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener()
        {
            @Override
            public void onDrawerOpened()
            {
                im.setImageResource(R.drawable.close);
            }
        });
			    /* 设置SlidingDrawer被关闭的事件处理 */
        sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener()
        {
            @Override
            public void onDrawerClosed()
            {
                im.setImageResource(R.drawable.open);
            }
        });

    }


    //处理 壁纸的选择对layout的设置
    private LinearLayout deallayoiut(LinearLayout locallinear){
        SharedPreferences prs = getSharedPreferences("schedule.xm",Context.MODE_PRIVATE);
        int num = prs.getInt("bgnum", 0);
        if(num==0||num==1)
            locallinear.setBackgroundResource(R.drawable.bg_hdpi);
        else if(num==2)
            locallinear.setBackgroundResource(R.drawable.bg2);
        else if(num==3)
            locallinear.setBackgroundResource(R.drawable.bg3);
        else if(num==4)
            locallinear.setBackgroundResource(R.drawable.bg4);
        return locallinear;

    }
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }


}