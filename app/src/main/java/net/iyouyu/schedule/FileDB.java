package net.iyouyu.schedule;

import java.io.File;
import java.io.IOException;

public class FileDB {
	public static File path = new File("/sdcard/cqu/data/");// create a directer
	public static File f = new File("/sdcard/cqu/data/schedule.db");// create a file
	public static boolean fileflag = false;

	public static void create() {
		//System.out.println(" File create()"+android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED));
		if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		{
	
			if (!path.exists()) { // create a mysql database
				path.mkdirs();
				System.out.println("create a directer");
			}
			if (!f.exists()) { // 文件存在返回false
				try {
					f.createNewFile();// create a file
					fileflag = true;
					System.out.println("create a file");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("create a file exception");
				}
			}
		}
	}
}
