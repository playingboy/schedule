package net.iyouyu.schedule;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    public final static int VERSION = 1;// 版本号
    public final static String TABLE_NAME = "classes";// 表名
    public final static String TABLE_NAME2 = "term";// 表名2
    public final static String Day = "day";// 后面ContentProvider使用
    public final static String Startterm = "startterm";
    public final static String No = "no";
    public final static String Id = "id";
    public final static String Name = "name";
    public final static String Address="address";
    public final static String WeekStart="weekstart";
    public final static String WeekEnd="weekend";
    public final static String Information="information";
    public static final String DATABASE_NAME = "schedule.db";
    public  static SQLiteDatabase mysql;
    public MySQLiteOpenHelper(Context context) {
        // 在Android 中创建和打开一个数据库都可以使用openOrCreateDatabase 方法来实现，
        // 因为它会自动去检测是否存在这个数据库，如果存在则打开，不过不存在则创建一个数据库；
        // 创建成功则返回一个 SQLiteDatabase对象，否则抛出异常FileNotFoundException。
        // 下面是来创建一个名为"DATABASE_NAME"的数据库，并返回一个SQLiteDatabase对象

        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    // 在数据库第一次生成的时候会调用这个方法，一般我们在这个方法里边生成数据库表;
    public void onCreate(SQLiteDatabase db) {
        if(!this.tableis(db,TABLE_NAME))
        {
            String str_sql = "CREATE TABLE " + TABLE_NAME + "(" + Day
                    + " INTEGER,"+ No  + " INTEGER," + Name +" text,"
                    + Address + " text," + WeekStart + " INTEGER,"+ WeekEnd + " INTEGER,"
            + Information +" text );";
            // CREATE TABLE 创建一张表 然后后面是我们的表名
            // 然后表的列，第一个是id 方便操作数据,int类型
            // PRIMARY KEY 是指主键 这是一个int型,用于唯一的标识一行;
            // AUTOINCREMENT 表示数据库会为每条记录的key加一，确保记录的唯一性;
            // 最后我加入一列文本 String类型
            // 注意：这里str_sql是sql语句，注意空格！
            db.execSQL(str_sql);

            //for(int i=0;i<7;i++)
            //    for (int j=1;j<8;j++){
            //        db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES("+i+","+j+","+i+","+j+","+j+","+j+");");
                    //	  db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES("+i+","+j+",null,null);");
            //        System.out.println(j);
            //    }
        }
        if(!this.tableis(db,TABLE_NAME2))
        {
            String str_sql = "CREATE TABLE " + TABLE_NAME2 + "(" + Id+" INTEGER,"+Startterm
                    + " DATE);";
            db.execSQL(str_sql);
            db.execSQL("INSERT INTO "+TABLE_NAME2+" VALUES("+1+",null);");
        }

    }
    public void onInsert(SQLiteDatabase db,int day,int no,
                         String name,String address,
                         int weekstart, int weekend, String information) {
        String str_sql = "INSERT INTO " + TABLE_NAME +
                " VALUES("+day+","+no+",'"+name+"','"+address+"',"+weekstart+","+weekend+",'"+information+"');";
        // CREATE TABLE 创建一张表 然后后面是我们的表名
        // 然后表的列，第一个是id 方便操作数据,int类型
        // PRIMARY KEY 是指主键 这是一个int型,用于唯一的标识一行;
        // AUTOINCREMENT 表示数据库会为每条记录的key加一，确保记录的唯一性;
        // 最后我加入一列文本 String类型
        // 注意：这里str_sql是sql语句，注意空格！
        db.execSQL(str_sql);
        // 虽然此句我们生成了一张数据库表和包含该表的sql.himi文件,
        // 但是要注意 不是方法是创建，是传入的一句str_sql这句sql语句表示创建！！
        System.out.println("插入数据成功");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 一般默认情况下，当我们插入 数据库就立即更新
        // 当数据库需要升级的时候，Android 系统会主动的调用这个方法。
        // 一般我们在这个方法里边删除数据表，并建立新的数据表，
        // 当然是否还需要做其他的操作，完全取决于游戏需求。
        Log.v("schedule", "onUpgrade");
    }
    @SuppressWarnings("null")
    public String[] onSelect(SQLiteDatabase db, int day, int no, int weekstart, int weekend) {
        String b[] = new String[2];
        Cursor cur = db.rawQuery("SELECT * FROM classes WHERE day="+day+" AND no="+no+" AND weekstart="+weekstart+" AND weekend="+weekend+";",null);
        //if (cur != null) {
            //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++1");
            //int i = 0;
            //String a[] = null;
        if(cur.moveToNext()) {
            b[0] = cur.getString(2); // 参数0 指的是列的下标,这里的0指的是id列
            b[1] = cur.getString(3);
            //System.out.println(b[0] + b[1]);
            return b;
        }
        else {
            return null;
        }
    }



    public boolean tableis(SQLiteDatabase db,String tableName){



        Cursor cs = db.rawQuery("select count(*) from sqlite_master where type ='table' and name = '"+tableName+"'", null);
        if(cs.moveToNext()){
            int count = cs.getInt(0);
            if(count>0){
                return true;
            }
        }
        return false;


    }



}